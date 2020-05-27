from dynabuffers.api.ISerializable import ISerializable


class OptionTypeOptions:
    def __init__(self, datatype: ISerializable):
        self.datatype = datatype


class OptionType(ISerializable):

    def __init__(self, options: OptionTypeOptions):
        self.options = options

    def size(self, value, registry):
        return 1 + (0 if value is None else self.options.datatype.size(value, registry))

    def serialize(self, value, buffer, registry):
        buffer.put(bytes([0 if value is None else 1]))
        if value is not None:
            self.options.datatype.serialize(value, buffer, registry)

    def deserialize(self, buffer, registry):
        if buffer.get() == 0:
            return None
        return self.options.datatype.deserialize(buffer, registry)
