from typing import Union, Any, List

from dynabuffers import NAMESPACE_KEY
from dynabuffers.NamespaceResolver import NamespaceResolver
from dynabuffers.Registry import Registry
from dynabuffers.api.ISerializable import ISerializable, ByteBuffer
from dynabuffers.api.map.DynabuffersMap import DynabuffersMap
from dynabuffers.header.RootElement import RootElement


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
                raise Exception("{} is already present in dictionary - use serialize/1".format(NAMESPACE_KEY))
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
