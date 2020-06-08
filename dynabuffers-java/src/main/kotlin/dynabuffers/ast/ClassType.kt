package dynabuffers.ast

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import dynabuffers.ast.datatype.OptionType
import dynabuffers.ast.structural.ClassOptions
import dynabuffers.exception.DynabuffersException
import java.nio.ByteBuffer
import java.util.*

data class ClassType(val options: ClassTypeOptions) : IType, ISerializable {

    override fun size(value: Any, registry: IRegistry): Int {
        val map = HashMap(value as Map<*, *>)

        for (field in options.fields) {
            if (map[field.options.name] == null && field.options.defaultVal == null) {
                if (field.options.dataType is OptionType)
                    map[field.options.name] = Optional.empty<Any>()
                else
                    throw DynabuffersException("field '${field.options.name}' is missing")
            }
        }

        return options.fields
                .filter { map.containsKey(it.options.name) || it.options.defaultVal != null }
                .map {
                    if (map.containsKey(it.options.name))
                        it.size(map[it.options.name]!!, registry)
                    else
                        it.size(it.options.defaultVal!!, registry)
                }.sum()
    }

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        validate(registry)
        val map = HashMap(value as Map<*, *>)

        for (field in options.fields) {
            if (map[field.options.name] == null && field.options.defaultVal == null) {
                if (field.options.dataType is OptionType)
                    map[field.options.name] = Optional.empty<Any>()
                else
                    throw DynabuffersException("field '${field.options.name}' is missing")
            }
        }

        options.fields
                .filter { map.containsKey(it.options.name) || it.options.defaultVal != null }
                .forEach {
                    if (map.containsKey(it.options.name))
                        it.serialize(map[it.options.name]!!, buffer, registry)
                    else
                        it.serialize(it.options.defaultVal!!, buffer, registry)
                }
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any {
        val map = HashMap<String, Any>()
        options.fields.forEach { map.put(it.options.name, it.deserialize(buffer, registry)) }

        validate(registry)
        return map
    }

    private fun validate(registry: IRegistry) {
        if (options.options.isDeprecated()) {
            registry.addNotification("deprecated class ${options.name} used.")
        }
    }

    data class ClassTypeOptions(val name: String, val fields: List<FieldType>, val options: ClassOptions)

}
