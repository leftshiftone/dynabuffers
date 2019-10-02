package dynabuffers.ast.structural

import dynabuffers.api.IType

class Value(val options: ValueTypeOptions) : IType {

    fun isNumeric() = options.value.toString().codePoints().allMatch(Character::isDigit)
    fun asInt() = options.value.toString().toInt()
    fun asFloat() = options.value.toString().toFloat()

    data class ValueTypeOptions(val value: Any)

}
