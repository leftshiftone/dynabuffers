package dynabuffers

import dynabuffers.exception.DynabuffersException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

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
        assertMap(engine, mapOf("type" to Optional.of("test")))
    }

    @Test
    fun testEmptyOptional() {
        val engine = Dynabuffers.parse("class Data { type:string? }")
        assertMap(engine, mapOf("type" to Optional.empty<String>()))
    }

}
