from dynabuffers.ast.AbstractAST import AbstractAST


class IntType(AbstractAST):

    def size(self, value, registry):
        return 4

    def serialize(self, value, buffer, registry):
        buffer.putInt(value)

    def deserialize(self, buffer, registry):
        return buffer.getInt()
