package dynabuffers.ast.datatype

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import java.nio.ByteBuffer
import java.util.*

class OptionType(private val options: OptionTypeOptions) : IType, ISerializable {

    override fun size(value: Any, registry: IRegistry): Int {
        val option = value as Optional<*>
        if (option.isPresent)
            return 1 + options.datatype.size(option.get(), registry)
        return 1
    }

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        val option = value as Optional<*>
        buffer.put(if (option.isPresent) 1.toByte() else 0.toByte())
        option.ifPresent { options.datatype.serialize(it, buffer, registry) }
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any {
        if (buffer.get() == 0.toByte())
            return Optional.empty<Any>()
        return Optional.of(options.datatype.deserialize(buffer, registry))
    }

    data class OptionTypeOptions(val datatype: ISerializable)

}
