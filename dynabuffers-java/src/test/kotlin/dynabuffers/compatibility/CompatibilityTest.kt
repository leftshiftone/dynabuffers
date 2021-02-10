package dynabuffers.compatibility

import dynabuffers.Dynabuffers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class CompatibilityTest {

    @Test
    fun simpleClass() {
        val engine = Dynabuffers.parse("class Color { name:string }")
        Assertions.assertEquals("AQAAAAADcmVk", engine.serialize(mapOf("name" to "red")).base64())
    }

    @Test
    fun classWithEnum() {
        val engine = Dynabuffers.parse("""
enum Color { RED GREEN BLUE }
class Product { name:string color:Color }
        """.trimIndent())
        Assertions.assertEquals("AQAAAAACVFYDUkVE", engine.serialize(mapOf("name" to "TV", "color" to "RED")).base64())
    }

    @Test
    fun classWithMultipleFields() {
        val engine = Dynabuffers.parse("class Product { name:string price:float }".trimIndent())
        Assertions.assertEquals("AQAAAAACVFZEegAA", engine.serialize(mapOf("name" to "TV", "price" to 1000)).base64())
    }

    @Test
    fun multipleClasses() {
        val engine = Dynabuffers.parse("""
class Product { name:string price:float }
class Order(primary) { product:Product amount:int }
        """.trimIndent())
        Assertions.assertEquals(
            "AQAAAAACVFZEegAAAAAAAg==",
            engine.serialize(mapOf("product" to mapOf("name" to "TV", "price" to 1000), "amount" to 2)).base64()
        )
    }

    @Test
    fun optionalField() {
        val engine = Dynabuffers.parse("class Color { name:string=red }".trimIndent())
        Assertions.assertEquals("AQAAAAADcmVk", engine.serialize(mapOf()).base64())
    }

    private fun ByteArray.base64(): String {
        return Base64.getEncoder().encodeToString(this)
    }
}
