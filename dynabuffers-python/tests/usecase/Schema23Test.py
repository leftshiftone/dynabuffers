import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema23(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema23.dbs"))

    def test(self):
        serialized = self.engine.serialize({'amicool': True})
        deserialized = self.engine.deserialize(serialized)
        assert deserialized["amicool"]


if __name__ == "__main__":
    unittest.main()
