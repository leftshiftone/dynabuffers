package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class Schema14Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema08Test::class.java.getResourceAsStream("/schema14.dbs"))
    }

    @Test
    fun test() {
        val message = mapOf(
                "uuid" to UUID.randomUUID().toString(),
                "payload" to mapOf(
                        ":type" to 0,
                        "identityId" to UUID.randomUUID().toString(),
                        "eventName" to "test",
                        "eventData" to mapOf("a" to "b")
                ),
                "replyTo" to "asdf",
                "timeout" to 1000
                )
        val result = engine.deserialize(engine.serialize(message))

        assertMap(message, result)
    }

}
