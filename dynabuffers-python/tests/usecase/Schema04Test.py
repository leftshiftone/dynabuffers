import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema04Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def test_parse(self):
        engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema04.dbs"))
        map = engine.deserialize(engine.serialize({"m1": {"s":"test"}, "m2" : {"t":"hello world!"}}))

        self.assertEqual(map, {"m1": {"s":"test", ":type":1}, "m2" : {"t":"hello world!", ":type":0}})


if __name__ == "__main__":
    unittest.main()
