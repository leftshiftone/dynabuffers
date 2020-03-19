import base64
import unittest

from antlr4 import InputStream

from dynabuffers.Dynabuffers import Dynabuffers


class CompatibilityTest(unittest.TestCase):

    def test_simple_class(self):
        engine = Dynabuffers.parse(InputStream("class Color { name:string }"))
        encoded = base64.b64encode(engine.serialize({"name": "red"})).decode("utf-8")

        self.assertEqual(encoded, "AAAAA3JlZA==")

    def test_class_with_enum(self):
        engine = Dynabuffers.parse(InputStream("""
enum Color { RED GREEN BLUE }
class Product { name:string color:Color }
        """))

        encoded = base64.b64encode(engine.serialize({"name": "TV", "color": "RED"})).decode("utf-8")
        self.assertEqual(encoded, "AAAAAlRWA1JFRA==")

    def test_class_with_multiple_fields(self):
        engine = Dynabuffers.parse(InputStream("class Product { name:string price:float }"))
        encoded = base64.b64encode(engine.serialize({"name": "TV", "price": 1000})).decode("utf-8")

        self.assertEqual(encoded, "AAAAAlRWRHoAAA==")

    def test_multiple_classes(self):
        engine = Dynabuffers.parse(InputStream("""
class Product { name:string price:float }
class Order(primary) { product:Product amount:int }        
        """))

        encoded = base64.b64encode(engine.serialize({"product": {"name": "TV", "price": 1000}, "amount": 2})).decode(
            "utf-8")

        self.assertEqual(encoded, "AAAAAlRWRHoAAAAAAAI=")

    def test_optional_field(self):
        engine = Dynabuffers.parse(InputStream("class Color { name:string=red }"))
        encoded = base64.b64encode(engine.serialize({})).decode("utf-8")

        self.assertEqual(encoded, "AAAAA3JlZA==")


if __name__ == "__main__":
    unittest.main()
