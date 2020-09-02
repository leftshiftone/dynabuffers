package dynabuffers

import dynabuffers.api.IAnnotation
import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import dynabuffers.api.map.DynabuffersMap
import dynabuffers.api.map.ImplicitDynabuffersMap
import dynabuffers.ast.ClassType
import dynabuffers.ast.EnumType
import dynabuffers.ast.UnionType
import dynabuffers.ast.annotation.*
import dynabuffers.ast.structural.Annotation
import dynabuffers.exception.DynabuffersException
import java.nio.ByteBuffer

class DynabuffersEngine(private val tree: List<IType>) {

    private val namespaceResolver: NamespaceResolver = NamespaceResolver(this.tree)
    private val listeners = ArrayList<(String) -> Unit>()

    fun addListener(consumer: (String) -> Unit) = listeners.add(consumer)

    @JvmOverloads
    fun serialize(map: Map<String, Any?>): ByteArray {
        return when (val namespaceDescription = namespaceResolver.getNamespace(map)) {
            is AbsentNamespace -> {
                val clazz = getRootType() as ISerializable
                val buffer = ByteBuffer.allocate(clazz.size(map, this.registry()) + 1)
                buffer.put(0)
                clazz.serialize(map, buffer, this.registry())
                buffer.array()
            }
            is ConcreteNamespaceDescription -> {
                val engine = DynabuffersEngine(namespaceDescription.namespace.options.list)
                return engine.serializeNamespaceAware(map, namespaceDescription)
            }
        }
    }

    fun serialize(map: Map<String, Any?>, namespaceName: String): ByteArray {
        if (map.containsKey(SpecialKey.NAMESPACE.key)) throw IllegalStateException("Nope sir")
        return serialize(map.plus(SpecialKey.NAMESPACE.key to namespaceName))
    }

    fun serialize(map: Map<String, Any?>, namespaceNames: List<String>): ByteArray {
        if (map.containsKey(SpecialKey.NAMESPACE.key)) throw IllegalStateException("Nope sir")
        return serialize(map.plus(SpecialKey.NAMESPACE.key to namespaceNames.joinToString(".")))
    }

    private fun serializeNamespaceAware(map: Map<String, Any?>, namespaceInformation: ConcreteNamespaceDescription): ByteArray {
        val clazz = getRootType() as ISerializable
        val amountNamespaces = namespaceInformation.path.size.toByte()
        val buffer = ByteBuffer.allocate(clazz.size(map, this.registry()) + 1 + amountNamespaces)
        buffer.put(amountNamespaces)
        namespaceInformation.path.forEach { buffer.put(it.position) }
        clazz.serialize(map, buffer, this.registry())
        return buffer.array()
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
    @JvmOverloads
    fun deserialize(bytes: ByteArray, namespaceNames: List<String>? = null): DynabuffersMap {
        val bb = ByteBuffer.wrap(bytes)
        return when (val namespaceDescription = namespaceResolver.getNamespace(bb)) {
            is AbsentNamespace -> internalDeserialize(bb, null)
            is ConcreteNamespaceDescription -> {
                val engine = DynabuffersEngine(namespaceDescription.namespace.options.list)
                engine.internalDeserialize(bb, namespaceDescription)
            }
        }
    }

    private fun internalDeserialize(byteBuffer: ByteBuffer, namespaceInfo: ConcreteNamespaceDescription?): DynabuffersMap {
        val root = getRootType() as ISerializable
        val map = if (namespaceInfo != null) {
            val m = root.deserialize(byteBuffer, this.registry()) as Map<String, Any>
            m.plus(SpecialKey.NAMESPACE.key to namespaceInfo.joinedPath())
        } else {
            root.deserialize(byteBuffer, this.registry()) as Map<String, Any>
        }

        return when (root) {
            is ClassType -> if (root.options.options.isImplicit())
                ImplicitDynabuffersMap(map, tree, root) else DynabuffersMap(map, tree, root)
            is UnionType -> if (root.options.options.isImplicit())
                ImplicitDynabuffersMap(map, tree, root) else DynabuffersMap(map, tree, root)
            else -> DynabuffersMap(map, tree, root)
        }
    }

    fun deserialize(bytes: ByteArray, namespaceName: String): DynabuffersMap {
        return deserialize(bytes, listOf(namespaceName))
    }

    private fun registry(): IRegistry {
        return object : IRegistry {
            override fun resolveAnnotation(annotation: Annotation): IAnnotation {
                when (annotation.options.name) {
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
}
