from dynabuffers.api.ISerializable import ISerializable


class ShortType(ISerializable):

    def size(self, value, registry):
        return 2

    def serialize(self, value, buffer, registry):
        buffer.putShort(value if isinstance(value, int) else int(value))

    def deserialize(self, buffer, registry):
        return buffer.getShort()
