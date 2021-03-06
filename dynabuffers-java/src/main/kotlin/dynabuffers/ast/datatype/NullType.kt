package dynabuffers.ast.datatype

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import java.nio.ByteBuffer

class NullType : IType, ISerializable {

    override fun size(value: Any?, registry: IRegistry) = 0

    override fun serialize(value: Any?, buffer: ByteBuffer, registry: IRegistry) {
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Nothing? = null
}
