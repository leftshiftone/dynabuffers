import base64
import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema21Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema21.dbs"))

    def test_first_namespace_incoming_can_be_serialized(self):
        message = {"request": "hello world", ":namespace": "first.incoming"}
        result = self.engine.deserialize(self.engine.serialize(message))

        self.assertEqual(message, result)

    def test_first_namespace_outgoing_can_be_serialized(self):
        message = {"response": "hello back", ":namespace": "first.outgoing"}
        result = self.engine.deserialize(self.engine.serialize(message))

        self.assertEqual(message, result)


if __name__ == "__main__":
    unittest.main()
