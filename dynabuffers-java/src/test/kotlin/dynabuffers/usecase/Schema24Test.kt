package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class Schema24Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema08Test::class.java.getResourceAsStream("/schema24.dbs"))
    }

   @Test
   fun `the usual stuff`() {
       val reference = mapOf<String,Any>("ner" to arrayOf(mapOf("text" to "asdf1234")))
       val serialized = engine.serialize(reference)
       val deserialized = engine.deserialize(serialized)
       Assertions.assertThat(deserialized["ner"]).isEqualTo(reference["ner"])
   }
}
