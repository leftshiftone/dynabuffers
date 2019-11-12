package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import org.junit.jupiter.api.Test

class Schema02Test : AbstractDynabuffersTest() {

    @Test
    fun testParse() {
        val engine = Dynabuffers.parse(Schema02Test::class.java.getResourceAsStream("/schema02.dbs"))

        val input = mapOf(
                "message" to mapOf("wasteType" to "Schuhe unbeschädigt", "point" to mapOf("lat" to 47.070890f, "lng" to 15.439279f)))

        val output = mapOf(
                "message" to mapOf("wasteType" to "Schuhe unbeschädigt", "point" to mapOf("lat" to 47.070890f, "lng" to 15.439279f), ":type" to 0))

        assertResultMap(engine, input, output)
    }

}
