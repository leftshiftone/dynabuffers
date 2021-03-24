import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema25(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema25.dbs"))

    def test_first_schema(self):
        obj = {"text": "someText",
               "identityIds": ["1", "2", "3"],
               "languages": ["de", "en"],
               "minScore": 1.0,
               "noOfMatches": 1}
        result = self.engine.deserialize(self.engine.serialize(obj, "incoming"))
        self.assertEqual(result, obj)

    def test_second_schema(self):
        obj = {"matches": [{
            "identityId": "string",
            "intentId": "string",
            "score": 1.0,
            "language": "string",
            "utterance": "string"
        }]}
        result = self.engine.deserialize(self.engine.serialize(obj, "outgoing"))
        self.assertEqual(result, obj)


if __name__ == "__main__":
    unittest.main()
