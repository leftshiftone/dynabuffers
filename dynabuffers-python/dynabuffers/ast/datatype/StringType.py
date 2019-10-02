from dynabuffers.api.ISerializable import ISerializable


class StringTypeOptions:
    def __init__(self, charset: str):
        self.charset = charset


class StringType(ISerializable):
    def __init__(self, options: StringTypeOptions):
        self.options = options

    def size(self, value, registry):
        return 2 + len(str(value).encode(self.options.charset))

    def serialize(self, value, buffer, registry):
        buffer.putShort(len(value))
        buffer.put(value.encode(self.options.charset))

    def deserialize(self, buffer, registry):
        length = buffer.getShort()
        array = buffer.get(length)

        return array.decode(self.options.charset)
