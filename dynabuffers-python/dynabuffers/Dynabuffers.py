from antlr4 import *

from dynabuffers.DynabuffersEngine import DynabuffersEngine
from dynabuffers.DynabuffersVisitor import DynabuffersVisitor
from dynabuffers.antlr.DynabuffersLexer import DynabuffersLexer
from dynabuffers.antlr.DynabuffersParser import DynabuffersParser


class Dynabuffers:
    @staticmethod
    def parse(schema) -> DynabuffersEngine:
        lexer = DynabuffersLexer(Dynabuffers.stream(schema))
        stream = CommonTokenStream(lexer)
        parser = DynabuffersParser(stream)

        visitor = DynabuffersVisitor()
        tree = visitor.visit(parser.compilation())

        return DynabuffersEngine(tree)

    @staticmethod
    def stream(obj):
        if isinstance(obj, InputStream):
            return obj
        if isinstance(obj, str):
            return InputStream(str(obj))
        if isinstance(obj, bytes):
            return InputStream(obj.decode("utf-8"))
        if isinstance(obj, bytearray):
            return InputStream(obj.decode("utf-8"))
        raise ValueError(str(obj) + " cannot be converted to input stream")
