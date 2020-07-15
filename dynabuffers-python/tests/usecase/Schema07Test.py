import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema05Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def test_parse(self):
        with open(self.root_dir + "/1.jpg", 'rb') as f:
            data = b"".join(f.readlines())
            engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema07.dbs"))
            map = engine.deserialize(engine.serialize({"image": data}))

            self.assertEqual(map, {"image": data})


if __name__ == "__main__":
    unittest.main()
