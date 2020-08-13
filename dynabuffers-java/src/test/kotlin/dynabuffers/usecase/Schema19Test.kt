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
        val serializedMessage=engine.serialize(listOf("echo", "incoming"), message)
        val result = engine.deserialize(listOf("echo", "incoming"), serializedMessage)
        assertMap(message, result)
    }

}
