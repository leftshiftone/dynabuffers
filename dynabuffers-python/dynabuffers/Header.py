from math import ceil
from typing import List, Union

from dynabuffers.NamespaceResolver import NamespaceDescription, NamespaceResolver
from dynabuffers.api.ISerializable import ISerializable
from dynabuffers.api.Subbyte import Subbyte


class HeaderSpec:
    def __init__(self, version: int, reserved: int, namespace_description: NamespaceDescription):
        self._version = version
        self._reserved = reserved
        self._namespace_description = namespace_description

    @property
    def version(self):
        return self._version

    @property
    def reserved(self):
        return self._reserved

    @property
    def namespace_description(self):
        return self._namespace_description


class Header(ISerializable):
    _number_of_reserved_bits = 5

    def __init__(self, namespace_resolver: NamespaceResolver, version: int, reserved: int = 0):
        self._namespace_resolver = namespace_resolver
        self._version = version
        self._reserved = reserved

    def size(self, value, registry):
        namespace_path_size = len(self.get_namespace_path(value))
        return 2 + ceil(namespace_path_size / 2.0)

    def get_namespace_path(self, value: Union[dict, any]) -> List[int]:
        namespace_description = self._namespace_resolver.get_namespace(value)
        if namespace_description is None:
            return []
        return list(map(lambda waypoint: waypoint.position, namespace_description.path))

    def serialize(self, value, buffer: 'ByteBuffer', registry):
        # 1. Byte: Version
        buffer.put(bytes([self._version]))

        # 2. Byte: Namespace depth + reserved bits
        namespace_path = self.get_namespace_path(value)
        namespace_depth = Subbyte(len(namespace_path), 8 - self._number_of_reserved_bits)
        reserved_bits = Subbyte(self._reserved, self._number_of_reserved_bits)
        buffer.put(Subbyte.compress_values_into_byte([namespace_depth, reserved_bits]))

        # 3. - 6. Byte: Namespace path
        for byte in self.compress_4_bit_values_to_bytes(namespace_path):
            buffer.put(byte)

    def deserialize(self, buffer: 'ByteBuffer', registry):
        deserialized_version = buffer.get()

        second_byte = buffer.get()
        reserved_bits = second_byte & (0xFF >> (8 - self._number_of_reserved_bits))
        namespace_depth = second_byte >> self._number_of_reserved_bits

        namespace_description = self._namespace_resolver.get_namespace_from_serialized(buffer, namespace_depth)
        return HeaderSpec(deserialized_version, reserved_bits, namespace_description)

    def compress_4_bit_values_to_bytes(self, bits: List[int]) -> List[bytes]:
        bits = bits[:]
        if len(bits) % 2 == 1:
            bits.append(0)
        subbytes = [Subbyte(subbyte, 4) for subbyte in bits]
        return Subbyte.compress_values_into_bytes(subbytes, "Maximum number of namespaces on the same level exceeded.")
