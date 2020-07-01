package dynabuffers.ast.structural

import dynabuffers.api.IType

class UnionOptions(private val options: UnionOptionsOptions) : IType {

    data class UnionOptionsOptions(val primary:Boolean, val deprecated: Boolean, val implicit: Boolean)

    fun isPrimary() = options.primary
    fun isDeprecated() = options.deprecated
    fun isImplicit() = options.implicit

}
