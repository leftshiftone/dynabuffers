import unittest

from dynabuffers.Dynabuffers import Dynabuffers


class DynabuffersTest(unittest.TestCase):

    def test_schema_with_too_many_nested_namespaces(self):
        engine = Dynabuffers.parse(
            """
               namespace abc{
                   namespace abc{
                       namespace abc{
                           namespace abc{
                               namespace abc{
                                   namespace abc{
                                       namespace abc{
                                           namespace abc{
                                               class Data {
                                                   value: string
                                               }
                                           }
                                       }
                                   }
                               }
                           }
                       }
                   }  
               }
            """)
        map = {"value": "hallo"}
        with self.assertRaises(ValueError) as ve:
            engine.serialize(map, ["abc", "abc", "abc", "abc", "abc", "abc", "abc", "abc"])
        self.assertEqual("Value 8 of Namespace Depth is too large for field size of 3 bits.", str(ve.exception))

    def test_schema_with_too_many_namespaces_per_layer(self):
        engine = Dynabuffers.parse(
            """
                namespace n0 {} namespace n1 {}
                namespace n2 {} namespace n3 {}
                namespace n4 {} namespace n5 {}
                namespace n6 {} namespace n7 {}
                namespace n8 {} namespace n9 {}
                namespace n10 {} namespace n11 {}
                namespace n12 {} namespace n13 {}
                namespace n14 {} namespace n15 {}
                namespace n16 { 
                    class Data {
                        value: string
                    } 
                }
            """
        )
        map = {"value": "hallo"}
        with self.assertRaises(ValueError) as ve:
            engine.serialize(map, "n16")
        self.assertEqual("Value 16 of Namespace Path Indicator is too large for field size of 4 bits.",
                         str(ve.exception))
