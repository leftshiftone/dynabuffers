import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema01Test(unittest.TestCase):

    def test_parse(self):
        engine = Dynabuffers.parse(FileStream(__file__ + "/../schema01.dbs"))
        map = engine.deserialize(engine.serialize({"message": {"text": "abcd", "type": ["DEP", "NER"]}}))

        self.assertEqual(map, {"message": {"text": "abcd", "type": ["DEP", "NER"]}})


if __name__ == "__main__":
    unittest.main()
