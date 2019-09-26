package dynabuffers.ast

import java.nio.ByteBuffer

data class FieldType(val options: FieldTypeOptions) : AbstractAST() {

    override fun size(value: Any, registry: IRegistry) = options.dataType.size(value, registry)

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        if (options.deprecated)
            registry.addNotification("deprecated field ${options.name} used.")

        options.dataType.serialize(value, buffer, registry)
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any {
        if (options.deprecated)
            registry.addNotification("deprecated field ${options.name} used.")

        return options.dataType.deserialize(buffer, registry)
    }

    data class FieldTypeOptions(val name: String, val dataType: AbstractAST, val deprecated:Boolean, val defaultVal:Any?)

}
