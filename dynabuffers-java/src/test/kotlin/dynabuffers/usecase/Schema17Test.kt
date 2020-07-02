package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Schema17Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema17Test::class.java.getResourceAsStream("/schema17.dbs"))
    }

    @Test
    fun namespaceTest() {
        val message = mapOf("contentA" to "abc")
        val result = engine.deserialize("request", engine.serialize("request", message))

        assertMap(message, result)
    }

}
