package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import org.junit.jupiter.api.Test

/**
 * @author benjamin.krenn@leftshift.one - 10/25/19.
 * @since 0.1.0
 */
class Schema02Test : AbstractDynabuffersTest() {

    @Test
    fun testParse() {
        val engine = Dynabuffers.parse(Schema02Test::class.java.getResourceAsStream("/schema02.dbs"))
        assertMap(engine, mapOf("message" to mapOf("wasteType" to "Schuhe unbeschädigt", "point" to mapOf("lat" to 47.070890f, "lng" to 15.439279f))))
    }

}
