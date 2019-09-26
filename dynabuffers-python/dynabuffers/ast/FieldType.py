from dynabuffers.ast.AbstractAST import ByteBuffer, AbstractAST


class FieldTypeOptions():
    def __init__(self, name: str, datatype: AbstractAST, deprecated: bool, defaultVal):
        self.name = name
        self.datatype = datatype
        self.deprecated = deprecated
        self.defaultVal = defaultVal


class FieldType(AbstractAST):

    def __init__(self, options: FieldTypeOptions):
        self.options = options

    def size(self, value, registry):
        return 1 + len(str(value).encode("utf-8"))

    def serialize(self, value, buffer: ByteBuffer, registry):
        if self.options.deprecated:
            registry.addNotification("deprecated field " + self.options.name + " used")

        self.options.datatype.serialize(value, buffer, registry)

    def deserialize(self, buffer, registry):
        return self.options.datatype.deserialize(buffer, registry)
