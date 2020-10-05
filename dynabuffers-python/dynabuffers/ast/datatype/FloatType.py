from dynabuffers.api.ISerializable import ISerializable


class FloatType(ISerializable):

    def size(self, value, registry):
        return 4

    def serialize(self, value, buffer, registry):
        buffer.putFloat(value if isinstance(value, float) else float(value))

    def deserialize(self, buffer, registry):
        return buffer.getFloat()
