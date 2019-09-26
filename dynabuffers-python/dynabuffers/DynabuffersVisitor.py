from dynabuffers.antlr.DynabuffersParser import DynabuffersParser
from dynabuffers.antlr.DynabuffersVisitor import DynabuffersVisitor as DynabuffersBaseVisitor
from dynabuffers.ast.ClassType import ClassType, ClassTypeOptions
from dynabuffers.ast.EnumType import EnumType, EnumTypeOptions
from dynabuffers.ast.FieldType import FieldType, FieldTypeOptions
from dynabuffers.ast.UnionType import UnionType, UnionTypeOptions
from dynabuffers.ast.datatype.ArrayType import ArrayType, ArrayTypeOptions
from dynabuffers.ast.datatype.BooleanType import BooleanType
from dynabuffers.ast.datatype.ByteType import ByteType
from dynabuffers.ast.datatype.FloatType import FloatType
from dynabuffers.ast.datatype.IntType import IntType
from dynabuffers.ast.datatype.LongType import LongType
from dynabuffers.ast.datatype.RefType import RefType, RefTypeOptions
from dynabuffers.ast.datatype.ShortType import ShortType
from dynabuffers.ast.datatype.StringType import StringType, StringTypeOptions


class DynabuffersVisitor(DynabuffersBaseVisitor):

    def visitEnumType(self, ctx: DynabuffersParser.EnumTypeContext):
        name = str(ctx.getChild(1))
        values = []
        for index in range(3, ctx.getChildCount() - 1):
            values.append(str(ctx.getChild(index)))

        return EnumType(EnumTypeOptions(name, values))

    def visitClassType(self, ctx: DynabuffersParser.ClassTypeContext):
        substring = ctx.getText()[0:ctx.getText().find("{")]
        primary = "primary" in substring
        deprecated = "deprecated" in substring

        name = str(ctx.getChild(1))
        fields = super().visitClassType(ctx)

        return ClassType(ClassTypeOptions(name, fields, primary, deprecated))

    def visitUnionType(self, ctx: DynabuffersParser.UnionTypeContext):
        values = []
        for i in range(3, ctx.getChildCount() - 1):
            values.append(str(ctx.getChild(i)))

        return UnionType(UnionTypeOptions(str(ctx.getChild(1)), values))

    def visitFieldType(self, ctx: DynabuffersParser.FieldTypeContext):
        name = str(ctx.getChild(0))
        datatype = super().visitFieldType(ctx)[0]
        deprecated = "(deprecated)" in ctx.getText()

        index = str(ctx.getText()).find("=")
        if index > 0:
            return FieldType(FieldTypeOptions(name, datatype, deprecated, ctx.getText()[index + 1:]))

        return FieldType(FieldTypeOptions(name, datatype, deprecated, None))

    def visitDataType(self, ctx: DynabuffersParser.DataTypeContext):
        if ctx.getText() == "string":
            return StringType(StringTypeOptions("utf-8"))
        if ctx.getText() == "float":
            return FloatType()
        if ctx.getText() == "int":
            return IntType()
        if ctx.getText() == "boolean":
            return BooleanType()
        if ctx.getText() == "long":
            return LongType()
        if ctx.getText() == "byte":
            return ByteType()
        if ctx.getText() == "short":
            return ShortType()
        else:
            return RefType(RefTypeOptions(ctx.getText()))

    def visitArrayType(self, ctx: DynabuffersParser.ArrayTypeContext):
        datatype = super().visitArrayType(ctx)[0]
        return ArrayType(ArrayTypeOptions(datatype))

    def aggregateResult(self, aggregate, nextResult):
        array = []
        if aggregate is not None:
            array += aggregate
        if nextResult is not None:
            array += [nextResult]
        return array
