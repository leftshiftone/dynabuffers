package dynabuffers.ast.datatype

import dynabuffers.ast.AbstractAST
import java.nio.ByteBuffer

class IntType : AbstractAST() {

    override fun size(value: Any, registry: IRegistry) = 4

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        buffer.putInt(int(value))
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry) = buffer.int

    private fun int(obj: Any) = obj.toString().toInt()

}
