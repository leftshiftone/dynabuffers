from dynabuffers.api.ISerializable import ISerializable


class BytearrayType(ISerializable):

    def size(self, value, registry):
        return 4 + len(value)

    def serialize(self, value, buffer, registry):
        buffer.putInt(len(value))
        buffer.put(value)

    def deserialize(self, buffer, registry):
        length = buffer.getInt()
        array = buffer.get(length)

        return array
