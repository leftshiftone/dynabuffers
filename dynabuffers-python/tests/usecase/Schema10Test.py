import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema10Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))
    error = "int too large to convert"

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(f"{self.root_dir}/schema10.dbs"))

    def test_long_positive_boundary(self):
        obj = {"val": 9223372036854775807}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)

    def test_long_negative_boundary(self):
        obj = {"val": -9223372036854775808}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)

    def test_long_overflow_positive(self):
        with self.assertRaises(Exception) as ctx:
            self.engine.deserialize(self.engine.serialize({"val": 9223372036854775808}))

        self.assertEqual(self.error, str(ctx.exception))

    def test_long_overflow_negative(self):
        with self.assertRaises(Exception) as ctx:
            self.engine.deserialize(self.engine.serialize({"val": -9223372036854775809}))

        self.assertEqual(self.error, str(ctx.exception))


if __name__ == "__main__":
    unittest.main()
