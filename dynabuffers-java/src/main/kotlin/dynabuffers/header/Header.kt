package dynabuffers.header

import dynabuffers.NamespaceDescription
import dynabuffers.NamespaceResolver
import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import java.nio.ByteBuffer
import kotlin.experimental.and
import kotlin.math.ceil

class Header(
    private val namespaceResolver: NamespaceResolver,
    private val version: Byte,
    private val flags: Byte = 0
) : ISerializable {
    private val numberOfFlagBits = 5

    override fun size(value: Any?, registry: IRegistry): Int {
        val namespacePathSize = getNamespacePath(value as Map<String, Any?>).size
        return 2 + ceil(namespacePathSize / 2.0).toInt() // 1 Version + 1 Namespace Depth/Flags + 0 - 4 Namespace Path Indicator
    }

    private fun getNamespacePath(map: Map<String, Any?>): List<Byte> {
        return namespaceResolver.getNamespace(map).getWaypoints().map { it.position }
    }

    override fun serialize(value: Any?, buffer: ByteBuffer, registry: IRegistry) {
        // 1. Byte: Version
        buffer.put(version.toByte())
        // 2. Byte: Namespace depth + flag bits
        val namespacePath = getNamespacePath(value as Map<String, Any?>)
        val namespaceDepth = Subbyte(namespacePath.size, 8 - numberOfFlagBits, "Namespace Depth")
        val flagBits = Subbyte(flags, numberOfFlagBits, "Flags")
        buffer.put(Subbyte.compressValuesIntoByte(listOf(namespaceDepth, flagBits)))
        // 3. up to 6. Byte: Namespace path
        compress4BitToBytes(namespacePath).forEach { buffer.put(it) }
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): HeaderSpec {
        val deserializedVersion = buffer.get()

        val secondByte = buffer.get()
        val flagBits = secondByte and (0xFF shr (8 - numberOfFlagBits)).toByte()
        val namespaceDepth = secondByte.toInt() shr numberOfFlagBits

        val namespaceDescription = namespaceResolver.getNamespace(buffer, namespaceDepth)

        return HeaderSpec(deserializedVersion, flagBits, namespaceDescription)
    }

    private fun compress4BitToBytes(bits: List<Byte>): List<Byte> {
        val bitsToCompress = if (bits.size % 2 == 0) bits else bits.plus(0)

        return bitsToCompress
            .chunked(2) {
                val firstNamespace = Subbyte(it[0], 4, "Namespace Path Indicator")
                val secondNamespace = Subbyte(it[1], 4, "Namespace Path Indicator")
                Subbyte.compressValuesIntoByte(listOf(firstNamespace, secondNamespace))
            }
    }
}

data class HeaderSpec(val version: Byte, val flags: Byte, val namespaceDescription: NamespaceDescription)