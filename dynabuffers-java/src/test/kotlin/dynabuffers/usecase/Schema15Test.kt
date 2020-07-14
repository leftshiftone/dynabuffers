package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Schema15Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine;

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema15Test::class.java.getResourceAsStream("/schema15.dbs"))
    }

    @Test
    fun resolveUnionTypeWithOrdinal() {
        val message = mapOf(
                "name" to "test",
                "content" to "text".toByteArray(),
                ":type" to 1
                )
        val result = engine.deserialize(engine.serialize(message))

        assertMap(message, result)
    }

    @Test
    fun resolveUnionTypeWithQualifier() {
        val message = mapOf(
                "name" to "test",
                "content" to "text".toByteArray(),
                ":type" to "ByteArrayData"
        )
        val result = engine.deserialize(engine.serialize(message))

        assertMap(mapOf(
                "name" to "test",
                "content" to "text".toByteArray(),
                ":type" to 1
        ), result)
    }

}
