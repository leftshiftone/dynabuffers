package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger

class Schema18Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema15Test::class.java.getResourceAsStream("/schema18.dbs"))
    }

    @Test
    fun test_int_type() {
        val message = mapOf(
                "message" to mapOf("hello" to "world", ":type" to 0, "some" to mapOf(":type" to 1, "i" to "1"))
        )
        engine.deserialize(engine.serialize(message))
    }

    @Test
    fun test_float_type() {
        val message = mapOf(
                "message" to mapOf("hello" to "world", ":type" to 0, "some" to mapOf(":type" to 1f, "i" to "1"))
        )
        engine.deserialize(engine.serialize(message))
    }

    @Test
    fun test_biginteger_type() {
        val message = mapOf(
                "message" to mapOf("hello" to "world", ":type" to 0, "some" to mapOf(":type" to BigInteger.ONE, "i" to "1"))
        )
        engine.deserialize(engine.serialize(message))
    }

    @Test
    fun test_bigdecimal_type() {
        val message = mapOf(
                "message" to mapOf("hello" to "world", ":type" to 0, "some" to mapOf(":type" to BigDecimal(1), "i" to "1"))
        )
        engine.deserialize(engine.serialize(message))
    }

    @Test
    fun test_string_type() {
        val message = mapOf(
                "message" to mapOf("hello" to "world", ":type" to 0, "some" to mapOf(":type" to "Y", "i" to "1"))
        )
        engine.deserialize(engine.serialize(message))
    }
}
