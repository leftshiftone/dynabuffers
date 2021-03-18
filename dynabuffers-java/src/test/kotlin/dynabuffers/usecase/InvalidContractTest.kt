package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class InvalidContractTest : AbstractDynabuffersTest() {

   @Test
   fun `throws on parse if invalid contract is parsed`() {
       // should throw here
       val engine = Dynabuffers.parse(InvalidContractTest::class.java.getResourceAsStream("/invalid_contract.dbs"))
       val m = mapOf("text" to "hallo welt!")
       // does throw here
       val serialized = engine.serialize(m)
       val deserialized = engine.deserialize(serialized)
       Assertions.assertThat(deserialized["amicool"]).isEqualTo(true)
   }
}
