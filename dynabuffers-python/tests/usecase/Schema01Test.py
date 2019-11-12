import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema01Test(unittest.TestCase):

    root_dir = os.path.dirname(os.path.realpath(__file__))

    def test_parse(self):
        engine = Dynabuffers.parse(FileStream(f"{self.root_dir}/schema01.dbs"))
        map = engine.deserialize(engine.serialize({"message": {"text": "abcd", "type": ["DEP", "NER"]}}))

        self.assertEqual(map, {"message": {"text": "abcd", "type": ["DEP", "NER"], ":type":0}})


if __name__ == "__main__":
    unittest.main()
