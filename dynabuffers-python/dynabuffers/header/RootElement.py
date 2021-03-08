from typing import List, Union

from dynabuffers import NAMESPACE_KEY
from dynabuffers.DynabuffersEngine import Registry
from dynabuffers.header.DynabuffersVersion import DYNABUFFERS_VERSION
from dynabuffers.header.Header import Header
from dynabuffers.NamespaceResolver import NamespaceResolver, NamespaceDescription
from dynabuffers.api.ISerializable import ISerializable
from dynabuffers.api.map.DynabuffersMap import DynabuffersMap
from dynabuffers.api.map.ImplicitDynabuffersMap import ImplicitDynabuffersMap
from dynabuffers.ast.ClassType import ClassType
from dynabuffers.ast.UnionType import UnionType


class RootElement(ISerializable):
    def __init__(self, tree: [ISerializable], version: int = DYNABUFFERS_VERSION.get_major()):
        self._tree = tree
        self._version = version
        self._namespace_resolver = NamespaceResolver(tree)

    def size(self, value, registry):
        schema = self.__get_schema_for_namespace(value)
        header_size = Header(self._namespace_resolver, self._version).size(value, registry)
        content_size = self.__get_root_type(schema).size(value, registry.createForSchema(schema))
        return header_size + content_size

    def __get_schema_for_namespace(self, value: dict) -> List[ISerializable]:
        namespace_description = self._namespace_resolver.get_namespace(value)
        if namespace_description is not None:
            return namespace_description.namespace.options.list
        return self._tree

    def serialize(self, value, buffer: 'ByteBuffer', registry):
        header = Header(self._namespace_resolver, self._version)
        header.serialize(value, buffer, registry)
        schema = self.__get_schema_for_namespace(value)
        self.__get_root_type(schema).serialize(value, buffer, registry.createForSchema(schema))

    def deserialize(self, buffer: 'ByteBuffer', registry):
        header = Header(self._namespace_resolver, self._version).deserialize(buffer, registry)
        if header.version != self._version:
            raise ValueError("Dynabuffers version of serialized data does not match current version. "
                             "Expected: {}  Actual: {}".format(self._version, header.version))

        # TODO: Handle flag bits here

        if header.namespace_description is not None:
            schema = header.namespace_description.namespace.options.list
            new_root = RootElement(schema)
            new_registry = registry.createForSchema(schema)
            return new_root.__deserialize_content(buffer, new_registry, header.namespace_description)
        return self.__deserialize_content(buffer, registry, None)

    def __deserialize_content(self, buffer: 'ByteBuffer', registry,
                              namespace_description: Union[NamespaceDescription, None]):
        root_type = self.__get_root_type(self._tree)
        result = root_type.deserialize(buffer, registry)
        if namespace_description is not None:
            result[NAMESPACE_KEY] = namespace_description.joined_path()

        if root_type.options.options.is_implicit:
            return ImplicitDynabuffersMap(result, self._tree, root_type)
        return DynabuffersMap(result, self._tree, root_type)

    def __get_root_type(self, schema: [ISerializable]) -> ClassType:
        classes = list(filter(lambda x: isinstance(x, ClassType) or isinstance(x, UnionType), schema))
        for clazz in classes:
            if clazz.options.options.is_primary():
                return clazz

        if len(classes) == 0:
            raise ValueError("no root type found")
        return classes[0]
