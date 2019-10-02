from dynabuffers.api.ISerializable import ISerializable, ByteBuffer


class EnumTypeOptions():
    def __init__(self, name: str, values: [str]):
        self.name = name
        self.values = values


class EnumType(ISerializable):

    def __init__(self, options):
        self.options = options

    def size(self, value, registry):
        return 1 + len(str(value).encode("utf-8"))

    def serialize(self, value, buffer: ByteBuffer, registry):
        array = str(value).encode("utf-8")
        buffer.put(bytes([len(array)]))
        buffer.put(array)

    def deserialize(self, buffer, registry):
        length = buffer.get()
        array = buffer.get(length)

        return array.decode("utf-8")
