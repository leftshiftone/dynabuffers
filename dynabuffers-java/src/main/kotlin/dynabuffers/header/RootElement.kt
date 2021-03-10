package dynabuffers.header

import dynabuffers.*
import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import dynabuffers.api.map.DynabuffersMap
import dynabuffers.api.map.ImplicitDynabuffersMap
import dynabuffers.ast.ClassType
import dynabuffers.ast.UnionType
import dynabuffers.exception.DynabuffersException
import java.nio.ByteBuffer

class RootElement(val tree: List<IType>, private val version: Byte = DynabuffersVersion.current.getMajor()) :
    ISerializable {
    private var namespaceResolver = NamespaceResolver(tree)

    override fun size(value: Any?, registry: IRegistry): Int {
        val schema = getSchemaForNamespace(value as Map<String, Any?>)
        val schemaRegistry = registry.createForSchema(schema)
        return Header(namespaceResolver, version).size(value, registry) + getRootType(schema).size(value, schemaRegistry)
    }

    private fun getSchemaForNamespace(value: Map<String, Any?>): List<IType> {
        val namespaceDescription = namespaceResolver.getNamespace(value)
        if (namespaceDescription is ConcreteNamespaceDescription) {
            return namespaceDescription.namespace.options.list;
        }
        return tree
    }

    override fun serialize(value: Any?, buffer: ByteBuffer, registry: IRegistry) {
        val header = Header(namespaceResolver, version)
        header.serialize(value, buffer, registry)
        val schema = getSchemaForNamespace(value as Map<String, Any?>)
        getRootType(schema).serialize(value, buffer, registry.createForSchema(schema))
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): DynabuffersMap {
        val header = Header(namespaceResolver, version).deserialize(buffer, registry)
        if (header.version != version)
            throw DynabuffersException("Dynabuffers version of serialized data does not match current version. Expected: $version Actual: ${header.version}")

        // TODO: Handle flag bits here (Encryption etc)

        if (header.namespaceDescription is ConcreteNamespaceDescription) {
            val schema = header.namespaceDescription.namespace.options.list
            val newRoot = RootElement(schema)
            val newRegistry = registry.createForSchema(schema)
            return newRoot.deserializeContent(buffer, newRegistry, header.namespaceDescription)
        }
        return deserializeContent(buffer, registry, header.namespaceDescription)
    }

    private fun deserializeContent(
        buffer: ByteBuffer,
        registry: IRegistry,
        namespaceDescription: NamespaceDescription
    ): DynabuffersMap {
        val rootType = getRootType(tree)

        var map = rootType.deserialize(buffer, registry) as Map<String, Any>
        if (namespaceDescription is ConcreteNamespaceDescription)
            map = map.plus(SpecialKey.NAMESPACE.key to namespaceDescription.joinedPath())

        return when (rootType) {
            is ClassType -> if (rootType.options.options.isImplicit())
                ImplicitDynabuffersMap(map, tree, rootType) else DynabuffersMap(map, tree, rootType)
            is UnionType -> if (rootType.options.options.isImplicit())
                ImplicitDynabuffersMap(map, tree, rootType) else DynabuffersMap(map, tree, rootType)
            else -> DynabuffersMap(map, tree, rootType)
        }
    }


    private fun getRootType(tree: List<IType>): ISerializable {
        val classes = tree.filter { it is ClassType || it is UnionType }
            .map { it as ISerializable }
        return classes.find {
            when (it) {
                is ClassType -> it.options.options.isPrimary()
                is UnionType -> it.options.options.isPrimary()
                else -> false
            }
        } ?: if (classes.isNotEmpty()) classes[0] else throw DynabuffersException("no root type found")
    }
}