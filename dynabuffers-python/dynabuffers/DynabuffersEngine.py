from dynabuffers.api.ISerializable import ISerializable, ByteBuffer
from dynabuffers.ast.ClassType import ClassType
from dynabuffers.ast.EnumType import EnumType
from dynabuffers.ast.UnionType import UnionType
from dynabuffers.ast.annotation.GreaterEquals import GreaterEquals
from dynabuffers.ast.annotation.GreaterThan import GreaterThan
from dynabuffers.ast.annotation.LowerEquals import LowerEquals
from dynabuffers.ast.annotation.LowerThan import LowerThan
from dynabuffers.ast.annotation.MaxLength import MaxLength
from dynabuffers.ast.annotation.MinLength import MinLength
from dynabuffers.ast.annotation.NotBlank import NotBlank


class DynabuffersEngine(object):

    def __init__(self, tree: [ISerializable]):
        self.tree = tree
        self.listeners = []

    def addListener(self, listener):
        self.listeners.append(listener)

    def getPrimaryClass(self) -> ClassType:
        classes = list(filter(lambda x: isinstance(x, ClassType), self.tree))
        for clazz in classes:
            if clazz.options.options.isPrimary():
                return clazz

        return classes[0]

    def serialize(self, map: dict) -> bytearray:
        clazz = self.getPrimaryClass()
        buffer = ByteBuffer(clazz.size(map, Registry(self.tree, self.listeners)))
        clazz.serialize(map, buffer, Registry(self.tree, self.listeners))

        return buffer.toBytes()

    def deserialize(self, bytes: bytearray) -> dict:
        clazz = self.getPrimaryClass()
        return clazz.deserialize(ByteBuffer(len(bytes), bytes), Registry(self.tree, self.listeners))


class Registry(object):

    def __init__(self, tree: [ISerializable], listeners):
        self.tree = tree
        self.listeners = listeners

    def resolveAnnotation(self, name, args):
        if name == "GreaterThan":
            return GreaterThan(args)
        if name == "GreaterEquals":
            return GreaterEquals(args)
        if name == "LowerThan":
            return LowerThan(args)
        if name == "LowerEquals":
            return LowerEquals(args)
        if name == "MaxLength":
            return MaxLength(args)
        if name == "MinLength":
            return MinLength(args)
        if name == "NotBlank":
            return NotBlank(args)
        raise ValueError("unknown annotation " + str(name))

    def resolve(self, name: str):
        for element in self.tree:
            if isinstance(element, ClassType) and element.options.name == name:
                return element
            if isinstance(element, EnumType) and element.options.name == name:
                return element
            if isinstance(element, UnionType) and element.options.name == name:
                return element

    def addNotification(self, notification: str):
        for listener in self.listeners:
            listener(notification)
