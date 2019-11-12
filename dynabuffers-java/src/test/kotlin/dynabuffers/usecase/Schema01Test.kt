package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import org.junit.jupiter.api.Test

class Schema01Test : AbstractDynabuffersTest() {

    @Test
    fun testParse() {
        val engine = Dynabuffers.parse(Schema01Test::class.java.getResourceAsStream("/schema01.dbs"))
        val output = mapOf(
                "message" to mapOf("text" to "abcd", "type" to arrayOf("DEP", "NER"), "_type" to 0))
        assertResultMap(engine, mapOf(
                "message" to mapOf("text" to "abcd", "type" to arrayOf("DEP", "NER"))), output)
    }
}
