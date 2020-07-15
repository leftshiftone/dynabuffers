import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema03Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def test_parse(self):
        engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema03.dbs"))
        map = engine.deserialize(engine.serialize({"results": [{"text":"hello world"}]}))

        self.assertEqual(map, {"results": [{"text":"hello world"}]})


if __name__ == "__main__":
    unittest.main()
