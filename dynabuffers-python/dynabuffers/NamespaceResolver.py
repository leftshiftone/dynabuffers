from math import ceil
from typing import List, Union, Any

from dynabuffers.api.ISerializable import ISerializable, ByteBuffer
from dynabuffers.ast.NamespaceType import NamespaceType


class Waypoint:
    def __init__(self, name: str, position: int):
        self._name = name
        self._position = position

    @property
    def name(self):
        return self._name

    @property
    def position(self):
        return self._position


class NamespaceDescription:
    def __init__(self, namespace: NamespaceType, path: List[Waypoint]):
        self._namespace = namespace
        self._path = path

    @property
    def namespace(self):
        return self._namespace

    @property
    def path(self):
        return self._path

    def joined_path(self) -> str:
        return ".".join(list(map(lambda waypoint: waypoint.name, self._path)))


class NamespaceResolver:
    def __init__(self, tree: List[ISerializable]):
        self.tree = tree

    def get_namespace_from_names(self, names: Union[List[str], None]) -> Union[NamespaceDescription, None]:
        if names is None:
            return self._infer_default_namespace()
        r = self.__get_namespace(names, self.__get_namespaces_from_root_level())
        return r

    def get_namespace(self, d: Union[dict, Any]) -> Union[NamespaceDescription, None]:
        namespaces = d.get(':namespace')
        if namespaces is not None:
            return self.get_namespace_from_names(namespaces.split('.'))
        return self._infer_default_namespace()

    def get_namespace_from_serialized(self, bb: ByteBuffer, len_namespace_path: int) -> Union[
        NamespaceDescription, None]:
        """
        Reads the encoded namespace path directly from the given byte buffer
        WARNING: Modifies the provided byte buffer
        :param bb: byte buffer of serialized payload
        :param len_namespace_path: denotes the length of the namespace path (in terms of namespaces)
        :return:
        """
        if len_namespace_path == 0:
            return None

        namespace_bytes = [bb.get() for _ in range(ceil(len_namespace_path / 2))]
        waypoints = []
        # Read waypoints from bytes, 2 waypoints per byte
        for byte in namespace_bytes:
            waypoints.append(byte & 0xF0)
            waypoints.append(byte & 0x0F)
        waypoints = waypoints[:len_namespace_path]

        # map the read path to the namespaces within the schema - sorry this is not very elegant
        namespaces = list(map(lambda nd: nd.namespace, self.__get_namespaces_from_root_level()))
        path = []
        for waypoint in waypoints:
            namespace = namespaces[waypoint]
            namespaces = namespace.nestedNamespaces
            path.append(NamespaceDescription(namespace, [Waypoint(namespace.options.name, waypoint)]))

        return NamespaceDescription(path[-1:][0].namespace, list(map(lambda n: n.path[0], path)))

    def __get_namespace(self, names: List[str], namespaces: List[NamespaceDescription]) -> Union[
        NamespaceDescription, None]:
        ns_name = names[0]
        ns = self.__find_namespace_by_name(ns_name, namespaces)
        if len(names) == 1:
            return ns

        nested = [NamespaceDescription(n, ns.path + [Waypoint(n.options.name, idx)]) for idx, n in
                  enumerate(ns.namespace.nestedNamespaces)]
        return self.__get_namespace(names[1:], nested)

    def __get_namespaces_from_root_level(self) -> List[NamespaceDescription]:
        namespace_types = list(filter(lambda x: isinstance(x, NamespaceType), self.tree))
        return [NamespaceDescription(ns, [Waypoint(ns.options.name, idx)]) for idx, ns in enumerate(namespace_types)]

    def __find_namespace_by_name(self, name: str, namespaces: [NamespaceDescription]) -> NamespaceDescription:
        namespace = next((x for x in namespaces if x.namespace.options.name == name), None)
        if namespace is None:
            raise ValueError("no namespace with name " + str(name) + " found")
        return namespace

    def _infer_default_namespace(self) -> Union[NamespaceDescription, None]:
        """
        infers the namespace from the schema - multiple nested namespaces will cause this method to raise an exception.
        :return:
        """

        def _resolve(namespaces: List[NamespaceDescription]) -> Union[NamespaceDescription, None]:
            if len(namespaces) == 0:
                return None
            if len(namespaces) > 1:
                raise ValueError("Could not infer default namespace")
            candidate = namespaces[0]
            if len(candidate.namespace.nestedNamespaces) == 0:
                return candidate
            new_candidates = [NamespaceDescription(ns, candidate.path + [Waypoint(ns.options.name, idx)]) for idx, ns in
                              enumerate(candidate.namespace.nestedNamespaces)]
            return _resolve(new_candidates)

        return _resolve(self.__get_namespaces_from_root_level())
