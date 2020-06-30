package dynabuffers

import dynabuffers.api.IAnnotation
import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.map.ImplicitDynabuffersMap
import dynabuffers.ast.ClassType
import dynabuffers.ast.EnumType
import dynabuffers.ast.UnionType
import dynabuffers.ast.annotation.*
import dynabuffers.ast.structural.Annotation
import dynabuffers.exception.DynabuffersException
import java.nio.ByteBuffer

class DynabuffersEngine(private val tree: List<ISerializable>) {

    private val listeners = ArrayList<(String) -> Unit>()

    fun addListener(consumer: (String) -> Unit) = listeners.add(consumer)

    fun serialize(map: Map<String, Any?>): ByteArray {
        val clazz = getPrimaryClass()
        val buffer = ByteBuffer.allocate(clazz.size(map, this.registry()))
        clazz.serialize(map, buffer, this.registry())

        return buffer.array()
    }

    fun serialize(implicitKey:String = "result", result: String) = serialize(mapOf(implicitKey to result))
    fun serialize(implicitKey:String = "result", result: ByteArray) = serialize(mapOf(implicitKey to result))
    fun serialize(implicitKey:String = "result", result: Int) = serialize(mapOf(implicitKey to result))
    fun serialize(implicitKey:String = "result", result: Long) = serialize(mapOf(implicitKey to result))
    fun serialize(implicitKey:String = "result", result: Short) = serialize(mapOf(implicitKey to result))
    fun serialize(implicitKey:String = "result", result: Byte) = serialize(mapOf(implicitKey to result))
    fun serialize(implicitKey:String = "result", result: Float) = serialize(mapOf(implicitKey to result))
    fun serialize(implicitKey:String = "result", result: Double) = serialize(mapOf(implicitKey to result))
    fun serialize(implicitKey:String = "result", result: Boolean) = serialize(mapOf(implicitKey to result))

    @Suppress("UNCHECKED_CAST")
    fun deserialize(bytes: ByteArray): Map<String, Any?> {
        val clazz = getPrimaryClass()
        val map = clazz.deserialize(ByteBuffer.wrap(bytes), this.registry()) as Map<String, Any>

        if (clazz is ClassType) {
            return if (clazz.options.options.isImplicit()) ImplicitDynabuffersMap(map) else map
        }
        return map
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
                return tree.find {
                    if (it is ClassType && it.options.name == name) return@find true
                    if (it is EnumType && it.options.name == name) return@find true
                    if (it is UnionType && it.options.name == name) return@find true

                    return@find false
                } ?: throw DynabuffersException("invalid reference $name")
            }
        }
    }

    private fun getPrimaryClass(): ISerializable {
        val classes = tree.filter { it is ClassType || it is UnionType }//.map { it as ClassType }
        return classes.find {
            when (it) {
                is ClassType -> it.options.options.isPrimary()
                is UnionType -> it.options.options.isPrimary()
                else -> false
            }
        } ?: classes[0]
    }

}
