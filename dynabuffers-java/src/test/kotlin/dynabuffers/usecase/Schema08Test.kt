package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.NumberFormatException

class Schema08Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema08Test::class.java.getResourceAsStream("/schema08.dbs"))
    }

    @Test
    fun testShortPositiveBoundary() {
        val output = mapOf("val" to Short.MAX_VALUE)

        assertMap(engine, mapOf("val" to Short.MAX_VALUE), output)
    }

    @Test
    fun testShortNegativeBoundary() {
        val output = mapOf("val" to Short.MIN_VALUE)

        assertMap(engine, mapOf("val" to Short.MIN_VALUE), output)
    }

    @Test
    fun testShortPositiveOverflow() {
        assertThatThrownBy { engine.serialize(mapOf("val" to Short.MAX_VALUE + 1)) }
            .isInstanceOf(NumberFormatException::class.java)
    }

    @Test
    fun testShortNegativeOverflow() {
        assertThatThrownBy { engine.serialize(mapOf("val" to Short.MIN_VALUE - 1)) }
            .isInstanceOf(NumberFormatException::class.java)
    }

}
