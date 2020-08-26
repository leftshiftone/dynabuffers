from typing import List, Union

from dynabuffers.api.ISerializable import ISerializable
from dynabuffers.ast.NamespaceType import NamespaceType


class NamespaceResolver:
    def __init__(self, tree: List[ISerializable]):
        self.tree = tree

    def get_nested_namespace(self, names: Union[List[str], str, None]) -> Union[NamespaceType, None]:
        if names is None:
            return self.__get_default_namespace()
        elif not isinstance(names, list):
            names = [names]
        return self.__get_namespace(names, self.__get_namespaces_from_root_level())

    def __get_default_namespace(self) -> Union[NamespaceType, None]:
        namespaces = self.__get_namespaces_from_root_level()
        if len(namespaces) != 1:
            return None
        while len(namespaces[0].nestedNamespaces) == 1:
            namespaces = namespaces[0].nestedNamespaces
        return namespaces[0]

    def __get_namespaces_from_root_level(self) -> List[NamespaceType]:
        return list(filter(lambda x: isinstance(x, NamespaceType), self.tree))

    def __get_namespace(self, names: [str], namespaces: [NamespaceType]) -> NamespaceType:
        ns_name = names[0]
        ns = self.__find_namespace_by_name(ns_name, namespaces)
        if len(names) == 1:
            return ns
        return self.__get_namespace(names[1:], ns.nestedNamespaces)

    def __find_namespace_by_name(self, name: str, namespaces: [NamespaceType]) -> NamespaceType:
        namespace = next((x for x in namespaces if x.options.name == name), None)
        if namespace is None:
            raise ValueError("no namespace with name " + str(name) + " found")
        return namespace
