package dynabuffers.ast.datatype

import dynabuffers.ast.AbstractAST
import java.nio.ByteBuffer

class FloatType : AbstractAST() {

    override fun size(value: Any, registry: IRegistry) = 4

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        buffer.putFloat(float(value))
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry) = buffer.float

    private fun float(obj: Any) = obj.toString().toFloat()

}
