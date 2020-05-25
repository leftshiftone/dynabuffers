package dynabuffers.ast.datatype

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import dynabuffers.ast.datatype.ArrayType.ArrayTypeOptions
import dynabuffers.ast.datatype.StringType.StringTypeOptions
import java.nio.ByteBuffer
import java.nio.charset.Charset

class MapType(val options: MapTypeOptions) : IType, ISerializable {

    private val stringType = StringType(StringTypeOptions(options.charset))
    private val booleanType = BooleanType()
    private val byteType = ByteType()
    private val floatType = FloatType()
    private val intType = IntType()
    private val longType = LongType()
    private val shortType = ShortType()

    override fun size(value: Any, registry: IRegistry): Int {
        var size = 0
        (value as Map<*, *>)
                .filter { e -> e.key != null && e.value != null }
                .forEach { k, v ->
                    val keyType = getKeyType(k!!)
                    val valType = getValType(v!!)

                    size += 4
                    size += keyType.size(k, registry)
                    size += valType.size(v, registry)
                }
        return size
    }

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        (value as Map<*, *>)
                .filter { e -> e.key != null && e.value != null }
                .forEach { k, v ->
                    val keyType = getKeyType(k!!)
                    val valType = getValType(v!!)

                    val keySize = keyType.size(k, registry)
                    val valSize = valType.size(v, registry)

                    val ordinal = typeToOrdinal(v)
                    buffer.putInt(mergeHeader(ordinal, keySize + valSize))
                    keyType.serialize(k, buffer, registry)
                    valType.serialize(v, buffer, registry)
                }

    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any {
        val map = HashMap<String, Any>()
        while (buffer.hasRemaining()) {
            val header = buffer.int
            val (type, length) = unmergeHeader(header)

            val keyType = stringType
            val valType = ordinalToType(type)

            val rawValue = ByteArray(length)
            buffer.get(rawValue)

            val entry = ByteBuffer.wrap(rawValue)

            // entry key deserialization
            val k = keyType.deserialize(entry, registry)
            // entry val deserialization
            val v = valType.deserialize(entry, registry)

            map[k as String] = v
        }
        return map
    }

    private fun getValType(obj: Any): ISerializable = when (obj) {
        is String -> stringType
        is Map<*, *> -> this
        is Boolean -> booleanType
        is Byte -> byteType
        is Float -> floatType
        is Int -> intType
        is Long -> longType
        is Short -> shortType
        is Array<*> -> ArrayType(ArrayTypeOptions(getValType(obj.first() ?: "")))
        is Collection<*> -> ArrayType(ArrayTypeOptions(getValType(obj.first() ?: "")))
        else -> throw IllegalArgumentException("cannot handle value $obj")
    }

    private fun getKeyType(obj: Any): ISerializable = when (obj) {
        is String -> stringType
        else -> throw IllegalArgumentException("cannot handle $obj")
    }

    private fun typeToOrdinal(obj: Any): Byte = when (obj) {
        is String -> 0
        is Boolean -> 10
        is Byte -> 20
        is Float -> 30
        is Int -> 40
        is Long -> 50
        is Short -> 60
        is Map<*, *> -> 70
        is Array<*> -> (80 + typeToOrdinal(obj.first() ?: "") / 10).toByte()
        is Collection<*> -> (80 + typeToOrdinal(obj.first() ?: "") / 10).toByte()
        else -> throw IllegalArgumentException("cannot handle value $obj")
    }

    private fun ordinalToType(ordinal: Byte): ISerializable = when (ordinal.toInt()) {
        0 -> stringType
        10 -> booleanType
        20 -> byteType
        30 -> floatType
        40 -> intType
        50 -> longType
        60 -> shortType
        70 -> this
        80 -> ArrayType(ArrayTypeOptions(StringType(StringTypeOptions(options.charset))))
        81 -> ArrayType(ArrayTypeOptions(BooleanType()))
        82 -> ArrayType(ArrayTypeOptions(ByteType()))
        83 -> ArrayType(ArrayTypeOptions(FloatType()))
        84 -> ArrayType(ArrayTypeOptions(IntType()))
        85 -> ArrayType(ArrayTypeOptions(LongType()))
        86 -> ArrayType(ArrayTypeOptions(ShortType()))
        87 -> ArrayType(ArrayTypeOptions(this))
        else -> throw IllegalArgumentException("cannot handle ordinal $ordinal")
    }

    private fun mergeHeader(a: Byte, b: Int): Int {
        val right = 0xFFFFFF
        return a.toInt().shl(24).or(b.and(right))
    }

    private fun unmergeHeader(c: Int):Pair<Byte, Int> {
        val right = 0xFFFFFF

        val aBack = c.shr(24).toByte();
        val bBack = c.and(right)

        return Pair(aBack, bBack)
    }

    data class MapTypeOptions(val charset: Charset)

}
