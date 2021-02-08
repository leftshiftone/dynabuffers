import unittest
from typing import List

from parameterized import parameterized

from dynabuffers.api.Subbyte import Subbyte


class SubbyteTest(unittest.TestCase):

    @parameterized.expand([
        [0, 1],
        [0, 8],
        [255, 8],
        [127, 7],
        [15, 4],
        [14, 4]
    ])
    def test_create_valid_subbyte(self, value: int, number_of_bytes: int):
        Subbyte(value, number_of_bytes, "")

    @parameterized.expand([
        [0, 9],
        [-1, 8],
        [256, 8],
        [16, 4],
        [0, 0],
        [0, -1]
    ])
    def test_create_invalid_subbyte(self, value, number_of_bits):
        with self.assertRaises(ValueError):
            Subbyte(value, number_of_bits, "")

    @parameterized.expand([
        [[
            Subbyte(1, 1, "1"),
            Subbyte(1, 1, "2"),
            Subbyte(1, 1, "3"),
            Subbyte(1, 1, "4"),
            Subbyte(1, 1, "5"),
            Subbyte(1, 1, "6"),
            Subbyte(1, 1, "7"),
            Subbyte(1, 1, "8")
        ], 255],
        [[Subbyte(123, 8, "1-8")], 123],
        [[Subbyte(255, 8, "1-8")], 255],
        [[Subbyte(15, 4, "1-4"), Subbyte(0, 4, "5-8")], 240]
    ])
    def test_compress_subbytes_to_byte(self, values: List[Subbyte], resulting_byte: int):
        self.assertEqual(bytes([resulting_byte]), Subbyte.compress_values_into_byte(values))

    @parameterized.expand([
        [[
            Subbyte(1, 1, "1"),
            Subbyte(1, 1, "2"),
            Subbyte(1, 1, "3"),
            Subbyte(1, 1, "4"),
            Subbyte(1, 1, "5"),
            Subbyte(1, 1, "6"),
            Subbyte(1, 1, "7"),
            Subbyte(1, 1, "8"),
            Subbyte(1, 1, "9")
        ]],
        [[Subbyte(15, 4, "1-4"), Subbyte(0, 5, "5-9")]]
    ])
    def test_compress_invalid_number_of_subbytes_to_byte(self, values: List[Subbyte]):
        with self.assertRaises(ValueError):
            Subbyte.compress_values_into_byte(values)
