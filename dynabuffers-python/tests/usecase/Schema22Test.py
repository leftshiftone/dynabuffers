import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema22(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema22.dbs"))

    def test(self):
        values = [
            9223372036854775807,  # long
            -9223372036854775808,  # long
            2147483647,  # int
            -2147483648,  # int
            32767,  # short,
            -32768  # short
        ]
        for value in values:
            with self.subTest(value=value):
                obj = {"someMap": {"someValue": value}}
                result = self.engine.deserialize(self.engine.serialize(obj))
                self.assertEqual(result, obj)


if __name__ == "__main__":
    unittest.main()
