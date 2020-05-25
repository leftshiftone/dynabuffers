package dynabuffers.ast.datatype

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import java.nio.ByteBuffer

class ShortType : IType, ISerializable {

    override fun size(value: Any, registry: IRegistry) = 2

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        buffer.putShort(short(value))
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry) = buffer.short

    override fun supports(value: Any): Boolean {
        return value is Number && value.toDouble() <= Short.MAX_VALUE && value.toFloat() >= Short.MIN_VALUE
    }

    private fun short(obj: Any) = obj.toString().toShort()

}
