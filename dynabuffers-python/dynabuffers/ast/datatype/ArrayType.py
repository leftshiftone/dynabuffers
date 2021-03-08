from dynabuffers.api.ISerializable import ISerializable
from dynabuffers.ast.datatype.ByteType import ByteType


class ArrayTypeOptions:
    def __init__(self, datatype:ISerializable):
        self.datatype = datatype

class ArrayType(ISerializable):

    def __init__(self, options):
        self.options = options

    def size(self, value, registry):
        s = sum([self.options.datatype.size(item, registry) for item in value])
        return 4 + s

    def serialize(self, value, buffer, registry):
        buffer.putInt(len(value))
        for entry in value:
            self.options.datatype.serialize(entry, buffer, registry)

    def deserialize(self, buffer, registry):
        length = buffer.getInt()
        array = []

        for i in range(length):
            array.append(self.options.datatype.deserialize(buffer, registry))

        if isinstance(self.options.datatype, ByteType):
            return bytes(array)
        return array
