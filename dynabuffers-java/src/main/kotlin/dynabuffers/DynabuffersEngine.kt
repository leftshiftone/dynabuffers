package dynabuffers

import dynabuffers.api.IAnnotation
import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import dynabuffers.api.map.DynabuffersMap
import dynabuffers.api.map.ImplicitDynabuffersMap
import dynabuffers.ast.ClassType
import dynabuffers.ast.EnumType
import dynabuffers.ast.NamespaceType
import dynabuffers.ast.UnionType
import dynabuffers.ast.annotation.*
import dynabuffers.ast.structural.Annotation
import dynabuffers.exception.DynabuffersException
import java.nio.ByteBuffer
import kotlin.streams.toList

class DynabuffersEngine(private val tree: List<IType>) {

    private val listeners = ArrayList<(String) -> Unit>()

    fun addListener(consumer: (String) -> Unit) = listeners.add(consumer)

    fun serialize(map: Map<String, Any?>): ByteArray {
        val clazz = getRootType() as ISerializable
        val buffer = ByteBuffer.allocate(clazz.size(map, this.registry()))
        clazz.serialize(map, buffer, this.registry())

        return buffer.array()
    }

    fun serialize(namespaceName: String, map: Map<String, Any?>): ByteArray {
        val namespace = getNamespace(namespaceName)
        val engine = DynabuffersEngine(namespace.options.list)
        return engine.serialize(map)
    }

    fun serialize(namespaceNames: List<String>, map: Map<String, Any?>): ByteArray {
        val namespace = getNamespace(namespaceNames)
        val engine = DynabuffersEngine(namespace.options.list)
        return engine.serialize(map)
    }

    fun serialize(result: String) = serialize(mapOf("value" to result))
    fun serialize(result: ByteArray) = serialize(mapOf("value" to result))
    fun serialize(result: Int) = serialize(mapOf("value" to result))
    fun serialize(result: Long) = serialize(mapOf("value" to result))
    fun serialize(result: Short) = serialize(mapOf("value" to result))
    fun serialize(result: Byte) = serialize(mapOf("value" to result))
    fun serialize(result: Float) = serialize(mapOf("value" to result))
    fun serialize(result: Double) = serialize(mapOf("value" to result))
    fun serialize(result: Boolean) = serialize(mapOf("value" to result))

    @Suppress("UNCHECKED_CAST")
    fun deserialize(bytes: ByteArray): DynabuffersMap {
        val root = getRootType() as ISerializable
        val map = root.deserialize(ByteBuffer.wrap(bytes), this.registry()) as Map<String, Any>

        return when (root) {
            is ClassType -> if (root.options.options.isImplicit())
                ImplicitDynabuffersMap(map, tree, root) else DynabuffersMap(map, tree, root)
            is UnionType -> if (root.options.options.isImplicit())
                ImplicitDynabuffersMap(map, tree, root) else DynabuffersMap(map, tree, root)
            else -> DynabuffersMap(map, tree, root)
        }
    }

    fun deserialize(namespaceName: String, bytes: ByteArray): DynabuffersMap {
        val namespace = getNamespace(namespaceName)
        val engine = DynabuffersEngine(namespace.options.list)
        return engine.deserialize(bytes)
    }

    fun deserialize(namespaceNames: List<String>, bytes: ByteArray): DynabuffersMap {
        val namespace = getNamespace(namespaceNames)
        val engine = DynabuffersEngine(namespace.options.list)
        return engine.deserialize(bytes)
    }

    private fun registry(): IRegistry {
        return object : IRegistry {
            override fun resolveAnnotation(annotation: Annotation):IAnnotation {
                when(annotation.options.name) {
                    "NotBlank" -> return NotBlank(annotation.options.values)
                    "MinLength" -> return MinLength(annotation.options.values)
                    "MaxLength" -> return MaxLength(annotation.options.values)
                    "GreaterThan" -> return GreaterThan(annotation.options.values)
                    "GreaterEquals" -> return GreaterEquals(annotation.options.values)
                    "LowerThan" -> return LowerThan(annotation.options.values)
                    "LowerEquals" -> return LowerEquals(annotation.options.values)
                    else -> throw DynabuffersException("unknown annotation ${annotation.options.name}")
                }

            }

            override fun addNotification(notification: String) {
                listeners.forEach { it(notification) }
            }

            override fun resolve(name: String): ISerializable {
                return tree.filterIsInstance<ISerializable>().find {
                    if (it is ClassType && it.options.name == name) return@find true
                    if (it is EnumType && it.options.name == name) return@find true
                    if (it is UnionType && it.options.name == name) return@find true

                    return@find false
                } ?: throw DynabuffersException("invalid reference $name")
            }
        }
    }

    private fun getRootType(): IType {
        val classes = tree.filter { it is ClassType || it is UnionType }
        return classes.find {
            when (it) {
                is ClassType -> it.options.options.isPrimary()
                is UnionType -> it.options.options.isPrimary()
                else -> false
            }
        } ?: if (classes.isNotEmpty()) classes[0] else throw DynabuffersException("no root type found")
    }

    private fun getNamespace(name: String): NamespaceType = getNamespace(listOf(name))

    private fun findNamespaceByName(name: String, namespaces: List<NamespaceType>?): NamespaceType? {
        return namespaces?.find { it.options.name==name }
    }

    private fun getNamespace(names: List<String>, namespaces: List<NamespaceType>): NamespaceType {
        val nsName= names.first()
        val ns = findNamespaceByName(nsName, namespaces) ?: throw DynabuffersException("no namespace with name $nsName found")
        if(names.size==1) return ns
        return getNamespace(names.stream().skip(1).toList(), ns.nestedNamespaces)
    }

    private fun getNamespacesFromRootLevel() =  tree.filterIsInstance<NamespaceType>()
    private fun getNamespace(names: List<String>): NamespaceType = getNamespace(names, getNamespacesFromRootLevel())

}
