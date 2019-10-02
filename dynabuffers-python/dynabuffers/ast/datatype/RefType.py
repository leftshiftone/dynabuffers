from dynabuffers.api.ISerializable import ISerializable


class RefTypeOptions:
    def __init__(self, name: str):
        self.name = name


class RefType(ISerializable):
    def __init__(self, options: RefTypeOptions):
        self.options = options

    def size(self, value, registry):
        return registry.resolve(self.options.name).size(value, registry)

    def serialize(self, value, buffer, registry):
        registry.resolve(self.options.name).serialize(value, buffer, registry)

    def deserialize(self, buffer, registry):
        return registry.resolve(self.options.name).deserialize(buffer, registry)
