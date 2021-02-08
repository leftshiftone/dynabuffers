package dynabuffers

import dynabuffers.exception.DynabuffersException
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class DynabuffersLimitsTest : AbstractDynabuffersTest() {
    @Test
    fun `Schema with too many (8) nested namespaces`() {
        val engine = Dynabuffers.parse(
            """
            namespace abc{
                namespace abc{
                    namespace abc{
                        namespace abc{
                            namespace abc{
                                namespace abc{
                                    namespace abc{
                                        namespace abc{
                                            class Data {
                                                value: string
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }  
            }
            """.trimIndent()
        )
        assertThatThrownBy {
            engine.serialize(mapOf("value" to "someString"), "abc.abc.abc.abc.abc.abc.abc.abc")
        }.isInstanceOf(DynabuffersException::class.java)
    }

    @Test
    fun `Schema with too many (17) namespaces per layer`() {
        val engine = Dynabuffers.parse(
            """
                namespace n0 {} namespace n1 {}
                namespace n2 {} namespace n3 {}
                namespace n4 {} namespace n5 {}
                namespace n6 {} namespace n7 {}
                namespace n8 {} namespace n9 {}
                namespace n10 {} namespace n11 {}
                namespace n12 {} namespace n13 {}
                namespace n14 {} namespace n15 {}
                namespace n16 { 
                    class Data {
                        value: string
                    } 
                }
            """.trimIndent()
        )
        assertThatThrownBy {
            engine.serialize(mapOf("value" to "someString"), "n16")
        }.isInstanceOf(DynabuffersException::class.java)
    }
}