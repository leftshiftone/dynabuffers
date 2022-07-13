package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class Schema26Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema26Test::class.java.getResourceAsStream("/schema26.dbs"))
    }

    @Test
    fun `test defaults`() {
        val reference = mapOf<String, Any>(
            "text" to "123",
            "oneInt" to 1,
            "twoFloat" to 2.0f,
            "threeShort" to 3.toShort(),
            "fourLong" to 4L,
            "trueBoolean" to true,
            "falseBoolean" to false
        )
        val serialized = engine.serialize(reference)
        val deserialized = engine.deserialize(serialized)
        Assertions.assertThat(deserialized).isEqualTo(reference)
    }
}
