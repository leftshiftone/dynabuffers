from typing import Union, Any

from dynabuffers.api.ISerializable import ISerializable, ByteBuffer
from dynabuffers.api.map.DynabuffersMap import DynabuffersMap
from dynabuffers.api.map.ImplicitDynabuffersMap import ImplicitDynabuffersMap
from dynabuffers.ast.ClassType import ClassType
from dynabuffers.ast.EnumType import EnumType
from dynabuffers.ast.NamespaceType import NamespaceType
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

    def add_listener(self, listener):
        self.listeners.append(listener)

    def get_root_type(self) -> ClassType:
        classes = list(filter(lambda x: isinstance(x, ClassType) or isinstance(x, UnionType), self.tree))
        for clazz in classes:
            if clazz.options.options.is_primary():
                return clazz

        if len(classes) == 0:
            raise ValueError("no root type found")
        return classes[0]

    def get_namespace(self, name: str) -> NamespaceType:
        namespace = next((x for x in self.tree if x.options.name == name), None)
        if namespace is None:
            raise ValueError("no namespace with name " + str(name) + " found")
        return namespace

    def serialize(self, value: Union[dict, Any], namespace_name:str = None) -> bytearray:
        if namespace_name is not None:
            namespace = self.get_namespace(namespace_name)
            engine = DynabuffersEngine(namespace.options.list)
            return engine.serialize(value)

        if not isinstance(value, dict):
            return self.serialize({"value": value})

        clazz = self.get_root_type()
        buffer = ByteBuffer(clazz.size(value, Registry(self.tree, self.listeners)))
        clazz.serialize(value, buffer, Registry(self.tree, self.listeners))

        return buffer.toBytes()

    def deserialize(self, bytes: bytearray, namespace_name:str = None) -> DynabuffersMap:
        if namespace_name is not None:
            namespace = self.get_namespace(namespace_name)
            engine = DynabuffersEngine(namespace.options.list)
            return engine.deserialize(bytes)

        root = self.get_root_type()
        result = root.deserialize(ByteBuffer(len(bytes), bytes), Registry(self.tree, self.listeners))
        if root.options.options.is_implicit:
            return ImplicitDynabuffersMap(result, self.tree, root)
        return DynabuffersMap(result, self.tree, root)

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
