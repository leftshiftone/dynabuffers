from dynabuffers.api.ISerializable import ISerializable


class NoneType(ISerializable):

    def size(self, value, registry):
        return 0

    def serialize(self, value, buffer, registry):
        pass

    def deserialize(self, buffer, registry):
        return None
