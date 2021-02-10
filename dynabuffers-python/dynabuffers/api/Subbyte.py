from typing import List


def assert_only_n_first_bits_set(value: int, n: int, name: str):
    if ((0xFF >> (8 - n)) & value) != value:
        raise ValueError("Value {} of {} is too large for field size of {} bits.".format(value, name, n))
    return value


class Subbyte:

    def __init__(self, value: int, number_of_bits: int, name: str):
        """
        Creates and validates a Subbyte (value smaller or equal to a byte in size) with the given value and
        number_of_bits as size in bits.
        :param value: value of the Subbyte
        :param number_of_bits: the size of the Subbyte in bits
        :param name: name of the Subbyte used for logging errors
        :return: Subbyte
        """
        if number_of_bits > 8 or number_of_bits < 1:
            raise ValueError("Field size of {} bits for field {} must be in range 1-8.".format(number_of_bits, name))
        assert_only_n_first_bits_set(value, number_of_bits, name)
        self._value = value
        self._number_of_bits = number_of_bits

    @property
    def value(self):
        return self._value

    @property
    def number_of_bits(self):
        return self._number_of_bits

    @staticmethod
    def compress_values_into_bytes(values: List['Subbyte']) -> List[bytes]:
        result = []
        subbytes_for_byte = []
        total_bits = 0
        for subbyte in values:
            subbytes_for_byte.append(subbyte)
            total_bits += subbyte.number_of_bits
            if total_bits >= 8:
                result.append(Subbyte.compress_values_into_byte(subbytes_for_byte))
                subbytes_for_byte.clear()
                total_bits = 0
        if total_bits != 0:
            raise ValueError("Provided bits ({}) must be a multiple of 8.".format((len(result) * 8) + total_bits))
        return result

    @staticmethod
    def compress_values_into_byte(values: List['Subbyte']) -> bytes:
        """
        Compresses multiple Subbyte values into a byte value if they have the correct number of bits in total.
        :param values: list of Subbyte values
        :return: bytes
        :raise ValueError if the Subbytes values do not have 8 bits in total.
        """
        remaining_bits = 8
        result = 0
        for value in values:
            result |= value.value << (remaining_bits - value.number_of_bits)
            remaining_bits -= value.number_of_bits
        if remaining_bits != 0:
            raise ValueError("Subbyte values ({}) have invalid length in bits. "
                             "Expected: 8 Actual: {}".format(values, 8 - remaining_bits))
        return bytes([result])
