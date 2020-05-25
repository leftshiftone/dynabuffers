package dynabuffers.api

import java.nio.ByteBuffer

interface ISerializable {
    fun size(value: Any, registry: IRegistry): Int
    fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry)
    fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any
    fun supports(value: Any): Boolean
}
