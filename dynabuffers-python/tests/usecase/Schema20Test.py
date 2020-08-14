import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema20Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema20.dbs"))

    def test_namespace(self):
        message = {"contentA":"abc"}
        result = self.engine.deserialize(self.engine.serialize(message, ["`leftshiftone/echo`","incoming"]), ["`leftshiftone/echo`","incoming"])

        self.assertEqual(message, result)


if __name__ == "__main__":
    unittest.main()
