from dynabuffers.antlr.DynabuffersParser import DynabuffersParser
from dynabuffers.antlr.DynabuffersVisitor import DynabuffersVisitor as DynabuffersBaseVisitor
from dynabuffers.ast.ClassType import ClassType, ClassTypeOptions
from dynabuffers.ast.EnumType import EnumType, EnumTypeOptions
from dynabuffers.ast.FieldType import FieldType, FieldTypeOptions
from dynabuffers.ast.NamespaceType import NamespaceType, NamespaceTypeOptions
from dynabuffers.ast.UnionType import UnionType, UnionTypeOptions
from dynabuffers.ast.datatype.ArrayType import ArrayType, ArrayTypeOptions
from dynabuffers.ast.datatype.BooleanType import BooleanType
from dynabuffers.ast.datatype.ByteType import ByteType
from dynabuffers.ast.datatype.FloatType import FloatType
from dynabuffers.ast.datatype.IntType import IntType
from dynabuffers.ast.datatype.LongType import LongType
from dynabuffers.ast.datatype.MapType import MapType, MapTypeOptions
from dynabuffers.ast.datatype.OptionType import OptionType, OptionTypeOptions
from dynabuffers.ast.datatype.RefType import RefType, RefTypeOptions
from dynabuffers.ast.datatype.ShortType import ShortType
from dynabuffers.ast.datatype.StringType import StringType, StringTypeOptions
from dynabuffers.ast.structural.Annotation import AnnotationOptions, Annotation
from dynabuffers.ast.structural.ClassOptions import ClassOptions, ClassOptionsOptions
from dynabuffers.ast.structural.FieldOptions import FieldOptions, FieldOptionsOptions
from dynabuffers.ast.structural.UnionOptions import UnionOptions, UnionOptionsOptions
from dynabuffers.ast.structural.Value import Value, ValueOptions


class DynabuffersVisitor(DynabuffersBaseVisitor):

    def visitEnumType(self, ctx: DynabuffersParser.EnumTypeContext):
        name = str(ctx.getChild(1))
        values = []
        for index in range(3, ctx.getChildCount() - 1):
            values.append(str(ctx.getChild(index)))

        return EnumType(EnumTypeOptions(name, values))

    def visitClassType(self, ctx: DynabuffersParser.ClassTypeContext):
        values = super().visitClassType(ctx)
        options = next(filter(lambda x: isinstance(x, ClassOptions), values), ClassOptions(ClassOptionsOptions(False, False, False)))

        fields = list(filter(lambda x: isinstance(x, FieldType), values))
        return ClassType(ClassTypeOptions(ctx.getChild(1).getText(), fields, options))

    def visitUnionType(self, ctx: DynabuffersParser.UnionTypeContext):
        values = []
        for i in range(3, ctx.getChildCount() - 1):
            # TODO: remove workaround
            if str(ctx.getChild(i)) != "{":
                values.append(str(ctx.getChild(i)))

        options = next(filter(lambda x: isinstance(x, UnionOptions), values), UnionOptions(UnionOptionsOptions(False, False, False)))

        return UnionType(UnionTypeOptions(str(ctx.getChild(1)), values, options))

    def visitFieldType(self, ctx: DynabuffersParser.FieldTypeContext):
        values = super().visitFieldType(ctx)
        annotations = list(filter(lambda x: isinstance(x, Annotation), values))
        name = ctx.getChild(len(annotations)).getText()
        datatype = values[len(annotations)]
        options = next(filter(lambda x: isinstance(x, FieldOptions), values), FieldOptions(FieldOptionsOptions(False)))
        defaultVal = next(map(lambda x: x.options.value, filter(lambda x: isinstance(x, Value), values)), None)

        return FieldType(FieldTypeOptions(name, datatype, options, annotations, defaultVal))

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
        if ctx.getText() == "map":
            return MapType(MapTypeOptions("utf-8"))
        else:
            return RefType(RefTypeOptions(ctx.getText()))

    def visitArrayType(self, ctx: DynabuffersParser.ArrayTypeContext):
        datatype = super().visitArrayType(ctx)[0]
        return ArrayType(ArrayTypeOptions(datatype))

    def visitOptionType(self, ctx: DynabuffersParser.OptionTypeContext):
        datatype = super().visitOptionType(ctx)[0]
        return OptionType(OptionTypeOptions(datatype))

    def visitClassOptions(self, ctx: DynabuffersParser.ClassOptionsContext):
        implicit = "implicit" in ctx.getText()
        return ClassOptions(ClassOptionsOptions("primary" in ctx.getText(), "deprecated" in ctx.getText(), implicit))

    def visitUnionOptions(self, ctx: DynabuffersParser.UnionOptionsContext):
        implicit = "implicit" in ctx.getText()
        return UnionOptions(UnionOptionsOptions("primary" in ctx.getText(), "deprecated" in ctx.getText(), implicit))

    def visitFieldOptions(self, ctx: DynabuffersParser.FieldOptionsContext):
        return FieldOptions(FieldOptionsOptions("deprecated" in ctx.getText()))

    def visitAnnotation(self, ctx: DynabuffersParser.AnnotationContext):
        values = super().visitAnnotation(ctx)
        return Annotation(AnnotationOptions(ctx.getChild(1).getText(), values))

    def visitNamespaceType(self, ctx: DynabuffersParser.NamespaceTypeContext):
        _name = ctx.getChild(1).getText()
        _list = list(filter(lambda x: not isinstance(x, NamespaceType), super().visitNamespaceType(ctx)))
        _nestedNamespaces = list(filter(lambda x: isinstance(x, NamespaceType), super().visitNamespaceType(ctx)))
        return NamespaceType(NamespaceTypeOptions(_name, _list), _nestedNamespaces)

    def visitValue(self, ctx: DynabuffersParser.ValueContext):
        if ctx.getText() == "[]":
            return Value(ValueOptions([]))
        if ctx.getText() == "[:]":
            return Value(ValueOptions({}))
        return Value(ValueOptions(ctx.getText()))

    def aggregateResult(self, aggregate, nextResult):
        array = []
        if aggregate is not None:
            array += aggregate
        if nextResult is not None:
            array += [nextResult]
        return array
