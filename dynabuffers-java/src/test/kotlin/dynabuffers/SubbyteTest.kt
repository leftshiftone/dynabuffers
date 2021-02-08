package dynabuffers

import dynabuffers.exception.DynabuffersException
import dynabuffers.header.Subbyte
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class SubbyteTest {

    @TestFactory
    fun `create valid subbyte`() = listOf(
        TestCase(0, 1),
        TestCase(0, 8),
        TestCase(255, 8),
        TestCase(127, 7),
        TestCase(5, 4)
    ).map {
        DynamicTest.dynamicTest("valid subbyte does not throw exception") {
            Subbyte(it.value, it.numberOfBits, "Testvalue")
        }
    }

    @TestFactory
    fun `create invalid subbyte`() = listOf(
        TestCase(0, 9),
        TestCase(-1, 8),
        TestCase(256, 8),
        TestCase(12345, 8),
        TestCase(0, 0),
        TestCase(0, -1)
    ).map {
        DynamicTest.dynamicTest("invalid subbyte throws exception") {
            assertThatThrownBy { Subbyte(it.value, it.numberOfBits, "Testvalue") }
                .isInstanceOf(DynabuffersException::class.java)
        }
    }

    @ExperimentalUnsignedTypes
    @TestFactory
    fun `test compress subbytes to byte`() = listOf(
        CompressionTestCase(
            listOf(
                Subbyte(1, 1, "1"),
                Subbyte(1, 1, "2"),
                Subbyte(1, 1, "3"),
                Subbyte(1, 1, "4"),
                Subbyte(1, 1, "5"),
                Subbyte(1, 1, "6"),
                Subbyte(1, 1, "7"),
                Subbyte(1, 1, "8")
            ), 255
        ),
        CompressionTestCase(listOf(Subbyte(123, 8, "1-8")), 123),
        CompressionTestCase(listOf(Subbyte((255).toByte(), 8, "1-8")), 255),
        CompressionTestCase(listOf(Subbyte(15, 4, "1-4"), Subbyte(0, 4, "5-8")), 240)
    ).map {
        DynamicTest.dynamicTest("compress subbytes to byte successful") {
            assertThat(Subbyte.compressValuesIntoByte(it.values)).isEqualTo(it.result.toByte())
        }
    }

    @TestFactory
    fun `test compress invalid number of subbytes to byte`() = listOf(
        listOf(
            Subbyte(1, 1, "1"),
            Subbyte(1, 1, "2"),
            Subbyte(1, 1, "3"),
            Subbyte(1, 1, "4"),
            Subbyte(1, 1, "5"),
            Subbyte(1, 1, "6"),
            Subbyte(1, 1, "7"),
            Subbyte(1, 1, "8"),
            Subbyte(1, 1, "9")
        ),
        listOf(Subbyte(15, 4, "1-4"), Subbyte(0, 5, "5-8"))
    ).map {
        DynamicTest.dynamicTest("compress invalid number of subbytes to byte fails") {
            assertThatThrownBy { Subbyte.compressValuesIntoByte(it) }.isInstanceOf(DynabuffersException::class.java)
        }
    }

    internal data class TestCase(val value: Int, val numberOfBits: Int)
    internal data class CompressionTestCase(val values: List<Subbyte>, val result: Int)
}