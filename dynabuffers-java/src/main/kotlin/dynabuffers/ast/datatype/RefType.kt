package dynabuffers.ast.datatype

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import java.nio.ByteBuffer

class RefType(val options: RefTypeOptions) : IType, ISerializable {

    override fun size(value: Any, registry: IRegistry) = registry.resolve(options.name).size(value, registry)

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        registry.resolve(options.name).serialize(value, buffer, registry)
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any {
        return registry.resolve(options.name).deserialize(buffer, registry)
    }

    override fun supports(value: Any): Boolean {
        return value is Map<*, *>
    }

    data class RefTypeOptions(val name: String)

}
