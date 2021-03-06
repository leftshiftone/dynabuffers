package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Schema11Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema08Test::class.java.getResourceAsStream("/schema11.dbs"))
    }

    @Test
    fun testDeepMap() {
        val envelope = mapOf("type" to "query", "data" to mapOf("a" to "b", "c" to mapOf("d" to mapOf("e" to 1))))
        assertMap(engine, envelope, envelope)
    }

    @Test
    fun testMapWithArray() {
        val envelope = mapOf("type" to "query", "data" to mapOf("a" to arrayOf("a", "b", "c", "d")))
        assertMap(engine, envelope, envelope)
    }

    @Test
    fun testMapWithNotNull() {
        val envelope = mapOf("type" to "query", "data" to mapOf("a" to "b"))
        assertMap(engine, envelope, envelope)
    }

    @Test
    fun testMapWithNull() {
        val envelope = mapOf("type" to "query", "data" to mapOf("a" to null))
        assertMap(engine, envelope, envelope)
    }

    @Test
    fun testArrayOfMaps() {
        val envelope = mapOf("type" to "query", "data" to mapOf("mapKey1" to arrayOf(mapOf("innerMapKey1" to "innerVal1"))))
        assertMap(engine, envelope, envelope)
    }

    @Test
    fun testListsOfMaps() {
        val input = mapOf("type" to "query", "data" to mapOf("mapKey1" to listOf(
                mapOf("innerMapKey1" to "innerVal1"),
                mapOf("innerMapKey2" to "innerVal2")
        )))
        val expected = mapOf("type" to "query", "data" to mapOf("mapKey1" to arrayOf(
                mapOf("innerMapKey1" to "innerVal1"),
                mapOf("innerMapKey2" to "innerVal2")
        )))
        assertMap(engine, input, expected)
    }

    @Test
    fun testCollectionOfMaps() {
        val input = mapOf("type" to "query", "data" to mapOf("mapKey1" to mutableListOf(
                mapOf("innerMapKey1" to "innerVal1"),
                mapOf("innerMapKey2" to "innerVal2")
        )))
        val expected = mapOf("type" to "query", "data" to mapOf("mapKey1" to arrayOf(
                mapOf("innerMapKey1" to "innerVal1"),
                mapOf("innerMapKey2" to "innerVal2")
        )))
        assertMap(engine, input, expected)
    }

}
