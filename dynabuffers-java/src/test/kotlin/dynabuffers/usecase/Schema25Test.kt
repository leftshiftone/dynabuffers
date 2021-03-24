package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class Schema25Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema08Test::class.java.getResourceAsStream("/schema25.dbs"))
    }

    @Test
    fun `test second schema`() {
        val reference = mapOf<String, Any>("matches" to arrayOf(mapOf("identityId" to "id", "score" to 1.0f)))
        val serialized = engine.serialize(reference, "outgoing")
        val deserialized = engine.deserialize(serialized)
        Assertions.assertThat(deserialized["matches"]).isEqualTo(reference["matches"])
    }
}
