from dynabuffers.api.ISerializable import ISerializable, ByteBuffer
from dynabuffers.ast.ClassType import ClassType


class UnionTypeOptions():
    def __init__(self, name: str, values: [str]):
        self.name = name
        self.values = values


class UnionType(ISerializable):

    def __init__(self, options):
        self.options = options

    def size(self, value, registry):
        clazz = self.resolve(value, registry)
        return 1 + clazz.size(value, registry)

    def serialize(self, value, buffer: ByteBuffer, registry):
        clazz = self.resolve(value, registry)
        buffer.put(bytes([self.options.values.index(clazz.options.name)]))

        return clazz.serialize(value, buffer, registry)

    def deserialize(self, buffer, registry):
        index = buffer.get()
        clazz = registry.resolve(self.options.values[index])

        return clazz.deserialize(buffer, registry)

    def resolve(self, value, registry) -> ClassType:
        classes = list(map(lambda x: registry.resolve(x), self.options.values))

        for clazz in classes:
            fields1 = list(map(lambda x: x.options.name, clazz.options.fields))
            fields2 = list(value.keys())

            if fields1 == fields2:
                return clazz

        raise ValueError("union type " + str(value) + " cannot be resolved")
