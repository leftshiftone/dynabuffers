package dynabuffers.ast.datatype

import dynabuffers.ast.AbstractAST
import java.nio.ByteBuffer

class BooleanType : AbstractAST() {

    override fun size(value: Any, registry: IRegistry) = 1

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        buffer.put(if (boolean(value)) 1.toByte() else 0.toByte())
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry) = buffer.get() == 1.toByte()

    private fun boolean(obj: Any) = obj.toString().equals("true", true)

}
