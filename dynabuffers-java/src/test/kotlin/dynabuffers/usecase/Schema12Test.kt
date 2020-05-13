package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Schema12Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema08Test::class.java.getResourceAsStream("/schema12.dbs"))
    }

    @Test
    fun testBytearray() {
        val envelope = mapOf("type" to "query", "data" to "abcdefghijklmnopqrstuvwxyz".toByteArray())
        assertMap(engine, envelope, envelope)
    }

}
