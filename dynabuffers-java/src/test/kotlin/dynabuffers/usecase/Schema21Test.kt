package dynabuffers.usecase

import dynabuffers.AbstractDynabuffersTest
import dynabuffers.Dynabuffers
import dynabuffers.DynabuffersEngine
import dynabuffers.SpecialKey
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.*

class Schema21Test : AbstractDynabuffersTest() {

    lateinit var engine: DynabuffersEngine

    @BeforeEach
    fun setUp() {
        engine = Dynabuffers.parse(Schema21Test::class.java.getResourceAsStream("/schema21.dbs"))
    }

    @Test
    fun `first namespace (incoming) can be serialized`() {
        val message = mapOf("request" to "hello world", SpecialKey.NAMESPACE.key to "first.incoming")
        val serializedMessage = engine.serialize(message)
        val result = engine.deserialize(serializedMessage)
        print(Base64.getEncoder().encodeToString(serializedMessage))
        assertMap(message, result)
    }

    @Test
    fun `first namespace (outgoing) can be serialized`() {
        val message = mapOf("response" to "hello world", SpecialKey.NAMESPACE.key to "first.outgoing")
        val serializedMessage = engine.serialize(message)
        val result = engine.deserialize(serializedMessage)
        assertMap(message, result)
    }

    @Test
    fun `second namespace (incoming) can be serialized`() {
        val message = mapOf("request" to "hello world", SpecialKey.NAMESPACE.key to "second.incoming")
        val serializedMessage = engine.serialize(message)
        val result = engine.deserialize(serializedMessage)
        assertMap(message, result)
    }

    @Test
    fun `second namespace (outgoing) can be serialized`() {
        val message = mapOf("response" to "hello world", SpecialKey.NAMESPACE.key to "second.outgoing")
        val serializedMessage = engine.serialize(message)
        val result = engine.deserialize(serializedMessage)
        assertMap(message, result)
    }
}
