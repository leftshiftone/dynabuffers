package dynabuffers.ast.datatype

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import java.nio.ByteBuffer
import java.util.*

class OptionType(private val options: OptionTypeOptions) : IType, ISerializable {

    override fun size(value: Any?, registry: IRegistry): Int {
        if (!Objects.isNull(value))
            return 1 + options.datatype.size(value, registry)
        return 1
    }

    override fun serialize(value: Any?, buffer: ByteBuffer, registry: IRegistry) {
        buffer.put(if (!Objects.isNull(value)) 1.toByte() else 0.toByte())
        Optional.ofNullable(value).ifPresent { options.datatype.serialize(it, buffer, registry) }
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any? {
        if (buffer.get() == 0.toByte())
            return null
        return options.datatype.deserialize(buffer, registry)
    }

    data class OptionTypeOptions(val datatype: ISerializable)

}
