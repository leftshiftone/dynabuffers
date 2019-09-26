/*
 * Copyright (c) 2016-2018, Leftshift One
 * __________________
 * [2018] Leftshift One
 * All Rights Reserved.
 * NOTICE:  All information contained herein is, and remains
 * the property of Leftshift One and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Leftshift One
 * and its suppliers and may be covered by Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Leftshift One.
 */

package dynabuffers

import dynabuffers.antlr.DynabuffersBaseVisitor
import dynabuffers.antlr.DynabuffersParser
import dynabuffers.ast.*
import dynabuffers.ast.EnumType.EnumTypeOptions
import dynabuffers.ast.datatype.*
import org.antlr.v4.runtime.ParserRuleContext
import java.nio.charset.Charset

class DynabuffersVisitor(private val charset: Charset) : DynabuffersBaseVisitor<List<AbstractAST>>() {

    override fun visitEnumType(ctx: DynabuffersParser.EnumTypeContext): List<AbstractAST> {
        return listOf(EnumType(EnumTypeOptions(ctx.textAt(1), ctx.textAt(3, 2), charset)))
    }

    override fun visitUnionType(ctx: DynabuffersParser.UnionTypeContext): List<AbstractAST> {
        return listOf(UnionType(UnionType.UnionTypeOptions(ctx.textAt(1), ctx.textAt(3, 2))))
    }

    override fun visitFieldType(ctx: DynabuffersParser.FieldTypeContext): List<AbstractAST> {
        val deprecated = ctx.options(3).contains("deprecated")
        val defaultVal = ctx.text.substringAfter("=")
        return listOf(FieldType(FieldType.FieldTypeOptions(ctx.textAt(0), super.visitFieldType(ctx)[0], deprecated, defaultVal)))
    }

    override fun visitDataType(ctx: DynabuffersParser.DataTypeContext): List<AbstractAST> {
        return when (ctx.text) {
            "string" -> listOf(StringType(StringType.StringTypeOptions(charset)))
            "float" -> listOf(FloatType())
            "int" -> listOf(IntType())
            "boolean" -> listOf(BooleanType())
            "long" -> listOf(LongType())
            "byte" -> listOf(ByteType())
            "short" -> listOf(ShortType())
            else -> listOf(RefType(RefType.RefTypeOptions(ctx.text)))
        }
    }

    override fun visitArrayType(ctx: DynabuffersParser.ArrayTypeContext): List<AbstractAST> {
        return listOf(ArrayType(ArrayType.ArrayTypeOptions(super.visitArrayType(ctx)[0])))
    }

    override fun visitClassType(ctx: DynabuffersParser.ClassTypeContext): List<AbstractAST> {
        val primary = ctx.options(2).contains("primary")
        val deprecated = ctx.options(2).contains("deprecated")

        val fields = super.visitClassType(ctx).map { it as FieldType }
        val options = ClassType.ClassTypeOptions(ctx.textAt(1), fields, primary, deprecated)

        return listOf(ClassType(options))
    }

    /**
     * {@inheritDoc}
     */
    override fun aggregateResult(aggregate: List<AbstractAST>?, nextResult: List<AbstractAST>?): List<AbstractAST> {
        val list = ArrayList<AbstractAST>()
        if (aggregate != null) list.addAll(aggregate)
        if (nextResult != null) list.addAll(nextResult)
        return list
    }

    private fun ParserRuleContext.textAt(index: Int) = this.getChild(index).text
    private fun ParserRuleContext.textAt(left: Int, right: Int) = (left..this.childCount - right).map { this.textAt(it) }
    private fun ParserRuleContext.textAt(range: IntRange) = range.map { this.textAt(it) }
    private fun ParserRuleContext.options(index: Int): List<String> {
        val list = ArrayList<String>()
        if (index < this.childCount && this.textAt(index) == "(") {
            for (i in index + 1..this.childCount) {
                if (this.textAt(i) != ")") {
                    list.add(this.textAt(i))
                } else {
                    break
                }
            }
        }
        return list
    }

}
