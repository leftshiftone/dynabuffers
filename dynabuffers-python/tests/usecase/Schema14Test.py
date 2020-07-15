import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema13Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema14.dbs"))

    def test(self):
        obj = {"uuid": "1234",
               "payload": {":type": 0, "identityId": "1234", "eventName": "test", "eventData": {"a": "b"}},
               "replyTo": "test",
               "timeout": 1000}
        result = self.engine.deserialize(self.engine.serialize(obj))
        self.assertEqual(result, obj)


if __name__ == "__main__":
    unittest.main()
