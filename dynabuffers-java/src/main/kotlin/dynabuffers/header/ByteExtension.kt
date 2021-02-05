package dynabuffers

import dynabuffers.exception.DynabuffersException

fun Byte.checkedShiftLeft(numberOfBits: Int, message: String = ""): Byte {
    val newSizeInBits = 8 - numberOfBits
    if ((0xFF shl newSizeInBits) and this.toInt() != 0) // TODO: different check
        throw DynabuffersException("$message Value $this is too large for max field size $newSizeInBits bit.")
    return (this.toInt() shl numberOfBits).toByte()
}

fun Byte.checkedShiftRight(numberOfBits: Int, message: String = ""): Byte {
    val newSizeInBits = 8 - numberOfBits
    if ((0xFF shr newSizeInBits) and this.toInt() != 0) // TODO: different check
        throw DynabuffersException("$message Value $this has bits set in range 0 - $numberOfBits that would be truncated by shift operation.")
    return (this.toInt() shr numberOfBits).toByte()
}

fun Byte.assertOnlyNFirstBitsSet(numberOfBits: Int, message: String = ""): Byte {
    if ((0xFF shl numberOfBits) and this.toInt() != 0) // TODO: different check
        throw DynabuffersException("$message Value $this is too large for max field size $numberOfBits bit.")
    return this
}
