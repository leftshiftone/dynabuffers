package dynabuffers.ast.datatype

import dynabuffers.ast.AbstractAST
import java.nio.ByteBuffer

class LongType : AbstractAST() {

    override fun size(value: Any, registry: IRegistry) = 8

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        buffer.putLong(long(value))
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry) = buffer.long

}
