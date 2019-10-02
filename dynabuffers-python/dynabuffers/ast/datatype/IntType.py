from dynabuffers.api.ISerializable import ISerializable


class IntType(ISerializable):

    def size(self, value, registry):
        return 4

    def serialize(self, value, buffer, registry):
        buffer.putInt(value)

    def deserialize(self, buffer, registry):
        return buffer.getInt()
