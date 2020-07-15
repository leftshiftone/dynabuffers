import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema05Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def test_parse(self):
        engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema05.dbs"))
        map = engine.deserialize(engine.serialize({"strings" : ["a", "b", "c"]}))

        self.assertEqual(map, {"strings" : ["a", "b", "c"]})


if __name__ == "__main__":
    unittest.main()
