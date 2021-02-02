package dynabuffers

import dynabuffers.exception.DynabuffersException

fun Byte.checkedShiftLeft(numberOfBits: Int): Byte {
    if ((0xFF shl (8 - numberOfBits)) and this.toInt() != 0)
        throw DynabuffersException("Value $this is too large for max field size $numberOfBits bit.")
    return (this.toInt() shl numberOfBits).toByte()
}

fun Byte.checkedShiftRight(numberOfBits: Int): Byte {
    if ((0xFF shr (8 - numberOfBits)) and this.toInt() != 0)
        throw DynabuffersException("Value $this has bits set in range 0 - $numberOfBits that would be truncated by shift operation.")
    return (this.toInt() shr numberOfBits).toByte()
}

fun Byte.assertOnlyNFirstBitsSet(numberOfBits: Int): Byte {
    if ((0xFF shl numberOfBits) and this.toInt() != 0)
        throw DynabuffersException("Value $this is too large for max field size $numberOfBits bit.")
    return this
}
