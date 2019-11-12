package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import org.junit.jupiter.api.Test

class Schema04Test : AbstractDynabuffersTest() {

    @Test
    fun testParse() {
        val engine = Dynabuffers.parse(Schema04Test::class.java.getResourceAsStream("/schema04.dbs"))
        val output = mapOf("m1" to mapOf("s" to "test", ":type" to 1), "m2" to mapOf("t" to "hello world!", ":type" to 0))

        assertMap(engine, mapOf("m1" to mapOf("s" to "test"), "m2" to mapOf("t" to "hello world!")), output)
    }

}
