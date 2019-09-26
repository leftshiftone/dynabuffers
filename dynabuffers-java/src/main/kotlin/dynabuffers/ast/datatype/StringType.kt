package dynabuffers.ast.datatype

import dynabuffers.ast.AbstractAST
import java.nio.ByteBuffer
import java.nio.charset.Charset

class StringType(private val options: StringTypeOptions) : AbstractAST() {

    override fun size(value: Any, registry: IRegistry) = 2 + str(value).toByteArray(options.charset).size

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        buffer.putShort(str(value).length.toShort())
        buffer.put(str(value).toByteArray(options.charset))
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any {
        val length = buffer.short
        val array = ByteArray(length.toInt())
        buffer.get(array)

        return String(array)
    }

    private fun str(obj: Any) = obj.toString()

    data class StringTypeOptions(val charset: Charset)

}
