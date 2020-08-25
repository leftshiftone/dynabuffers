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

    private val namespaceResolver: NamespaceResolver = NamespaceResolver(this.tree)
    private val listeners = ArrayList<(String) -> Unit>()

    fun addListener(consumer: (String) -> Unit) = listeners.add(consumer)

    @JvmOverloads
    fun serialize(map: Map<String, Any?>, namespaceNames: List<String>? = null): ByteArray {
        val namespace: NamespaceType? = namespaceResolver.getNamespace(namespaceNames)
        if (namespace != null) {
            val engine = DynabuffersEngine(namespace.options.list)
            return engine.serialize(map)
        }

        val clazz = getRootType() as ISerializable
        val buffer = ByteBuffer.allocate(clazz.size(map, this.registry()))
        clazz.serialize(map, buffer, this.registry())
        return buffer.array()
    }

    fun serialize(map: Map<String, Any?>, namespaceName: String): ByteArray {
        return serialize(map, listOf(namespaceName))
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
        val namespace: NamespaceType? = namespaceResolver.getNamespace(namespaceNames)
        if (namespace != null) {
            val engine = DynabuffersEngine(namespace.options.list)
            return engine.deserialize(bytes)
        }

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