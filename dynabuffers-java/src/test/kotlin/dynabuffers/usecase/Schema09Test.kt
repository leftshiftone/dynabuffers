package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.NumberFormatException

class Schema09Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema08Test::class.java.getResourceAsStream("/schema09.dbs"))
    }

    @Test
    fun testShortPositiveBoundary() {
        val output = mapOf("val" to Int.MAX_VALUE)

        assertMap(engine, mapOf("val" to Int.MAX_VALUE), output)
    }

    @Test
    fun testShortNegativeBoundary() {
        val output = mapOf("val" to Int.MIN_VALUE)

        assertMap(engine, mapOf("val" to Int.MIN_VALUE), output)
    }

    @Test
    fun testShortPositiveOverflow() {
        assertThatThrownBy { engine.serialize(mapOf("val" to Int.MAX_VALUE.toLong() + 1)) }
            .isInstanceOf(NumberFormatException::class.java)

    }

    @Test
    fun testShortNegativeOverflow() {
        assertThatThrownBy { engine.serialize(mapOf("val" to Int.MIN_VALUE.toLong() - 1)) }
            .isInstanceOf(NumberFormatException::class.java)
    }

}
