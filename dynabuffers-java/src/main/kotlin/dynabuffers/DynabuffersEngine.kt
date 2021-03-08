package dynabuffers

import dynabuffers.api.IAnnotation
import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import dynabuffers.api.map.DynabuffersMap
import dynabuffers.ast.*
import dynabuffers.ast.annotation.*
import dynabuffers.ast.structural.Annotation
import dynabuffers.exception.DynabuffersException
import dynabuffers.header.RootElement
import java.nio.ByteBuffer

class DynabuffersEngine(private val tree: List<IType>) {

    private val listeners = ArrayList<(String) -> Unit>()

    fun addListener(consumer: (String) -> Unit) = listeners.add(consumer)

    fun serialize(map: Map<String, Any?>): ByteArray {
        val root = RootElement(tree)
        val registry = Registry(tree, listeners)
        val buffer = ByteBuffer.allocate(root.size(map, registry))
        root.serialize(map, buffer, registry)
        return buffer.array()
    }

    fun serialize(map: Map<String, Any?>, namespaceName: String): ByteArray {
        if (map.containsKey(SpecialKey.NAMESPACE.key)) throw IllegalStateException("Nope sir")
        return serialize(map.plus(SpecialKey.NAMESPACE.key to namespaceName))
    }

    fun serialize(map: Map<String, Any?>, namespaceNames: List<String>): ByteArray {
        if (map.containsKey(SpecialKey.NAMESPACE.key)) throw IllegalStateException("Nope sir")
        return serialize(map.plus(SpecialKey.NAMESPACE.key to namespaceNames.joinToString(".")))
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


    fun deserialize(bytes: ByteArray): DynabuffersMap {
        val bb = ByteBuffer.wrap(bytes)
        return RootElement(tree).deserialize(bb, Registry(tree, listeners))
    }
}
