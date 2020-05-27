# Generated from Dynabuffers.g4 by ANTLR 4.7.2
from antlr4 import *
if __name__ is not None and "." in __name__:
    from .DynabuffersParser import DynabuffersParser
else:
    from DynabuffersParser import DynabuffersParser

# This class defines a complete generic visitor for a parse tree produced by DynabuffersParser.

class DynabuffersVisitor(ParseTreeVisitor):

    # Visit a parse tree produced by DynabuffersParser#compilation.
    def visitCompilation(self, ctx:DynabuffersParser.CompilationContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DynabuffersParser#enumType.
    def visitEnumType(self, ctx:DynabuffersParser.EnumTypeContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DynabuffersParser#classType.
    def visitClassType(self, ctx:DynabuffersParser.ClassTypeContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DynabuffersParser#unionType.
    def visitUnionType(self, ctx:DynabuffersParser.UnionTypeContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DynabuffersParser#fieldType.
    def visitFieldType(self, ctx:DynabuffersParser.FieldTypeContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DynabuffersParser#dataType.
    def visitDataType(self, ctx:DynabuffersParser.DataTypeContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DynabuffersParser#arrayType.
    def visitArrayType(self, ctx:DynabuffersParser.ArrayTypeContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DynabuffersParser#optionType.
    def visitOptionType(self, ctx:DynabuffersParser.OptionTypeContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DynabuffersParser#classOptions.
    def visitClassOptions(self, ctx:DynabuffersParser.ClassOptionsContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DynabuffersParser#fieldOptions.
    def visitFieldOptions(self, ctx:DynabuffersParser.FieldOptionsContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DynabuffersParser#annotation.
    def visitAnnotation(self, ctx:DynabuffersParser.AnnotationContext):
        return self.visitChildren(ctx)


    # Visit a parse tree produced by DynabuffersParser#value.
    def visitValue(self, ctx:DynabuffersParser.ValueContext):
        return self.visitChildren(ctx)



del DynabuffersParser