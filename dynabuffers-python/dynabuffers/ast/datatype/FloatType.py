from dynabuffers.ast.AbstractAST import AbstractAST


class FloatType(AbstractAST):

    def size(self, value, registry):
        return 4

    def serialize(self, value, buffer, registry):
        buffer.putFloat(value)

    def deserialize(self, buffer, registry):
        return buffer.getFloat()
