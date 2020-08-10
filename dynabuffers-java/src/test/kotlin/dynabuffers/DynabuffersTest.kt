package dynabuffers

import dynabuffers.api.map.ImplicitDynabuffersMap
import dynabuffers.exception.DynabuffersException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DynabuffersTest : AbstractDynabuffersTest() {

    @Test
    fun parseEnum() {
        val engine = Dynabuffers.parse("""
enum Color { RED GREEN BLUE }
class Product { name:string color:Color }
        """)
        assertMap(engine, mapOf("name" to "TV", "color" to "RED"))
    }

    @Test
    fun parseClass() {
        val engine = Dynabuffers.parse("class Color { name:string }")
        assertMap(engine, mapOf("name" to "red"))
    }

    @Test
    fun parseClassWithMultipleFields() {
        val engine = Dynabuffers.parse("class Product { name:string price:float }")
        assertMap(engine, mapOf("name" to "TV", "price" to 1000f))
    }

    @Test
    fun parseMultipleClasses() {
        val engine = Dynabuffers.parse("""
class Order(primary) { product:Product amount:int }
class Product { name:string price:float }
        """)
        assertMap(engine, mapOf("product" to mapOf("name" to "TV", "price" to 1000f), "amount" to 2))
    }

    @Test
    fun parseByteArrayField() {
        val engine = Dynabuffers.parse("class Image { content:[byte] type:string }")
        assertMap(engine, mapOf("type" to "jpg", "content" to "abc".toByteArray()))
    }

    @Test
    fun parseDeprecatedClass() {
        val engine = Dynabuffers.parse("class Color(deprecated) { name:string }")
        engine.addListener(System.out::println)
        assertMap(engine, mapOf("name" to "red"))
    }

    @Test
    fun parseDeprecatedField() {
        val engine = Dynabuffers.parse("class Color { name:string rgb:string(deprecated) }")
        engine.addListener(System.out::println)
        assertMap(engine, mapOf("name" to "red", "rgb" to "255,0,0"))
    }

    @Test
    fun parseOptionalField() {
        val engine = Dynabuffers.parse("class Color { name:string=red }")
        engine.addListener(System.out::println)

        val result = engine.deserialize(engine.serialize(mapOf()))
        assertMap(mapOf("name" to "red"), result)
    }

    @Test
    fun parseAnnotatedField() {
        val engine = Dynabuffers.parse("""
class Product {
   @NotBlank
   @MinLength(3)
   @MaxLength(10)
   name:string
   @GreaterThan(0)
   price:float
}
            """.trimIndent())
        engine.addListener(System.out::println)

        val result = engine.deserialize(engine.serialize(mapOf("name" to "Fernseher", "price" to 1000f)))
        assertMap(mapOf("name" to "Fernseher", "price" to 1000f), result)
    }

    @Test
    fun parseMap() {
        val engine = Dynabuffers.parse("class Data { type:string data:map }")
        engine.addListener(System.out::println)
        assertMap(engine, mapOf("type" to "abc", "data" to mapOf("a" to "b", "c" to 1)))
    }

    @Test
    fun parseDeepMap() {
        val engine = Dynabuffers.parse("class Data { type:string data:map }")
        engine.addListener(System.out::println)
        assertMap(engine, mapOf("type" to "abc", "data" to mapOf("a" to "b", "c" to mapOf("d" to mapOf("e" to 1)))))
    }

    @Test
    fun parseMapOfStringToList() {
        val engine = Dynabuffers.parse("class Languages { word:map }")
        engine.addListener(System.out::println)

        val result = engine.deserialize(engine.serialize(mapOf("word" to mapOf("de" to listOf("world", "hello")))))
        assertMap(mapOf("word" to mapOf("de" to arrayOf("world", "hello"))), result)
    }

    @Test
    fun parseBytearray() {
        val engine = Dynabuffers.parse("class Data { type:string data:[byte] }")
        assertMap(engine, mapOf("type" to "abc", "data" to "abcdefghijklmnopqrstuvwxyz".toByteArray()))
    }

    @Test
    @Suppress("UNCHECKED_CAST")
    fun handleNullField() {
        try {
            val engine = Dynabuffers.parse("class Data { type:string }")
            engine.serialize(mapOf("type" to null) as Map<String, Any>)

            Assertions.fail<String>("exception expected")
        } catch (e: DynabuffersException) {
            // do nothing
        }
    }

    @Test
    fun handleMissingField() {
        try {
            val engine = Dynabuffers.parse("class Data { type:string }")
            engine.serialize(emptyMap())

            Assertions.fail<String>("exception expected")
        } catch (e: DynabuffersException) {
            // do nothing
        }
    }

    @Test
    fun testOptional() {
        val engine = Dynabuffers.parse("class Data { type:string? }")
        assertMap(engine, mapOf("type" to "test"))
    }

    @Test
    fun testMissingOptional() {
        val engine = Dynabuffers.parse("class Data { type:string? }")
        val result = engine.deserialize(engine.serialize(emptyMap()))

        Assertions.assertEquals(result["type"], null)
    }

    @Test
    fun testMissingOptionalList() {
        val engine = Dynabuffers.parse("class Data { list:[string]? }")
        val result = engine.deserialize(engine.serialize(emptyMap()))

        Assertions.assertEquals(result["list"], null)
    }

    @Test
    fun testOptionalValue() {
        val engine = Dynabuffers.parse("""
            class Data {
               type:string = "test"
               list:[string] = []
               attr:map = [:]
            }
        """.trimIndent())
        val result = engine.deserialize(engine.serialize(emptyMap()))
        Assertions.assertTrue(result.containsKey("type"))
        Assertions.assertTrue(result.containsKey("list"))
        Assertions.assertTrue(result.containsKey("attr"))
    }

    @Test
    fun testMissingField() {
        Assertions.assertThrows(DynabuffersException::class.java) {
            val engine = Dynabuffers.parse("class Data { type:string }")
            engine.deserialize(engine.serialize(mapOf("type" to null)))
        }
    }

    @Test
    fun testImplicitClass() {
        val engine = Dynabuffers.parse("""
            class Data(implicit) {
               value:[byte]
            }
        """.trimIndent())
        val result = engine.deserialize(engine.serialize("test".toByteArray()))
        Assertions.assertTrue(result.containsKey("value"))
        Assertions.assertTrue(result is ImplicitDynabuffersMap)
        Assertions.assertArrayEquals((result as ImplicitDynabuffersMap).getValue() as ByteArray, "test".toByteArray())
    }

}
