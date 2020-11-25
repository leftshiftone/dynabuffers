package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Schema16Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema16Test::class.java.getResourceAsStream("/schema16.dbs"))
    }

    @Test
    fun dynabuffersMap() {
        val message = mapOf(
                "stringVal" to "abc",
                "intVal" to 0,
                "byteVal" to 1.toByte(),
                "shortVal" to 2.toShort(),
                "longVal" to 3.toLong(),
                "floatVal" to 4.toFloat(),
                "mapVal" to mapOf("a" to "b"),
                "byteArrayVal" to "text".toByteArray(),
                "refVal" to mapOf("name" to "text"),
                ":type" to 0
                )
        val result = engine.deserialize(engine.serialize(message))

        assertMap(message, result)
        result.getString("stringVal")
        result.getInt("intVal")
        result.getByte("byteVal")
        result.getShort("shortVal")
        result.getLong("longVal")
        result.getFloat("floatVal")
        result.getMap("mapVal")
        result.getByteArray("byteArrayVal")
        val ref = result.getRef("refVal")
        ref.getString("name")

        try {
            result["unknown"]
            Assertions.fail<String>("an exception is expected")
        } catch (e: Exception) {
        }
    }

    @Test
    fun dynabuffersMapWithEmptyList() {
        val message = mapOf(
                "stringVal" to "abc",
                "intVal" to 0,
                "byteVal" to 1.toByte(),
                "shortVal" to 2.toShort(),
                "longVal" to 3.toLong(),
                "floatVal" to 4.toFloat(),
                "mapVal" to mapOf("a" to emptyList<String>()),
                "byteArrayVal" to "text".toByteArray(),
                "refVal" to mapOf("name" to "text"),
                ":type" to 0
        )
        val result = engine.deserialize(engine.serialize(message))

        assertThat((result["mapVal"] as Map<String, Array<String>>)?.get("a")).isEmpty()
    }

}
