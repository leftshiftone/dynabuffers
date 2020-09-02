package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import dynabuffers.SpecialKey
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Schema20Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema20Test::class.java.getResourceAsStream("/schema20.dbs"))
    }

    @Test
    fun namespaceTest() {
        val message = mapOf("contentA" to "abc")
        val serializedMessage = engine.serialize(message, listOf("`leftshiftone/echo`", "incoming"))

        val result = engine.deserialize(serializedMessage, listOf("`leftshiftone/echo`", "incoming"))

        assertMap(message.plus(SpecialKey.NAMESPACE.key to "`leftshiftone/echo`.incoming"), result)
    }

}
