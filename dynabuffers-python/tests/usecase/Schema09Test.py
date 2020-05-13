import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema09Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))
    error = ["'i' format requires -2147483648 <= number <= 2147483647", "argument out of range"]

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(f"{self.root_dir}/schema09.dbs"))

    def test_int_positive_boundary(self):
        obj = {"val": 2147483647}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)

    def test_int_negative_boundary(self):
        obj = {"val": -2147483648}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)

    def test_int_overflow_positive(self):
        with self.assertRaises(Exception) as ctx:
            self.engine.deserialize(self.engine.serialize({"val": 2147483648}))

        self.assertTrue(str(ctx.exception) in self.error)

    def test_int_overflow_negative(self):
        with self.assertRaises(Exception) as ctx:
            self.engine.deserialize(self.engine.serialize({"val": -2147483649}))

        self.assertTrue(str(ctx.exception) in self.error)


if __name__ == "__main__":
    unittest.main()
