import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema24(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema24.dbs"))

    def test(self):
        obj = {"ner": [{"text": "asdf1234"}]}
        result = self.engine.deserialize(self.engine.serialize(obj, ["outgoing"]))
        self.assertEqual(result, obj)


if __name__ == "__main__":
    unittest.main()
