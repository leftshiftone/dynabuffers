from antlr4 import *

from dynabuffers.DynabuffersEngine import DynabuffersEngine
from dynabuffers.DynabuffersVisitor import DynabuffersVisitor
from dynabuffers.antlr.DynabuffersLexer import DynabuffersLexer
from dynabuffers.antlr.DynabuffersParser import DynabuffersParser


class Dynabuffers:
    @staticmethod
    def parse(schema: InputStream) -> DynabuffersEngine:
        lexer = DynabuffersLexer(schema)
        stream = CommonTokenStream(lexer)
        parser = DynabuffersParser(stream)

        visitor = DynabuffersVisitor()
        tree = visitor.visit(parser.compilation())

        return DynabuffersEngine(tree)
