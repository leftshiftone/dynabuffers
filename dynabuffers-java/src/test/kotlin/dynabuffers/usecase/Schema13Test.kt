package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Schema13Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema08Test::class.java.getResourceAsStream("/schema13.dbs"))
    }

    @Test
    fun testTypeHint() {
        val onUpdate = mapOf("message" to mapOf("type" to "query", "data" to "abc".toByteArray(), ":type" to 1))
        val result = engine.deserialize(engine.serialize(onUpdate))

        assertMap(onUpdate, result)
    }

}
