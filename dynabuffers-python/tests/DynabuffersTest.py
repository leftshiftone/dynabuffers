import unittest

from antlr4 import InputStream

from dynabuffers.Dynabuffers import Dynabuffers


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
        engine.addListener(print)
        map = engine.deserialize(engine.serialize({"name": "red"}))

        self.assertEqual(map, {"name": "red"})

    def test_deprecated_field(self):
        engine = Dynabuffers.parse(InputStream("class Color { name:string rgb:string(deprecated) }"))
        engine.addListener(print)
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
        engine = Dynabuffers.parse("class Data { type:string data:bytearray }")
        map = {"type": "abc", "data": bytearray(b"abcdefghijklmnopqrstuvwxyz")}
        result = engine.deserialize(engine.serialize(map))

        self.assertEqual(map, result)

    if __name__ == "__main__":
        unittest.main()
