import base64
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
        
    def test_from_base64(self):
        serialized = 'AAE='
        decoded = base64.b64decode(serialized.encode('ascii'))
        result = self.engine.deserialize(bytearray(decoded))
        assert result["amicool"]

if __name__ == "__main__":
    unittest.main()
