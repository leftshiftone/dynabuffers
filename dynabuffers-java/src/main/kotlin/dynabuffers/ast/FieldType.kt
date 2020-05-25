package dynabuffers.ast

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import dynabuffers.ast.structural.Annotation
import dynabuffers.ast.structural.FieldOptions
import java.nio.ByteBuffer

data class FieldType(val options: FieldTypeOptions) : IType, ISerializable {

    override fun size(value: Any, registry: IRegistry) = options.dataType.size(value, registry)

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        if (options.options.isDeprecated())
            registry.addNotification("deprecated field ${options.name} used.")

        options.annotations.forEach { registry.resolveAnnotation(it).validate(options.name, value) }

        options.dataType.serialize(value, buffer, registry)
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any {
        if (options.options.isDeprecated())
            registry.addNotification("deprecated field ${options.name} used.")

        return options.dataType.deserialize(buffer, registry)
    }

    data class FieldTypeOptions(val name: String, val annotations: List<Annotation>, val dataType: ISerializable, val options: FieldOptions, val defaultVal: Any?)

}
