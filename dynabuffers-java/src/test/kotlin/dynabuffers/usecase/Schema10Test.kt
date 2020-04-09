package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Schema10Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema08Test::class.java.getResourceAsStream("/schema10.dbs"))
    }

    @Test
    fun testShortPositiveBoundary() {
        val output = mapOf("val" to Long.MAX_VALUE)

        assertMap(engine, mapOf("val" to Long.MAX_VALUE), output)
    }

    @Test
    fun testShortNegativeBoundary() {
        val output = mapOf("val" to Long.MIN_VALUE)

        assertMap(engine, mapOf("val" to Long.MIN_VALUE), output)
    }

}
