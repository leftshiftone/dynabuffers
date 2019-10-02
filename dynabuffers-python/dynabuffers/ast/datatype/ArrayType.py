from dynabuffers.api.ISerializable import ISerializable
from dynabuffers.ast.datatype.ByteType import ByteType


class ArrayTypeOptions:
    def __init__(self, datatype:ISerializable):
        self.datatype = datatype

class ArrayType(ISerializable):

    def __init__(self, options):
        self.options = options

    def size(self, value, registry):
        return 2 + (len(value) * self.options.datatype.size(value, registry))

    def serialize(self, value, buffer, registry):
        buffer.putShort(len(value))
        for entry in value:
            self.options.datatype.serialize(entry, buffer, registry)

    def deserialize(self, buffer, registry):
        length = buffer.getShort()
        array = []

        for i in range(length):
            array.append(self.options.datatype.deserialize(buffer, registry))

        if isinstance(self.options.datatype, ByteType):
            return bytes(array)
        return array
