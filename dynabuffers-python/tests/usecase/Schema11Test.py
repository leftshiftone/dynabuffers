import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema11Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))
    error = "int too large to convert"

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema11.dbs"))

    def test_deep_map(self):
        obj = {"type": 'query', "data":{"a":"b", "c":{"d":{"e":1}}}}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)

    def test_map_with_array(self):
        obj = {"type": 'query', "data":{"a":["a", "b", "c", "d"]}}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)

    def test_map_not_null(self):
        obj = {"type": 'query', "data": {"a": "b"}}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)

    def test_map_with_null(self):
        obj = {"type": 'query', "data": {"a": None}}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)

    def test_map_with_empty_array(self):
        obj = {"type": 'query', "data": {"e": []}}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)

    def test_map_with_array_of_maps(self):
        obj = {"type": 'query', "data": {"e": [{"key1": "val1"}, {"key2": "val2"}]}}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)


if __name__ == "__main__":
    unittest.main()
