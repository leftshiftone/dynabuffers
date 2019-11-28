package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import org.junit.jupiter.api.Test

class Schema05Test : AbstractDynabuffersTest() {

    @Test
    fun testParse() {
        val engine = Dynabuffers.parse(Schema05Test::class.java.getResourceAsStream("/schema05.dbs"))
        val output = mapOf("strings" to arrayOf("a", "b", "c"))

        assertMap(engine, mapOf("strings" to listOf("a", "b", "c")), output)
    }

}
