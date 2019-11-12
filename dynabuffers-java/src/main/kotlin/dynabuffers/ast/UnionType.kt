package dynabuffers.ast

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import java.nio.ByteBuffer

data class UnionType(val options: UnionTypeOptions) : IType, ISerializable {

    override fun size(value: Any, registry: IRegistry): Int {
        val clazz = resolve(value, registry)
        return 1 + clazz.size(value, registry)
    }

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        val clazz = resolve(value, registry)
        buffer.put(options.values.indexOf(clazz.options.name).toByte())
        return clazz.serialize(value, buffer, registry)
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any {
        val index = buffer.get().toInt()
        val clazz = registry.resolve(options.values[index]) as ClassType
        return (mapOf(":type" to index)).plus(clazz.deserialize(buffer, registry) as Map<*, *>)
    }

    @Suppress("UNCHECKED_CAST")
    private fun resolve(value: Any, registry: IRegistry): ClassType {
        val classes = options.values.map(registry::resolve).filter { it is ClassType }.map { it as ClassType }

        val clazz = classes.find {
            val fields1 = it.options.fields.map { field -> field.options.name }.sorted()
            val fields2 = (value as Map<String, *>).keys.toList().sorted()

            fields1.containsAll(fields2)
        }
        require(clazz != null) { "union type $value cannot be resolved" }
        return clazz
    }

    data class UnionTypeOptions(val name: String, val values: List<String>)

}
