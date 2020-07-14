from typing import List

from dynabuffers.api.ISerializable import ISerializable
from dynabuffers.ast.ClassType import ClassType
from dynabuffers.ast.UnionType import UnionType
from dynabuffers.ast.datatype.OptionType import OptionType
from dynabuffers.ast.datatype.RefType import RefType


class DynabuffersMap(dict):

    def __init__(self, map: dict, tree: List[ISerializable], root: ISerializable):
        super().__init__()
        self.raw_map = map
        self.update(map)
        self.tree = tree
        self.clazz = self.resolve_class(tree, root, map)

    def __getitem__(self, key):
        if not self.is_defined(key):
            raise ValueError("field " + str(key) + " is not defined in " + str(self.clazz.options.name))

        result = self.raw_map[key]
        if result is None and not self.is_optional(key):
            raise ValueError("field " + str(key) + " is not optional in " + str(self.clazz.options.name))

        if result is not None and self.is_ref_type(key):
            ref_type = self.get_ref_type(key)
            if ref_type is not None:
                return DynabuffersMap(result, self.tree, ref_type)

        return result

    def get_ref(self, key: str) -> 'DynabuffersMap':
        if isinstance(self[key], dict):
            return DynabuffersMap(self[key], self.tree, self.clazz)
        raise ValueError("field " + str(key) + " with result " + str(self[key]) + " is not a ref type")

    def get_string(self, key: str) -> str:
        if isinstance(self[key], str):
            return self[key]
        raise ValueError("field " + str(key) + " with result " + str(self[key]) + " is not a string type")

    def get_int(self, key: str) -> int:
        if isinstance(self[key], int):
            return self[key]
        raise ValueError("field " + str(key) + " with result " + str(self[key]) + " is not a int type")

    def get_short(self, key: str) -> int:
        if isinstance(self[key], int):
            return self[key]
        raise ValueError("field " + str(key) + " with result " + str(self[key]) + " is not a short type")

    def get_byte(self, key: str) -> int:
        if isinstance(self[key], int):
            return self[key]
        raise ValueError("field " + str(key) + " with result " + str(self[key]) + " is not a byte type")

    def get_long(self, key: str) -> int:
        if isinstance(self[key], int):
            return self[key]
        raise ValueError("field " + str(key) + " with result " + str(self[key]) + " is not a long type")

    def get_float(self, key: str) -> float:
        if isinstance(self[key], float):
            return self[key]
        raise ValueError("field " + str(key) + " with result " + str(self[key]) + " is not a float type")

    def get_map(self, key: str) -> int:
        if isinstance(self[key], dict):
            return self[key]
        raise ValueError("field " + str(key) + " with result " + str(self[key]) + " is not a map type")

    def get_bytearray(self, key: str) -> bytes:
        if isinstance(self[key], bytes):
            return self[key]
        raise ValueError("field " + str(key) + " with result " + str(self[key]) + " is not a bytearray type")

    def resolve_class(self, tree: List[ISerializable], root: ISerializable, source: dict):
        if isinstance(root, ClassType):
            return root
        if isinstance(root, UnionType):
            if source[":type"] is None:
                return list(filter(lambda x: x.options.name == root.options.values[0], tree))[0]

            type = source[":type"] if isinstance(source[":type"], str) else root.options.values[source[":type"]]
            return list(filter(lambda x: x.options.name == type, tree))[0]
        raise ValueError("cannot resolve class from " + str(root))

    def is_defined(self, key: str) -> bool:
        if key == ":type":
            return True
        return next((x for x in self.clazz.options.fields if x.options.name == key), False)

    def is_optional(self, key: str) -> bool:
        if key == ":type":
            return False
        field = next((x for x in self.clazz.options.fields if x.options.name == key), None)
        return isinstance(field.options.dataType, OptionType) if field is not None else False

    def is_ref_type(self, key: str) -> bool:
        if key == ":type":
            return False
        field = next((x for x in self.clazz.options.fields if x.options.name == key), None)
        return isinstance(field, RefType)

    def get_ref_type(self, key: str) -> ClassType:
        field = next((x for x in self.clazz.options.fields if x.options.name == key), None)
        name = field.options.dataType.options.name
        type = next((x for x in self.tree if x.options.name == name), False)

        source = self.raw_map[key]
        return self.resolve_class(self.tree, type, source) if isinstance(type, UnionType) else type