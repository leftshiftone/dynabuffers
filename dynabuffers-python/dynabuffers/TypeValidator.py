from typing import Union

from antlr4 import ParserRuleContext

from dynabuffers.antlr.DynabuffersParser import DynabuffersParser


class DynabuffersTypeValidator:
    def __init__(self):
        self.visited_data_types = set()
        self.available_data_types = set()

    def validate(self):
        invalid_datatypes = self.visited_data_types - self.available_data_types
        if len(invalid_datatypes) > 0:
            raise ValueError(
                "The datatype(s) {} used in the schema are neither built-in datatypes nor class, union or enum "
                "references.".format(invalid_datatypes))

    def add_available_type(self, ctx: Union[
        DynabuffersParser.ClassTypeContext, DynabuffersParser.UnionTypeContext, DynabuffersParser.EnumTypeContext]):
        name = ctx.getChild(1).getText()
        prefix = self.__get_namespace_prefix(ctx)
        self.available_data_types.add(prefix + name)

    def add_used_type(self, ctx: DynabuffersParser.DataTypeContext):
        name = ctx.getText()
        prefix = self.__get_namespace_prefix(ctx)
        self.visited_data_types.add(prefix + name)

    def __get_namespace_prefix(self, ctx: ParserRuleContext):
        prefix = ""
        while not isinstance(ctx.parentCtx, DynabuffersParser.CompilationContext):
            ctx = ctx.parentCtx
            if isinstance(ctx, DynabuffersParser.NamespaceTypeContext):
                prefix = "{}.{}".format(ctx.getText(), prefix)
        return prefix
