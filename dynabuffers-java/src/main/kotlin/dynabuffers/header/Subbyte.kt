package dynabuffers.header

import dynabuffers.exception.DynabuffersException

data class Subbyte(private val value: Int, private val numberOfBits: Int, val name: String) {
    @ExperimentalUnsignedTypes
    constructor(value: Byte, numberOfBits: Int, name: String) : this(value.toUByte().toInt(), numberOfBits, name)

    init {
        if (numberOfBits > 8 || numberOfBits < 1) throw DynabuffersException("Field size of $numberOfBits bits for field $name must be in range 1-8.")
        val masked = (0xFF shr (8 - numberOfBits)) and value
        if (masked != value)
            throw DynabuffersException("Value $value of $name is too large for field size of $numberOfBits bits.")
    }

    companion object {
        fun compressValuesIntoByte(values: List<Subbyte>): Byte {
            var remainingBits = 8
            val result = values.map {
                remainingBits -= it.numberOfBits
                it.value shl remainingBits
            }.sum().toByte()
            if (remainingBits != 0) {
                throw DynabuffersException("Subbyte values have invalid length in bits. Expected: 8 Actual: ${8 - remainingBits}")
            }
            return result
        }
    }
}
