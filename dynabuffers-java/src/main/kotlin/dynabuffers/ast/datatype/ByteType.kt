package dynabuffers.ast.datatype

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import java.nio.ByteBuffer

class ByteType : IType, ISerializable {

    override fun size(value: Any, registry: IRegistry) = 1

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        buffer.put(byte(value))
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry) = buffer.get()

    private fun byte(obj: Any) = obj.toString().toByte()

}
