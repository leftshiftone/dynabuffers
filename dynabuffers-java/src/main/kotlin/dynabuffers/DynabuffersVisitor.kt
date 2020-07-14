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
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import dynabuffers.ast.*
import dynabuffers.ast.EnumType.EnumTypeOptions
import dynabuffers.ast.datatype.*
import dynabuffers.ast.structural.*
import dynabuffers.ast.structural.Annotation
import org.antlr.v4.runtime.ParserRuleContext
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList

class DynabuffersVisitor(private val charset: Charset) : DynabuffersBaseVisitor<List<IType>>() {

    override fun visitEnumType(ctx: DynabuffersParser.EnumTypeContext): List<IType> {
        return listOf(EnumType(EnumTypeOptions(ctx.textAt(1), ctx.textAt(3, 2), charset)))
    }

    override fun visitUnionType(ctx: DynabuffersParser.UnionTypeContext): List<IType> {
        val list = super.visitUnionType(ctx)
        val unionOptions = (list.firstOrNull { it is UnionOptions }
                ?: UnionOptions(UnionOptions.UnionOptionsOptions(false, false, false))) as UnionOptions

        // TODO: remove workaround
        return listOf(UnionType(UnionType.UnionTypeOptions(ctx.textAt(1), ctx.textAt(3, 2)
                .filter { it != "{" && it != "}" }, unionOptions)))
    }

    override fun visitFieldType(ctx: DynabuffersParser.FieldTypeContext): List<IType> {
        val list = super.visitFieldType(ctx)

        val annotations = list.filter { it is Annotation }.map { it as Annotation }
        val name = ctx.textAt(annotations.size)
        val datatype = list[annotations.size] as ISerializable
        val options = list.firstOrNull { it is FieldOptions } ?: FieldOptions(FieldOptions.FieldOptionsOptions(false))
        val defaultVal = list.filter { it is Value }.map { (it as Value).options.value }.firstOrNull()
        return listOf(FieldType(FieldType.FieldTypeOptions(name, annotations, datatype, options as FieldOptions, defaultVal)))
    }

    override fun visitDataType(ctx: DynabuffersParser.DataTypeContext): List<IType> {
        return when (ctx.text) {
            "string" -> listOf(StringType(StringType.StringTypeOptions(charset)))
            "float" -> listOf(FloatType())
            "int" -> listOf(IntType())
            "boolean" -> listOf(BooleanType())
            "long" -> listOf(LongType())
            "byte" -> listOf(ByteType())
            "short" -> listOf(ShortType())
            "map" -> listOf(MapType(MapType.MapTypeOptions(charset)))
            else -> listOf(RefType(RefType.RefTypeOptions(ctx.text)))
        }
    }

    override fun visitArrayType(ctx: DynabuffersParser.ArrayTypeContext): List<IType> {
        val datatype = super.visitArrayType(ctx)[0] as ISerializable
        return listOf(ArrayType(ArrayType.ArrayTypeOptions(datatype)))
    }

    override fun visitOptionType(ctx: DynabuffersParser.OptionTypeContext?): List<IType> {
        val datatype = super.visitOptionType(ctx)[0] as ISerializable
        return listOf(OptionType(OptionType.OptionTypeOptions(datatype)))
    }

    override fun visitClassType(ctx: DynabuffersParser.ClassTypeContext): List<IType> {
        val list = super.visitClassType(ctx)
        val classOptions = list.firstOrNull { it is ClassOptions }
                ?: ClassOptions(ClassOptions.ClassOptionsOptions(false, false, false))

        val fields = list.filter { it is FieldType }.map { it as FieldType }
        val options = ClassType.ClassTypeOptions(ctx.textAt(1), fields, classOptions as ClassOptions)

        return listOf(ClassType(options))
    }

    override fun visitAnnotation(ctx: DynabuffersParser.AnnotationContext): List<IType> {
        val name = ctx.textAt(1)
        val values = super.visitAnnotation(ctx).map { it as Value }
        return listOf(Annotation(Annotation.AnnotationTypeOptions(name, values)))
    }

    override fun visitValue(ctx: DynabuffersParser.ValueContext): List<IType> {
        return listOf(Value(Value.ValueTypeOptions(when (ctx.text) {
            "[]" -> ArrayList<Any>()
            "[:]" -> HashMap<String, Any>()
            else -> ctx.text
        })))
    }

    override fun visitClassOptions(ctx: DynabuffersParser.ClassOptionsContext): List<IType> {
        return listOf(ClassOptions(ClassOptions.ClassOptionsOptions(
                ctx.text.contains("primary"),
                ctx.text.contains("deprecated"),
                ctx.text.contains("implicit"))))
    }

    override fun visitUnionOptions(ctx: DynabuffersParser.UnionOptionsContext): List<IType> {
        return listOf(UnionOptions(UnionOptions.UnionOptionsOptions(
                ctx.text.contains("primary"),
                ctx.text.contains("deprecated"),
                ctx.text.contains("implicit"))))
    }

    override fun visitFieldOptions(ctx: DynabuffersParser.FieldOptionsContext): List<IType> {
        return listOf(FieldOptions(FieldOptions.FieldOptionsOptions(ctx.text.contains("deprecated"))))
    }

    override fun visitNamespaceType(ctx: DynabuffersParser.NamespaceTypeContext): List<IType> {
        val name = ctx.getChild(1).text
        val list = super.visitNamespaceType(ctx).map { it as ISerializable }
        return listOf(NamespaceType(NamespaceType.NamespaceTypeOptions(name, list)))
    }

    override fun aggregateResult(aggregate: List<IType>?, nextResult: List<IType>?): List<IType> {
        val list = ArrayList<IType>()
        if (aggregate != null) list.addAll(aggregate)
        if (nextResult != null) list.addAll(nextResult)
        return list
    }

    private fun ParserRuleContext.textAt(index: Int) = this.getChild(index).text
    private fun ParserRuleContext.textAt(left: Int, right: Int) = (left..this.childCount - right).map { this.textAt(it) }

}
