from dynabuffers.ast import AbstractAST
from dynabuffers.ast.AbstractAST import ByteBuffer
from dynabuffers.ast.ClassType import ClassType
from dynabuffers.ast.EnumType import EnumType
from dynabuffers.ast.UnionType import UnionType


class DynabuffersEngine(object):

    def __init__(self, tree: [AbstractAST]):
        self.tree = tree
        self.listeners = []

    def addListener(self, listener):
        self.listeners.append(listener)

    def getPrimaryClass(self) -> ClassType:
        classes = list(filter(lambda x: isinstance(x, ClassType), self.tree))
        for clazz in classes:
            if (clazz.options.primary):
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

    def __init__(self, tree: [AbstractAST], listeners):
        self.tree = tree
        self.listeners = listeners

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
