from dynabuffers.ast.AbstractAST import AbstractAST


class ByteType(AbstractAST):

    def size(self, value, registry):
        return 1

    def serialize(self, value, buffer, registry):
        buffer.put(bytes([value]))

    def deserialize(self, buffer, registry):
        return buffer.get()
