package dynabuffers.ast.datatype

import dynabuffers.ast.AbstractAST
import java.nio.ByteBuffer

class RefType(private val options: RefTypeOptions) : AbstractAST() {

    override fun size(value: Any, registry: IRegistry) = registry.resolve(options.name).size(value, registry)

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        registry.resolve(options.name).serialize(value, buffer, registry)
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any {
        return registry.resolve(options.name).deserialize(buffer, registry)
    }

    data class RefTypeOptions(val name:String)

}
