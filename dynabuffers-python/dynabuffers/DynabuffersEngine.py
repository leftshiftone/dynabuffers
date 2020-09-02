from typing import Union, Any, List

from dynabuffers.NamespaceResolver import NamespaceResolver, NamespaceDescription
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
        self.namespace_resolver = NamespaceResolver(tree)
        self.listeners = []

    def add_listener(self, listener):
        self.listeners.append(listener)

    def __get_root_type(self) -> ClassType:
        classes = list(filter(lambda x: isinstance(x, ClassType) or isinstance(x, UnionType), self.tree))
        for clazz in classes:
            if clazz.options.options.is_primary():
                return clazz

        if len(classes) == 0:
            raise ValueError("no root type found")
        return classes[0]

    def serialize(self, value: Union[dict, Any], namespace_names: Union[List[str], str] = None) -> bytearray:
        if not isinstance(value, dict):
            return self.serialize({"value": value})

        if namespace_names is not None:
            if ':namespace' in value:
                raise Exception(":namespace is already present in dictionary - use serialize/1")
            if isinstance(namespace_names, List):
                value[':namespace'] = '.'.join(namespace_names)
            if isinstance(namespace_names, str):
                value[':namespace'] = namespace_names
            return self.serialize(value)
        namespace_description = self.namespace_resolver.get_namespace(value)
        if namespace_description is not None:
            engine = DynabuffersEngine(namespace_description.namespace.options.list)
            return engine.__serialize_namespace_aware(value, namespace_description)


        clazz = self.__get_root_type()
        buffer = ByteBuffer(
            clazz.size(value, Registry(self.tree, self.listeners)) + 1)  # plus 1 for the namespace path length byte
        buffer.put(bytes([0]))  # no namespaces found - put 0 as namespace path length
        clazz.serialize(value, buffer, Registry(self.tree, self.listeners))

        return buffer.toBytes()

    def __serialize_namespace_aware(self, value: dict, namespace_description: NamespaceDescription) -> bytearray:
        clazz = self.__get_root_type()
        len_namespace_path = len(namespace_description.path)
        buffer = ByteBuffer(clazz.size(value, Registry(self.tree, self.listeners)) + 1 + len_namespace_path)
        buffer.put(bytes([len_namespace_path]))

        for waypoint in namespace_description.path:
            buffer.put(bytes([waypoint.position]))

        clazz.serialize(value, buffer, Registry(self.tree, self.listeners))
        return buffer.toBytes()


    def deserialize(self, bytes: bytearray, namespaces: Union[List[str], str] = None) -> DynabuffersMap:
        """
        :param bytes:
        :param namespaces: only there for compat reasons => unused and deprecated!
        :return:
        """
        namespace_description = self.namespace_resolver.get_namespace_from_serialized(bytes)
        if namespace_description is not None:
            engine = DynabuffersEngine(namespace_description.namespace.options.list)
            bytes_without_namespace_info = len(namespace_description.path) + 1
            return engine.__internal_deserialize(bytes[bytes_without_namespace_info:], namespace_description)
        return self.__internal_deserialize(bytes[1:], None)


    def __internal_deserialize(self, bb: bytearray, namespace_description: Union[NamespaceDescription, None]) -> DynabuffersMap:
        def _assemble() ->  DynabuffersMap:
            root = self.__get_root_type()
            result = root.deserialize(ByteBuffer(len(bb), bb), Registry(self.tree, self.listeners))
            if root.options.options.is_implicit:
                return ImplicitDynabuffersMap(result, self.tree, root)
            return DynabuffersMap(result, self.tree, root)
        map = _assemble()
        if namespace_description is not None:
            map[":namespace"] = namespace_description.joined_path()
        return map


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
