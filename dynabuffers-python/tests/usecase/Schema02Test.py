import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema02Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def test_parse(self):
        engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema02.dbs"))
        map = engine.deserialize(
            engine.serialize({"message": {"wasteType": "abcd", "point": {"lat": 47.0, "lng": 15.0}}}))

        self.assertEqual(map, {"message": {"wasteType": "abcd", "point": {"lat": 47.0, "lng": 15.0}, ":type": 0}})


if __name__ == "__main__":
    unittest.main()
