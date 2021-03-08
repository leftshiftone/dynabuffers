from dynabuffers.api.ISerializable import ISerializable, ByteBuffer
from dynabuffers.ast import FieldType
from dynabuffers.ast.datatype.OptionType import OptionType
from dynabuffers.ast.structural import ClassOptions


class ClassTypeOptions:
    def __init__(self, name, fields: [FieldType], options: ClassOptions):
        self.name = name
        self.fields = fields
        self.options = options


class ClassType(ISerializable):

    def __init__(self, options: ClassTypeOptions):
        self.options = options

    def size(self, value, registry):
        for field in self.options.fields:
            if field.options.defaultVal is None and field.options.name not in value:
                if not isinstance(field.options.datatype, OptionType):
                    raise ValueError("field '" + str(field.options.name) + "' is missing")
                else:
                    value[field.options.name] = None
            if field.options.defaultVal is None and value[field.options.name] is None:
                if not isinstance(field.options.datatype, OptionType):
                    raise ValueError("field '" + str(field.options.name) + "' is missing")
                else:
                    value[field.options.name] = None

        size = 0
        for field in self.options.fields:
            if field.options.name in value:
                size += field.size(value[field.options.name], registry)
            elif field.options.defaultVal is not None:
                size += field.size(field.options.defaultVal, registry)
        return size

    def serialize(self, value, buffer: ByteBuffer, registry):
        if self.options.options.is_deprecated():
            registry.addNotification("deprecated class " + self.options.name + " used")

        for field in self.options.fields:
            if not isinstance(field.options.datatype, OptionType):
                if field.options.defaultVal is None and field.options.name not in value:
                    raise ValueError("field '" + str(field.options.name) + "' is missing")
                if field.options.defaultVal is None and value[field.options.name] is None:
                    raise ValueError("field '" + str(field.options.name) + "' is missing")

        for element in list(
                filter(lambda x: (x.options.name in value) or x.options.defaultVal is not None, self.options.fields)):
            element.serialize(
                value[element.options.name] if element.options.name in value else element.options.defaultVal, buffer,
                registry)

    def deserialize(self, buffer, registry):
        map = {}
        for field in self.options.fields:
            map[field.options.name] = field.deserialize(buffer, registry)

        return map
