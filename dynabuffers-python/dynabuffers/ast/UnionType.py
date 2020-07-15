from dynabuffers.api.ISerializable import ISerializable, ByteBuffer
from dynabuffers.ast.ClassType import ClassType
from dynabuffers.ast.structural.UnionOptions import UnionOptions


class UnionTypeOptions():
    def __init__(self, name: str, values: [str], options: UnionOptions):
        self.name = name
        self.values = values
        self.options = options


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

        map = clazz.deserialize(buffer, registry)
        map[":type"] = index
        return map

    def resolve(self, value, registry) -> ClassType:
        classes = list(map(lambda x: registry.resolve(x), self.options.values))

        for clazz in classes:
            fields1 = sorted(list(map(lambda x: x.options.name, clazz.options.fields)))
            fields2 = sorted(list(value.keys()))

            if ":type" in value and value[":type"] == classes.index(clazz):
                return clazz
            if ":type" in value and value[":type"] in self.options.values \
                    and self.options.values.index(value[":type"]) == classes.index(clazz):
                return clazz
            if fields1 == fields2:
                return clazz

        raise ValueError("union type " + str(value) + " cannot be resolved")
