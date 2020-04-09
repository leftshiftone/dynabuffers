package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
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
        Assertions.assertThrows(NumberFormatException::class.java) {
            engine.serialize(mapOf("val" to Short.MAX_VALUE + 1))
        }
    }

    @Test
    fun testShortNegativeOverflow() {
        Assertions.assertThrows(NumberFormatException::class.java) {
            engine.serialize(mapOf("val" to Short.MIN_VALUE - 1))
        }
    }

}
