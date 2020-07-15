import os
import unittest

from antlr4 import FileStream

from dynabuffers.Dynabuffers import Dynabuffers


class Schema16Test(unittest.TestCase):
    root_dir = os.path.dirname(os.path.realpath(__file__))

    def setUp(self):
        self.engine = Dynabuffers.parse(FileStream(self.root_dir + "/schema16.dbs"))

    def test_resolve_union_type_with_ordinal(self):
        message = {
            "stringVal": "abc",
            "intVal": 0,
            "byteVal": 1,
            "shortVal": 2,
            "longVal": 3,
            "floatVal": 4,
            "mapVal": {"name": "text"},
            "byteArrayVal": bytearray(b"text"),
            "refVal": {"name": "text"},
            ":type": 0
        }
        result = self.engine.deserialize(self.engine.serialize(message))
        self.assertEqual(result, message)

        self.assertEqual(result.get_string("stringVal"), "abc")
        self.assertEqual(result.get_int("intVal"), 0)
        self.assertEqual(result.get_byte("byteVal"), 1)
        self.assertEqual(result.get_short("shortVal"), 2)
        self.assertEqual(result.get_long("longVal"), 3)
        self.assertEqual(result.get_float("floatVal"), 4)
        self.assertEqual(result.get_map("mapVal"), {"name":"text"})
        self.assertEqual(result.get_bytearray("byteArrayVal"), bytearray(b"text"))
        self.assertEqual(result.get_ref("refVal"), {"name":"text"})

        with self.assertRaises(Exception) as ctx:
            result["unknown"]

        self.assertIsNotNone(ctx.exception)



if __name__ == "__main__":
    unittest.main()
