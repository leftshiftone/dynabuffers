package dynabuffers.ast

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import dynabuffers.exception.DynabuffersException
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class EnumType(val options: EnumTypeOptions) : IType, ISerializable {

    override fun size(value: Any?, registry: IRegistry) = 1 + str(value).toByteArray(options.charset).size

    override fun serialize(value: Any?, buffer: ByteBuffer, registry: IRegistry) {
        if (value !in options.values)
            throw DynabuffersException("value $value is not a valid enum value (${options.values})")

        buffer.put(str(value).length.toByte())
        buffer.put(str(value).toByteArray(StandardCharsets.UTF_8))
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any {
        val length = buffer.get()
        val array = ByteArray(length.toInt())
        buffer.get(array)

        val string = String(array)
        if (string !in options.values)
            throw DynabuffersException("value $string is not a valid enum value (${options.values})")
        return string
    }

    private fun str(obj: Any?) = obj.toString()

    data class EnumTypeOptions(val name: String, val values: List<String>, val charset: Charset)

}
