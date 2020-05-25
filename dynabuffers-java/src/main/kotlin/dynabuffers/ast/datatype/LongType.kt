package dynabuffers.ast.datatype

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import java.nio.ByteBuffer

class LongType : IType, ISerializable {

    override fun size(value: Any, registry: IRegistry) = 8

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        buffer.putLong(long(value))
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry) = buffer.long

    override fun supports(value: Any): Boolean {
        return value is Number && value.toDouble() <= Long.MAX_VALUE && value.toFloat() >= Long.MIN_VALUE
    }

}
