package dynabuffers

import dynabuffers.antlr.DynabuffersParser
import dynabuffers.exception.DynabuffersException
import org.antlr.v4.runtime.ParserRuleContext
import java.lang.StringBuilder

class TypeValidator {
    private val usedDataTypes = mutableSetOf<String>()
    private val availableDataTypes = mutableSetOf<String>()

    fun validate() {
        val invalidDatatypes = usedDataTypes.minus(availableDataTypes)
        if (invalidDatatypes.isNotEmpty()) throw DynabuffersException("The datatype(s) $invalidDatatypes are neither built in datatypes nor class, union or enum references.")
    }

    fun addAvailableType(ctx: ParserRuleContext) {
        val name = ctx.textAt(1)
        val typeIdentifier = getNamespacePrefix(ctx).append(name).toString()
        availableDataTypes.add(typeIdentifier)
    }

    fun addUsedType(ctx: ParserRuleContext) {
        val name = ctx.text
        val typeIdentifier = getNamespacePrefix(ctx).append(name).toString()
        usedDataTypes.add(typeIdentifier)
    }

    private fun getNamespacePrefix(ctx: ParserRuleContext): StringBuilder {
        val stringBuilder = StringBuilder()
        var context = ctx
        while (context.getParent() !is DynabuffersParser.CompilationContext) {
            context = context.getParent()
            if (context is DynabuffersParser.NamespaceTypeContext)
                stringBuilder.insert(0, "${context.textAt(1)}.")
        }
        return stringBuilder
    }

    private fun ParserRuleContext.textAt(index: Int) = this.getChild(index).text
}