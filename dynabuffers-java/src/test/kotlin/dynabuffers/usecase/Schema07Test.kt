package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Schema07Test : AbstractDynabuffersTest() {

    @Test
    fun testParse() {
        val imageBytes = Schema07Test::class.java.getResourceAsStream("/_data/1.jpg").readAllBytes()
        val engine = Dynabuffers.parse(Schema07Test::class.java.getResourceAsStream("/schema07.dbs"))
        val output = mapOf("image" to imageBytes)
        val result = engine.serialize(output)
        val serialized = engine.deserialize(result)

        Assertions.assertThat(result.size < 25_000).isTrue()
        Assertions.assertThat(serialized["image"]).isEqualTo(imageBytes)
    }

}
