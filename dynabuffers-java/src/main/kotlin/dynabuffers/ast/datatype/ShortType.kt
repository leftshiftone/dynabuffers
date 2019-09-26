package dynabuffers.ast.datatype

import dynabuffers.ast.AbstractAST
import java.nio.ByteBuffer

class ShortType : AbstractAST() {

    override fun size(value: Any, registry: IRegistry) = 2

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        buffer.putShort(short(value))
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry) = buffer.short

    private fun short(obj: Any) = obj.toString().toShort()

}
