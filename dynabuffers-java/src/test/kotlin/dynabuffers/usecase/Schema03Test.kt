package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import org.junit.jupiter.api.Test

class Schema03Test : AbstractDynabuffersTest() {

    @Test
    fun testParse() {
        val engine = Dynabuffers.parse(Schema03Test::class.java.getResourceAsStream("/schema03.dbs"))
        assertMap(engine, mapOf("results" to arrayOf<Map<String,Any>>(mapOf("text" to "hello world!"))))
    }

    @Test
    fun handlesEmptyArrays() {
        val engine = Dynabuffers.parse(Schema03Test::class.java.getResourceAsStream("/schema03.dbs"))
        assertMap(engine, mapOf("results" to emptyArray<Map<String,Any>>()))
    }
}
