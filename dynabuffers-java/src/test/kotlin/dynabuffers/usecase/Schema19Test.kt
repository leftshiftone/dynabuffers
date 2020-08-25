package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Schema19Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema19Test::class.java.getResourceAsStream("/schema19.dbs"))
    }

    @Test
    fun namespaceTest() {
        val message = mapOf("contentA" to "abc")
        val serializedMessage = engine.serialize(message, listOf("echo", "incoming"))
        val result = engine.deserialize(serializedMessage, listOf("echo", "incoming"))
        assertMap(message, result)
    }

}
