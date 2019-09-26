package dynabuffers.ast.datatype

import dynabuffers.ast.AbstractAST
import java.nio.ByteBuffer

class ByteType : AbstractAST() {

    override fun size(value: Any, registry: IRegistry) = 1

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        buffer.put(byte(value))
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry) = buffer.get()

    private fun byte(obj: Any) = obj.toString().toByte()

}
