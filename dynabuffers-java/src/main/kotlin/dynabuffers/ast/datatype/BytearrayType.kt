package dynabuffers.ast.datatype

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import java.nio.ByteBuffer

class BytearrayType : IType, ISerializable {

    override fun size(value: Any, registry: IRegistry) = 4 + (value as ByteArray).size

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        val bytes = (value as ByteArray)
        buffer.putInt(bytes.size)
        buffer.put(bytes)
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any {
        val length = buffer.int
        val array = ByteArray(length)
        buffer.get(array)

        return array
    }

}
