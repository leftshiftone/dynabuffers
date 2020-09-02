import unittest

from antlr4 import InputStream

from dynabuffers.Dynabuffers import Dynabuffers
from dynabuffers.api.map.ImplicitDynabuffersMap import ImplicitDynabuffersMap


class DynabuffersTest(unittest.TestCase):

    def test_parse_enum(self):
        engine = Dynabuffers.parse(InputStream("""
enum Color { RED GREEN BLUE }
class Product { name:string color:Color }
        """))

        map = engine.deserialize(engine.serialize({"name": "TV", "color": "RED"}))

        self.assertEqual(map, {"name": "TV", "color": "RED"})

    def test_parse_class(self):
        engine = Dynabuffers.parse(InputStream("class Color { name:string }"))
        map = engine.deserialize(engine.serialize({"name": "red"}))

        self.assertEqual(map, {"name": "red"})

    def test_parse_class_with_multiple_fields(self):
        engine = Dynabuffers.parse(InputStream("class Product { name:string price:float }"))
        map = engine.deserialize(engine.serialize({"name": "TV", "price": 1000}))

        self.assertEqual(map, {"name": "TV", "price": 1000})

    def test_multiple_classes(self):
        engine = Dynabuffers.parse(InputStream("""
class Product { name:string price:float }
class Order(primary) { product:Product amount:int }        
        """))
        map = engine.deserialize(engine.serialize({"product": {"name": "TV", "price": 1000}, "amount": 2}))

        self.assertEqual(map, {"product": {"name": "TV", "price": 1000}, "amount": 2})

    def test_bytearray_field(self):
        engine = Dynabuffers.parse(InputStream("class Image { content:[byte] type:string }"))
        map = engine.deserialize(engine.serialize({"type": "jpg", "content": "abc".encode("utf-8")}))

        self.assertEqual(map, {"type": "jpg", "content": "abc".encode("utf-8")})

    def test_deprecated_class(self):
        engine = Dynabuffers.parse(InputStream("class Color(deprecated) { name:string }"))
        engine.add_listener(print)
        map = engine.deserialize(engine.serialize({"name": "red"}))

        self.assertEqual(map, {"name": "red"})

    def test_deprecated_field(self):
        engine = Dynabuffers.parse(InputStream("class Color { name:string rgb:string(deprecated) }"))
        engine.add_listener(print)
        map = engine.deserialize(engine.serialize({"name": "red", "rgb": "255,0,0"}))

        self.assertEqual(map, {"name": "red", "rgb": "255,0,0"})

    def test_optional_field(self):
        engine = Dynabuffers.parse(InputStream("class Color { name:string = red }"))
        map = engine.deserialize(engine.serialize({}))

        self.assertEqual(map, {"name": "red"})

    def test_annotated_field(self):
        engine = Dynabuffers.parse(InputStream("""
class Product {
   @NotBlank
   @MinLength(3)
   @MaxLength(10)
   name:string
   @GreaterThan(0)
   price:float
}        
        """))
        map = engine.deserialize(engine.serialize({"name": "Fernseher", "price": 1000}))

        self.assertEqual(map, {"name": "Fernseher", "price": 1000})

    def test_parse_map(self):
        engine = Dynabuffers.parse("class Data { type:string data:map }")
        map = {"type": "abc", "data": {"a": "b", "c": 1}}
        result = engine.deserialize(engine.serialize(map))

        self.assertEqual(map, result)

    def test_parse_deep_map(self):
        engine = Dynabuffers.parse("class Data { type:string data:map }")
        map = {"type": "abc", "data": {"a": "b", "c": {"d": {"e": 1}}}}
        result = engine.deserialize(engine.serialize(map))

        self.assertEqual(map, result)

    def test_bytearray(self):
        engine = Dynabuffers.parse("class Data { type:string data:[byte] }")
        map = {"type": "abc", "data": bytearray(b"abcdefghijklmnopqrstuvwxyz")}
        result = engine.deserialize(engine.serialize(map))

        self.assertEqual(map, result)

    def test_null_field(self):
        with self.assertRaises(Exception) as ctx:
            engine = Dynabuffers.parse("class Data { type:string }")
            engine.deserialize(engine.serialize({"type": None}))

        self.assertEqual(str(ctx.exception), "field 'type' is missing")

    def test_missing_field(self):
        with self.assertRaises(Exception) as ctx:
            engine = Dynabuffers.parse("class Data { type:string }")
            engine.deserialize(engine.serialize({}))

        self.assertEqual(str(ctx.exception), "field 'type' is missing")

    def test_optional(self):
        engine = Dynabuffers.parse("class Data { type:string? }")
        map = {"type": "abc"}
        result = engine.deserialize(engine.serialize(map))

        self.assertEqual(map, result)

    def test_missing_optional(self):
        engine = Dynabuffers.parse("class Data { type:string? }")
        result = engine.deserialize(engine.serialize({}))

        self.assertEqual({"type": None}, result)

    def test_missing_list_optional(self):
        engine = Dynabuffers.parse("class Data { list:[string]? }")
        result = engine.deserialize(engine.serialize({}))

        self.assertEqual({"list": None}, result)

    def test_empty_optional(self):
        engine = Dynabuffers.parse("class Data { type:string? }")
        map = {"type": None}
        result = engine.deserialize(engine.serialize(map))

        self.assertEqual(map, result)

    def test_optional_value(self):
        engine = Dynabuffers.parse("""
            class Data {
               type:string = "test"
               list:[string] = []
               attr:map = [:]
            }
        """)
        result = engine.deserialize(engine.serialize({}))
        self.assertTrue("type" in result)
        self.assertTrue("list" in result)
        self.assertTrue("attr" in result)

    def test_implicit_class(self):
        engine = Dynabuffers.parse("""
                class Data(implicit) {
                    value:[byte]
                }
        """)
        result = engine.deserialize(engine.serialize(bytearray(b"test")))
        self.assertTrue("value" in result)
        self.assertTrue(isinstance(result, ImplicitDynabuffersMap))
        self.assertEqual(result.get_value(), bytearray(b"test"))

    def test_schema_with_namespace(self):
        engine = Dynabuffers.parse("""
                namespace abc{
                    class Data {
                        value: string
                    }
                }
            """)
        map = {"value": "hallo"}
        result = engine.deserialize(engine.serialize(map, "abc"), "abc")
        self.assertEqual(map, result)

    def test_schema_with_nested_namespace(self):
        engine = Dynabuffers.parse("""
                namespace abc{
                    namespace def {
                        class Data {
                            value: string
                        }
                    }
                }
            """)
        map = {"value": "hallo"}
        result = engine.deserialize(engine.serialize(map, ["abc", "def"]), ["abc", "def"])
        self.assertEqual(map, result)

    def test_schema_with_nested_namespace_but_specified_namespaces_have_wrong_order_1(self):
        engine = Dynabuffers.parse("""
                namespace abc{
                    namespace def {
                        class Data {
                            value: string
                        }
                    }
                }
            """)
        map = {"value": "hallo"}
        with self.assertRaises(Exception) as ctx:
            engine.serialize(map, ["def", "abc"])
        self.assertTrue(str(ctx.exception) in "no namespace with name def found")

    def test_schema_with_nested_namespace_but_specified_namespaces_with_wrong_order_have_no_effect(self):
        engine = Dynabuffers.parse("""
                namespace abc{
                    namespace def {
                        class Data {
                            value: string
                        }
                    }
                }
            """)
        map = {"value": "hallo"}
        result = engine.deserialize(engine.serialize(map, ["abc", "def"]), ["def", "abc"])
        self.assertEqual({**map, **{':namespace': 'abc.def'}}, result)

    def test_default_namespace_is_used_if_only_one_is_defined(self):
        engine = Dynabuffers.parse("""
                namespace abc{
                    class Data {
                        value: string
                    }
                }
            """)
        map = {"value": "hallo"}
        resultmap = {"value": "hallo", ":namespace": "abc"}
        self.assertEqual(resultmap, engine.deserialize(engine.serialize(map), "abc"))
        self.assertEqual(resultmap, engine.deserialize(engine.serialize(map, "abc")))
        self.assertEqual(resultmap, engine.deserialize(engine.serialize(map)))

    def test_default_namespace_resolution_works_with_nested_namespaces(self):
        engine = Dynabuffers.parse("""
                namespace abc{
                    namespace xyz {
                        class Data {
                            value: string
                        }
                    }
                }                
            """)
        map = {"value": "hallo"}

        self.assertEqual(map, engine.deserialize(engine.serialize(map, ["abc", "xyz"])))
        self.assertEqual(map, engine.deserialize(engine.serialize(map), ["abc", "xyz"]))
        self.assertEqual(map, engine.deserialize(engine.serialize(map)))

    def test_default_namespace_resolution_fails_if_multiple_namespaces_are_defined(self):
        engine = Dynabuffers.parse("""
                namespace abc{
                    class Data {
                        value: string
                    }
                }
                namespace xyz {
                }
            """)
        map = {"value": "hallo"}

        with self.assertRaises(Exception) as ctx:
            engine.deserialize(engine.serialize(map))
        self.assertEqual(str(ctx.exception), "Could not infer default namespace")

    # TODO: should the default namespace really be resolved here?
    # def test_default_namespace_resolution_works_until_multiple_namespaces_are_available(self):
    #     engine = Dynabuffers.parse("""
    #         namespace abc{
    #             namespace xyz {}
    #             namespace bla {}
    #             class Data {
    #                 value: string
    #             }
    #         }
    #     """)
    #     map = {"value": "hallo"}
    #     resultmap = {**map, **{":namespace" : "abc"}}
    #     self.assertEqual(resultmap, engine.deserialize(engine.serialize(map)))

    #TODO: should this be allowed and not raise an exception?
    def test_default_empty_namespaces_will_raise_an_exception(self):
        engine = Dynabuffers.parse("""
            namespace abc{
                namespace xyz {}
                class Data {
                    value: string
                }
            }
        """)
        self.assertRaises(ValueError, lambda: engine.serialize({'value': 'sadsahjd'}))

    def test_schema_with_namespace_containing_slash_in_name(self):
        engine = Dynabuffers.parse("""
                namespace `leftshiftone/echo`{
                    namespace abc {
                        namespace def {
                            class Data {
                                value: string
                            }
                        }
                    }
                }
            """)
        map = {"value": "hallo"}
        result = engine.deserialize(engine.serialize(map, ["`leftshiftone/echo`", "abc", "def"]),
                                    ["`leftshiftone/echo`", "abc", "def"])
        self.assertEqual(map, result)

    def test_schema_with_nested_namespaces_containing_clases_in_all_levels(self):
        engine = Dynabuffers.parse("""
                namespace `leftshiftone/echo`{
                    class DataLevel0 {
                                value0: string
                            }
                    namespace abc {
                        class DataLevel1 {
                                value1: int
                            }
                        namespace def {
                            class DataLevel2 {
                                value2: [string]
                            }
                        }
                    }
                }
            """)
        map0 = {"value0": "someString"}
        map1 = {"value1": 3}
        map2 = {"value2": ["a", "b"]}
        result0 = engine.deserialize(engine.serialize(map0, ["`leftshiftone/echo`"]), ["`leftshiftone/echo`"])
        self.assertEqual(map0, result0)
        result1 = engine.deserialize(engine.serialize(map1, ["`leftshiftone/echo`", "abc"]),
                                     ["`leftshiftone/echo`", "abc"])
        self.assertEqual(map1, result1)
        result2 = engine.deserialize(engine.serialize(map2, ["`leftshiftone/echo`", "abc", "def"]),
                                     ["`leftshiftone/echo`", "abc", "def"])
        self.assertEqual(map2, result2)


if __name__ == "__main__":
    unittest.main()
