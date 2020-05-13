import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema11Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))
    error = "int too large to convert"

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(f"{self.root_dir}/schema11.dbs"))

    def test_deep_map(self):
        obj = {"type": 'query', "data":{"a":"b", "c":{"d":{"e":1}}}}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)


if __name__ == "__main__":
    unittest.main()
