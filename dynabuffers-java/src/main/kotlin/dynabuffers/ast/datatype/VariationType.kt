package dynabuffers.ast.datatype

import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import dynabuffers.exception.DynabuffersException
import java.nio.ByteBuffer

class VariationType(private val options: VariationTypeOptions) : IType, ISerializable {

    override fun size(value: Any, registry: IRegistry): Int {
        return resolve(value).second.size(value, registry) + 1
    }

    override fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry) {
        val pair = resolve(value)
        buffer.put(pair.first)
        pair.second.serialize(value, buffer, registry)
    }

    override fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any {
        val datatype = options.datatypes[buffer.get().toInt()]
        return datatype.deserialize(buffer, registry)
    }

    private fun resolve(value: Any):Pair<Byte, ISerializable> {
        return options.datatypes.mapIndexed { i, s ->  Pair(i.toByte(), s)}.find { it.second.supports(value) }!!
    }

    override fun supports(value: Any): Boolean {
        return options.datatypes.any { it.supports(value) }
    }

    data class VariationTypeOptions(val datatypes: List<ISerializable>)

}
