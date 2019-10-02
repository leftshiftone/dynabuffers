package dynabuffers.ast.structural

import dynabuffers.api.IType

class ClassOptions(private val options: ClassOptionsOptions) : IType {

    data class ClassOptionsOptions(val primary:Boolean, val deprecated: Boolean)

    fun isPrimary() = options.primary
    fun isDeprecated() = options.deprecated

}
