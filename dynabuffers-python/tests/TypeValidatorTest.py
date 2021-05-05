import unittest

from dynabuffers.Dynabuffers import Dynabuffers


class TypeValidatorTest(unittest.TestCase):

    def test_invalid_type_name(self):
        with self.assertRaises(ValueError) as ve:
            Dynabuffers.parse(
                """
                   class Test {
                     someVariable: invalidtype
                   }
                """)
        self.assertEqual(
            "The datatype(s) {'invalidtype'} used in the schema are neither built-in datatypes nor class, union or enum references.",
            str(ve.exception))

    def test_invalid_type_names(self):
        with self.assertRaises(ValueError):
            Dynabuffers.parse(
                """
                   class Test {
                     first: FirstInvalidType
                     second: secondInvalidType
                   }
                   class OtherTest {
                     third: thirdInvalidType
                     fourth: FirstInvalidType                    
                   }
                """)

    def test_invalid_class_reference_to_different_namespace(self):
        with self.assertRaises(ValueError):
            Dynabuffers.parse(
                """
                namespace One {
                    class First {
                        someVar: string
                   }
                }
                namespace Two {   
                   class Second {
                     someVar: First
                   }
                }
                """)

    def test_valid_class_reference_to_same_namespace(self):
        Dynabuffers.parse(
            """
            namespace One {
                class First {
                    someVar: string
               }
               class Second {
                 someVar: First
               }
            }
            """)
