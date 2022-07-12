import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema26(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema26.dbs"))

    def test_defaults(self):
        obj = {
            "text": "123",
            "oneInt": 1,
            "twoFloat": 2.0,
            "threeShort": 3,
            "fourLong": 4,
            "trueBoolean": True,
            "falseBoolean": False
        }
        result = self.engine.deserialize(self.engine.serialize({}))
        self.assertEqual(result, obj)


if __name__ == "__main__":
    unittest.main()
