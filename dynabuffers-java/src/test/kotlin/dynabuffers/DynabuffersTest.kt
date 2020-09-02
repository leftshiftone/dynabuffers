package dynabuffers

import dynabuffers.api.map.ImplicitDynabuffersMap
import dynabuffers.exception.DynabuffersException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

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
        val engine = Dynabuffers.parse("class Data { type:string }")
        assertThatThrownBy { engine.serialize(mapOf("type" to null) as Map<String, Any>) }.isInstanceOf(DynabuffersException::class.java)
    }

    @Test
    fun handleMissingField() {
        val engine = Dynabuffers.parse("class Data { type:string }")
        assertThatThrownBy { engine.serialize(emptyMap()) }.isInstanceOf(DynabuffersException::class.java)
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

        assertThat(result["type"]).isNull()
    }

    @Test
    fun testMissingOptionalList() {
        val engine = Dynabuffers.parse("class Data { list:[string]? }")
        val result = engine.deserialize(engine.serialize(emptyMap()))

        assertThat(result["list"]).isNull()
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
        assertThat(result).containsKey("type")
        assertThat(result).containsKey("list")
        assertThat(result).containsKey("attr")
    }

    @Test
    fun testMissingField() {
        assertThatThrownBy {
            val engine = Dynabuffers.parse("class Data { type:string }")
            engine.deserialize(engine.serialize(mapOf("type" to null)))
        }.isInstanceOf(DynabuffersException::class.java)
    }

    @Test
    fun testImplicitClass() {
        val engine = Dynabuffers.parse("""
            class Data(implicit) {
               value:[byte]
            }
        """.trimIndent())
        val result = engine.deserialize(engine.serialize("test".toByteArray()))
        assertThat(result).containsKey("value")
        assertThat(result).isInstanceOf(ImplicitDynabuffersMap::class.java)
        assertThat((result as ImplicitDynabuffersMap).getValue() as ByteArray).isEqualTo("test".toByteArray())
    }

    @Test
    fun `Schema with namespace`() {
        val engine = Dynabuffers.parse("""
            namespace abc{
                class Data {
                    value: string
                }
            }
        """.trimIndent())
        val result = engine.deserialize(engine.serialize(mapOf("value" to "someString"), "abc"), "abc")
        assertThat(result).containsKey("value")
        assertThat(result).containsValue("someString")
    }

    @Test
    fun `Schema with single namespace, therefore default namespace is chosen if no namespace is supplied`() {
        val engine = Dynabuffers.parse("""
            namespace abc{
                class Data {
                    value: string
                }
            }
        """.trimIndent())
        val map = mapOf("value" to "someString")
        assertThat(map.plus(SpecialKey.NAMESPACE.key to "abc")).isEqualTo(engine.deserialize(engine.serialize(map)))
        assertThat(map.plus(SpecialKey.NAMESPACE.key to "abc")).isEqualTo(engine.deserialize(engine.serialize(map, "abc")))
        assertThat(map.plus(SpecialKey.NAMESPACE.key to "abc")).isEqualTo(engine.deserialize(engine.serialize(map), "abc"))
    }

    @Test
    fun `Schema with nested namespaces, therefore default namespace is chosen if no namespace is supplied`() {
        val engine = Dynabuffers.parse("""
            namespace abc{
                namespace xyz {
                    class Data {
                        value: string
                    }
                }
            }
        """.trimIndent())
        val map = mapOf("value" to "someString")
        assertThat(map.plus(SpecialKey.NAMESPACE.key to "abc.xyz")).isEqualTo(engine.deserialize(engine.serialize(map)))
        assertThat(map.plus(SpecialKey.NAMESPACE.key to "abc.xyz")).isEqualTo(engine.deserialize(engine.serialize(map, listOf("abc", "xyz"))))
        assertThat(map.plus(SpecialKey.NAMESPACE.key to "abc.xyz")).isEqualTo(engine.deserialize(engine.serialize(map), listOf("abc", "xyz")))
    }

    @Test
    @Disabled("Should we really choose a default namespace here?")
    fun `Schema with multiple nested namespaces, therefore outer namespace is chosen as default`() {
        val engine = Dynabuffers.parse("""
            namespace abc{
                namespace uvw {}
                namespace xyz {}                
                class Data {
                    value: string
                }
            }
        """.trimIndent())
        val map = mapOf("value" to "someString")
        assertThat(map).isEqualTo(engine.deserialize(engine.serialize(map)))
        assertThat(map).isEqualTo(engine.deserialize(engine.serialize(map, listOf("abc"))))
        assertThat(map).isEqualTo(engine.deserialize(engine.serialize(map), listOf("abc")))
    }

    @Test
    fun `Schema with multiple namespaces, therefore default namespace is not chosen`() {
        val engine = Dynabuffers.parse("""
            namespace abc{
                class Data {
                    value: string
                }
            }
            namespace xyz {
            }
        """.trimIndent())
        val msg = engine.serialize(mapOf("value" to "someString"), "abc")

        assertThatThrownBy { engine.serialize(mapOf("value" to "someString")) }
                .isInstanceOf(DynabuffersException::class.java)
                .hasMessage("Could not infer default namespace")
    }

    @Test
    fun `Nested namespaces are processed if given in proper order`() {
        val engine = Dynabuffers.parse("""
            namespace abc{
                namespace def {
                    class Data {
                        value: string
                    }
                }
            }
        """.trimIndent())
        val result = engine.deserialize(engine.serialize(mapOf("value" to "someString"), listOf("abc", "def")), listOf("abc", "def"))
        assertThat(result).containsKey("value")
        assertThat(result).containsValue("someString")
    }

    @Test
    fun `Data can be serialized-deserialized when schema has nested namespaces and their names are not given in the proper order - since `() {
        val engine = Dynabuffers.parse("""
            namespace abc{
                namespace def {
                    class Data {
                        value: string
                    }
                }
            }
        """.trimIndent())
        val msg = engine.serialize(mapOf("value" to "someString"), listOf("abc", "def"))

        assertThatThrownBy { engine.serialize(mapOf("value" to "someString"), listOf("def", "abc")) }
                .isInstanceOf(DynabuffersException::class.java)
                .hasMessage("no namespace with name def found")

        val result = engine.deserialize(msg, listOf("def", "abc"))
        assertThat(result).isEqualTo(mapOf("value" to "someString", ":namespace" to "abc.def"))
    }

    @Test
    fun `Namespaces names may contain slashes if surrounded by literal symbol`() {
        val engine = Dynabuffers.parse("""
            namespace `leftshiftone/echo`{
                namespace abc {
                    namespace def {
                        class Data {
                            value: string
                        }
                    }
                }
            }
        """.trimIndent())
        val result = engine.deserialize(engine.serialize(mapOf("value" to "someString"), listOf("`leftshiftone/echo`", "abc", "def")), listOf("`leftshiftone/echo`", "abc", "def"))
        assertThat(result).containsKey("value")
        assertThat(result).containsValue("someString")
    }

    @Test
    fun `Use of classes from namespaces is allowed in any level of the nested structure`() {
        val engine = Dynabuffers.parse("""
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
                            value2: float
                        }
                    }
                }
            }
        """.trimIndent())
        val resultLevel0 = engine.deserialize(engine.serialize(mapOf("value0" to "someString"), listOf("`leftshiftone/echo`")), listOf("`leftshiftone/echo`"))
        assertThat(resultLevel0).containsKey("value0")
        assertThat(resultLevel0).containsValue("someString")

        val resultLevel1 = engine.deserialize(engine.serialize(mapOf("value1" to 3), listOf("`leftshiftone/echo`", "abc")), listOf("`leftshiftone/echo`", "abc"))
        assertThat(resultLevel1).containsKey("value1")
        assertThat(resultLevel1).containsValue(3)
        val resultLevel2 = engine.deserialize(engine.serialize(mapOf("value2" to 0.2f), listOf("`leftshiftone/echo`", "abc", "def")), listOf("`leftshiftone/echo`", "abc", "def"))
        assertThat(resultLevel2).containsKey("value2")
        assertThat(resultLevel2).containsValue(0.2f)
    }

}
