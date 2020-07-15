import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema17Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema17.dbs"))

    def test_namespace(self):
        message = {"contentA":"abc"}
        result = self.engine.deserialize(self.engine.serialize(message, "request"), "request")

        self.assertEqual(message, result)


if __name__ == "__main__":
    unittest.main()
