from typing import Union, Any, List

from dynabuffers import NAMESPACE_KEY
from dynabuffers.NamespaceResolver import NamespaceResolver
from dynabuffers.header.RootElement import RootElement
from dynabuffers.api.ISerializable import ISerializable, ByteBuffer
from dynabuffers.api.map.DynabuffersMap import DynabuffersMap
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
        self.namespace_resolver = NamespaceResolver(tree)
        self.listeners = []

    def add_listener(self, listener):
        self.listeners.append(listener)

    def serialize(self, value: Union[dict, Any], namespace_names: Union[List[str], str] = None) -> bytearray:
        if not isinstance(value, dict):
            return self.serialize({"value": value})

        if namespace_names is not None:
            if NAMESPACE_KEY in value:
                raise Exception(f"{NAMESPACE_KEY} is already present in dictionary - use serialize/1")
            if isinstance(namespace_names, List):
                value[NAMESPACE_KEY] = '.'.join(namespace_names)
            if isinstance(namespace_names, str):
                value[NAMESPACE_KEY] = namespace_names

        root_element = RootElement(self.tree)
        registry = Registry(self.tree, self.listeners)
        buffer = ByteBuffer(root_element.size(value, registry))
        root_element.serialize(value, buffer, registry)
        return buffer.toBytes()

    def deserialize(self, bytes: bytearray, namespaces: Union[List[str], str] = None) -> DynabuffersMap:
        """
        :param bytes:
        :param namespaces: only there for compat reasons => unused and deprecated!
        :return:
        """
        bb = ByteBuffer(len(bytes), bytes)
        registry = Registry(self.tree, self.listeners)
        return RootElement(self.tree).deserialize(bb, registry)


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
