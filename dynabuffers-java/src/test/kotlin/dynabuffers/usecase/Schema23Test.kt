package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class Schema23Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema08Test::class.java.getResourceAsStream("/schema23.dbs"))
    }

   @Test
   fun `the usual stuff`() {
       val serialized = engine.serialize(mapOf("amicool" to true))
       val deserialized = engine.deserialize(serialized)
       Assertions.assertThat(deserialized["amicool"]).isEqualTo(true)
   }
}
