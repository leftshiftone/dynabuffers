package dynabuffers.ast

import dynabuffers.NamespaceDescription
import dynabuffers.NamespaceResolver
import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.assertOnlyNFirstBitsSet
import dynabuffers.checkedShiftLeft
import java.nio.ByteBuffer
import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.math.ceil

data class HeaderSpec(val version: Byte, val reserved: Byte, val namespaceDescription: NamespaceDescription)

class Header(
    private val namespaceResolver: NamespaceResolver,
    private val version: Byte,
    private val reserved: Byte = 0
) : ISerializable {
    private val numberOfReservedBits = 5

    override fun size(value: Any?, registry: IRegistry): Int {
        val namespacePathSize = getNamespacePath(value as Map<String, Any?>).size
        return 2 + ceil(namespacePathSize / 2.0).toInt() // 1 Version + 1 Namespace Depth/Reserved + 0 - 4 Namespace Identifier
    }

    private fun getNamespacePath(map: Map<String, Any?>): List<Byte> {
        return namespaceResolver.getNamespace(map).getWaypoints().map { it.position }
    }

    override fun serialize(value: Any?, buffer: ByteBuffer, registry: IRegistry) {
        // 1. Byte: Version
        buffer.put(version)
        // 2. Byte: Namespace depth + reserved bits
        val namespacePath = getNamespacePath(value as Map<String, Any?>)
        val namespaceDepth = namespacePath.size.toByte().checkedShiftLeft(numberOfReservedBits)
        buffer.put(namespaceDepth or reserved.assertOnlyNFirstBitsSet(numberOfReservedBits))
        // 3. up to 6. Byte: Namespace path
        compress4BitToBytes(namespacePath).forEach { buffer.put(it) }
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): HeaderSpec {
        val deserializedVersion = buffer.get()

        val secondByte = buffer.get()
        val reservedBits = secondByte and (0xFF shr (8 - numberOfReservedBits)).toByte()
        val namespaceDepth = secondByte.toInt() shr numberOfReservedBits

        val namespaceDescription = namespaceResolver.getNamespace(buffer, namespaceDepth)

        return HeaderSpec(deserializedVersion, reservedBits, namespaceDescription)
    }

    private fun compress4BitToBytes(bits: List<Byte>): List<Byte> {
        val bitsToCompress = if (bits.size % 2 == 0) bits else bits.plus(0)
        return bitsToCompress
            .chunked(2) {
                (it[0].checkedShiftLeft(4) + it[1].assertOnlyNFirstBitsSet(4)).toByte()
            }
    }
}
