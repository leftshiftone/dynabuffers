package dynabuffers

import dynabuffers.api.IType
import dynabuffers.ast.NamespaceType
import dynabuffers.exception.DynabuffersException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.lang.Math.ceil
import java.nio.ByteBuffer
import kotlin.math.ceil

internal class NamespaceResolverTest {


    @TestFactory
    fun `gets nested namespaces from the given tree`() = listOf(
        TestCase(listOf("first", "first.first"), listOf(0, 0)),
        TestCase(listOf("first", "first.second"), listOf(0, 1)),
        TestCase(listOf("first"), listOf(0)),
        TestCase(listOf("first", "first.first", "first.first.first"), listOf(0, 0, 0)),
        TestCase(listOf("first", "first.first", "first.first.first", "first.first.first.first"), listOf(0, 0, 0, 0)),
        TestCase(listOf("second", "second.first"), listOf(1, 0)),
        TestCase(listOf("second", "second.second"), listOf(1, 1)),
        TestCase(listOf("second", "second.first", "second.first.first"), listOf(1, 0, 0)),
        TestCase(listOf("second", "second.first", "second.first.second"), listOf(1, 0, 1))
    ).map { testCase ->
        DynamicTest.dynamicTest("satisfies $testCase") {
            val classUnderTest = NamespaceResolver(testTree())

            val result = classUnderTest.getNamespace(testCase.namespaces)

            Assertions.assertThat(result).isInstanceOf(ConcreteNamespaceDescription::class.java)
            result as ConcreteNamespaceDescription
            Assertions.assertThat(result.namespace.options.name).isEqualTo(testCase.namespaces.last())
            Assertions.assertThat(result.path.map { it.position }
            ).isEqualTo(testCase.expectedLevels)
        }
    }

    @TestFactory
    fun `extracts namespaces from a given bytearray`() = listOf(
        TestCaseSerialized(listOf(1, 0, 1), "second.first.second"),
        TestCaseSerialized(listOf(0, 0, 0, 0), "first.first.first.first"),
        TestCaseSerialized(listOf(1, 1), "second.second")
    ).map { testCase ->
        DynamicTest.dynamicTest("satisfies $testCase") {
            val classUnderTest = NamespaceResolver(testTree())

            val bb = ByteBuffer.allocate(ceil(testCase.path.size / 2f).toInt())
            testCase.path.chunked(2)
                .forEach {
                    if (it.size == 1) bb.put((it[0].toInt() shl 4).toByte())
                    else bb.put(((it[0].toInt() shl 4) + it[1]).toByte())
                }
            bb.clear()

            val result = classUnderTest.getNamespace(bb, testCase.path.size)
            Assertions.assertThat(result).isInstanceOf(ConcreteNamespaceDescription::class.java)
            result as ConcreteNamespaceDescription
            Assertions.assertThat(result.namespace.options.name).isEqualTo(testCase.expectedNamespaceName)
        }
    }

    @Test
    fun `does return the default namespace if possible`() {
        val classUnderTest = NamespaceResolver(
            listOf(
                NamespaceType(
                    NamespaceType.NamespaceTypeOptions("first", emptyList()), listOf(
                        NamespaceType(
                            NamespaceType.NamespaceTypeOptions("second", emptyList()), listOf(
                                NamespaceType(NamespaceType.NamespaceTypeOptions("third", emptyList()), emptyList())
                            )
                        )
                    )
                )
            )
        )

        val result = classUnderTest.getNamespace(null)

        Assertions.assertThat(result).isInstanceOf(ConcreteNamespaceDescription::class.java)
        result as ConcreteNamespaceDescription
        Assertions.assertThat(result.namespace.options.name).isEqualTo("third")
        Assertions.assertThat(result.path.map { it.position.toInt() }).isEqualTo(listOf(0, 0, 0))
    }

    @TestFactory
    fun `throws if no default namespace could be inferred`() = listOf(
        listOf(
            NamespaceType(
                NamespaceType.NamespaceTypeOptions("first", emptyList()), listOf(
                    NamespaceType(NamespaceType.NamespaceTypeOptions("first.first", emptyList()), emptyList()),
                    NamespaceType(NamespaceType.NamespaceTypeOptions("first.second", emptyList()), emptyList())
                )
            )
        ),
        listOf(
            NamespaceType(NamespaceType.NamespaceTypeOptions("first", emptyList()), emptyList()),
            NamespaceType(NamespaceType.NamespaceTypeOptions("second", emptyList()), emptyList())
        )

    ).map {
        DynamicTest.dynamicTest("throws expected exception") {
            val classUnderTest = NamespaceResolver(it)

            Assertions.assertThatThrownBy {
                classUnderTest.getNamespace(null)
            }.isInstanceOf(DynabuffersException::class.java).hasMessage("Could not infer default namespace")
        }
    }

    data class TestCase(val namespaces: List<String>, val expectedLevels: List<Byte>)
    data class TestCaseSerialized(val path: List<Byte>, val expectedNamespaceName: String)

    private fun testTree() = listOf<IType>(
        NamespaceType(
            NamespaceType.NamespaceTypeOptions("first", emptyList()),
            listOf(
                NamespaceType(
                    NamespaceType.NamespaceTypeOptions("first.first", emptyList()),
                    listOf(
                        NamespaceType(
                            NamespaceType.NamespaceTypeOptions("first.first.first", emptyList()),
                            listOf(
                                NamespaceType(
                                    NamespaceType.NamespaceTypeOptions(
                                        "first.first.first.first",
                                        emptyList()
                                    )
                                )
                            )
                        )
                    )
                ),
                NamespaceType(
                    NamespaceType.NamespaceTypeOptions("first.second", emptyList()), listOf(
                        NamespaceType(NamespaceType.NamespaceTypeOptions("first.second.first", emptyList()))
                    )
                )
            )
        ),
        NamespaceType(
            NamespaceType.NamespaceTypeOptions("second", emptyList()), listOf(
                NamespaceType(
                    NamespaceType.NamespaceTypeOptions("second.first", emptyList()),
                    listOf(
                        NamespaceType(NamespaceType.NamespaceTypeOptions("second.first.first", emptyList())),
                        NamespaceType(NamespaceType.NamespaceTypeOptions("second.first.second", emptyList()))
                    )
                ),
                NamespaceType(
                    NamespaceType.NamespaceTypeOptions("second.second", emptyList()),
                    listOf(
                        NamespaceType(NamespaceType.NamespaceTypeOptions("second.second.first", emptyList()))
                    )
                )
            )
        )
    )
}
