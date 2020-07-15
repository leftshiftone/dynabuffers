import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema13Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))
    error = "int too large to convert"

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema13.dbs"))

    def test_type_hint(self):
        obj = {"message": {"type": 'query', "data": bytearray(b"abc"), ":type":1}}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)


if __name__ == "__main__":
    unittest.main()
