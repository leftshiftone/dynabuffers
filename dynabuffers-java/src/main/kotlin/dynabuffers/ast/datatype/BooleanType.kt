package dynabuffers.ast.datatype

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import java.nio.ByteBuffer

class BooleanType : IType, ISerializable {

    override fun size(value: Any, registry: IRegistry) = 1

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        buffer.put(if (boolean(value)) 1.toByte() else 0.toByte())
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry) = buffer.get() == 1.toByte()

    override fun supports(value: Any) = value.toString().toLowerCase() in listOf("true", "false")

    private fun boolean(obj: Any) = obj.toString().equals("true", true)

}
