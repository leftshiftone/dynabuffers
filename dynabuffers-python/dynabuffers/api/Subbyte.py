from __future__ import annotations

from dataclasses import dataclass
from typing import List


def assert_only_n_first_bits_set(value: int, n: int, message: str = ""):
    if ((0xFF >> (8 - n)) & value) != value:
        raise ValueError(f"{message} Value {value} is too large for max field size {n} bit.")
    return value


@dataclass
class Subbyte:
    value: int
    number_of_bits: int

    @staticmethod
    def compress_values_into_bytes(values: List[Subbyte], message: str = "") -> List[bytes]:
        result = []
        subbytes_for_byte: List[Subbyte] = []
        total_bits = 0
        for subbyte in values:
            subbytes_for_byte.append(subbyte)
            total_bits += subbyte.number_of_bits
            if total_bits >= 8:
                result.append(Subbyte.compress_values_into_byte(subbytes_for_byte, message))
                subbytes_for_byte.clear()
                total_bits = 0
        return result

    @staticmethod
    def compress_values_into_byte(values: List[Subbyte], message: str = "") -> bytes:
        remaining_bits = 8
        result = 0
        for value in values:
            assert_only_n_first_bits_set(value.value, value.number_of_bits)
            result |= value.value << (remaining_bits - value.number_of_bits)
            remaining_bits -= value.number_of_bits
        if remaining_bits != 0:
            raise ValueError(
                f"{message} Subbyte values have invalid length in bits. Expected: 8 Actual: {8 - remaining_bits}")
        return bytes([result])
