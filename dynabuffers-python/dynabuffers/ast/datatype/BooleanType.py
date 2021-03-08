from dynabuffers.api.ISerializable import ISerializable


class BooleanType(ISerializable):

    def size(self, value, registry):
        return 1

    def serialize(self, value, buffer, registry):
        if value:
            buffer.put(bytes([1]))
        else:
            buffer.put(bytes([0]))

    def deserialize(self, buffer, registry):
        if buffer.get() == 0:
            return False
        else:
            return True
