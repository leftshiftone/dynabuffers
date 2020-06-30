import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema15Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(f"{self.root_dir}/schema15.dbs"))

    def test_resolve_union_type_with_ordinal(self):
        obj = {"name": "test", "content": bytearray(b"text"), ":type": 1}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)

    def test_resolve_union_type_with_qualifier(self):
        obj = {"name": "test", "content": bytearray(b"text"), ":type": "ByteArrayData"}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, {"name": "test", "content": bytearray(b"text"), ":type": 1})


if __name__ == "__main__":
    unittest.main()
