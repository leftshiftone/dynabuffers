package dynabuffers.ast.structural

import dynabuffers.api.IType

class FieldOptions(private val options: FieldOptionsOptions) : IType {

    fun isDeprecated() = options.deprecated

    data class FieldOptionsOptions(val deprecated: Boolean)

}
