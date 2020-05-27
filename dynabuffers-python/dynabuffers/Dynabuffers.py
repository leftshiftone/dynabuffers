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
        if isinstance(obj, DynabuffersResource):
            return obj.get_content_stream()
        raise ValueError(str(obj) + " cannot be converted to input stream")


class DynabuffersResource:

    def __init__(self):
        self.builder = ""

    def append(self, schema):
        if isinstance(schema, str):
            self.builder += str(schema)
        if isinstance(schema, bytes):
            self.builder += schema.decode("utf-8")
        if isinstance(schema, bytearray):
            self.builder += schema.decode("utf-8")
        raise ValueError(str(schema) + " cannot be appended to resource")

    def get_content_stream(self):
        return InputStream(self.builder)
