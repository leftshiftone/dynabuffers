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

}
