package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import org.junit.jupiter.api.Test

class Schema06Test : AbstractDynabuffersTest() {

    @Test
    fun testParse() {
        val engine = Dynabuffers.parse(Schema06Test::class.java.getResourceAsStream("/schema06.dbs"))
        val output = mapOf("works" to "YES")

        assertMap(engine, mapOf("works" to "YES"), output)
    }

}
