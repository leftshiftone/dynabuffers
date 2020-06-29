package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.util.*

class Schema15Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema08Test::class.java.getResourceAsStream("/schema15.dbs"))
    }

    @TestFactory
    fun test() = listOf(
            9223372036854775807,
            2147483647,
            -2147483648

    ).map {
        DynamicTest.dynamicTest("serializes map with $it") {
            val message = mapOf(
                    "someMap" to mapOf("someValue" to it)
            )
            val result = engine.deserialize(engine.serialize(message))
            assertMap(message, result)
        }
    }
}
