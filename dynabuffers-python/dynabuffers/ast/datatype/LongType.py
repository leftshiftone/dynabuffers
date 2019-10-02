from dynabuffers.api.ISerializable import ISerializable


class LongType(ISerializable):

    def size(self, value, registry):
        return 8

    def serialize(self, value, buffer, registry):
        buffer.putFloat(value)

    def deserialize(self, buffer, registry):
        return buffer.getFloat()
