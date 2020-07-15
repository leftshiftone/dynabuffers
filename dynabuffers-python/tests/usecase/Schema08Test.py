import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema08Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))
    error = "'h' format requires -32768 <= number <= 32767"

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema08.dbs"))

    def test_short_positive_boundary(self):
        obj = {"val": 32767}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)

    def test_short_negative_boundary(self):
        obj = {"val": -32768}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)

    def test_short_overflow_positive(self):
        with self.assertRaises(Exception) as ctx:
            self.engine.deserialize(self.engine.serialize({"val": 32768}))

        self.assertEqual(self.error, str(ctx.exception))

    def test_short_overflow_negative(self):
        with self.assertRaises(Exception) as ctx:
            self.engine.deserialize(self.engine.serialize({"val": -32769}))

        self.assertEqual(self.error, str(ctx.exception))


if __name__ == "__main__":
    unittest.main()
