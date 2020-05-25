package dynabuffers.ast.datatype

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import java.nio.ByteBuffer

class IntType : IType, ISerializable {

    override fun size(value: Any, registry: IRegistry) = 4

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        buffer.putInt(int(value))
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry) = buffer.int

    private fun int(obj: Any) = obj.toString().toInt()

    override fun supports(value: Any): Boolean {
        return value is Number && value.toDouble() <= Int.MAX_VALUE && value.toFloat() >= Int.MIN_VALUE
    }

}
