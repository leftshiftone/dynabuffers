from dynabuffers.api.ISerializable import ISerializable, ByteBuffer
from dynabuffers.ast.structural import FieldOptions


class FieldTypeOptions():
    def __init__(self, name: str, datatype: ISerializable, options: FieldOptions, annotations, defaultVal):
        self.name = name
        self.datatype = datatype
        self.options = options
        self.annotations = annotations
        self.defaultVal = defaultVal


class FieldType(ISerializable):

    def __init__(self, options: FieldTypeOptions):
        self.options = options

    def size(self, value, registry):
        return 1 + len(str(value).encode("utf-8"))

    def serialize(self, value, buffer: ByteBuffer, registry):
        if self.options.options:
            registry.addNotification("deprecated field " + self.options.name + " used")

        for annotation in list(map(lambda x: registry.resolveAnnotation(x.options.name, x.options.values), self.options.annotations)):
            annotation.validate(self.options.name, value)

        self.options.datatype.serialize(value, buffer, registry)

    def deserialize(self, buffer, registry):
        return self.options.datatype.deserialize(buffer, registry)
