package dynabuffers

import dynabuffers.exception.DynabuffersException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class TypeValidatorTest {

    @Test
    fun `invalid type name throws exception`() {
        Assertions.assertThatThrownBy {
            Dynabuffers.parse(
                """
             class Test {
                someVariable: invalidtype
             }
            """.trimIndent()
            )
        }.isInstanceOf(DynabuffersException::class.java)
    }

    @Test
    fun `invalid class reference to different namespace throws exception`() {
        Assertions.assertThatThrownBy {
            Dynabuffers.parse(
                """
             namespace One {
                    class First {
                        someVar: string
                   }
             }
             namespace Two {
                   class Second {
                     someVar: First
                   }
             }
            """.trimIndent()
            )
        }.isInstanceOf(DynabuffersException::class.java)
    }

    @Test
    fun `valid class reference to same namespace does not throw exception`() {
        Dynabuffers.parse(
            """
            namespace One {
                class First {
                    someVar: string
               }
               class Second {
                 someVar: First
               }
            }
            """.trimIndent()
        )
    }
}
