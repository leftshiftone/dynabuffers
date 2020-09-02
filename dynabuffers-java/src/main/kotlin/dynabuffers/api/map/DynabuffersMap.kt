package dynabuffers.api.map

import dynabuffers.SpecialKey
import dynabuffers.api.IType
import dynabuffers.ast.ClassType
import dynabuffers.ast.UnionType
import dynabuffers.ast.datatype.OptionType
import dynabuffers.ast.datatype.RefType
import dynabuffers.exception.DynabuffersException

open class DynabuffersMap(map: Map<String, Any?>,
                          private val tree: List<IType>,
                          root: IType) : HashMap<String, Any?>(map) {

    private val clazz = resolveClass(tree, root)

    override fun getOrDefault(key: String, defaultValue: Any?): Any? {
        if (!isDefined(key))
            throw DynabuffersException("field $key is not defined in ${clazz.options.name}")
        return super.getOrDefault(key, defaultValue)
    }

    override fun get(key: String): Any? {
        if (!isDefined(key))
            throw DynabuffersException("field $key is not defined in ${clazz.options.name}")

        val result = super.get(key)
        if (result == null && !isOptional(key))
            throw DynabuffersException("field $key is not optional in ${clazz.options.name}")

        @Suppress("UNCHECKED_CAST")
        if (result != null && isRefType(key)) {
            val refType = getRefType(key)
            if (refType != null)
                return DynabuffersMap(result as Map<String, Any?>, tree, refType)
        }

        return result
    }

    fun getRef(key: String): DynabuffersMap {
        val result = get(key)
        if (result is DynabuffersMap) return result
        throw DynabuffersException("field $key with result $result is not a ref type")
    }

    fun getString(key: String): String {
        val result = get(key)
        if (result is String) return result
        throw DynabuffersException("field $key with result $result is not of type string")
    }

    fun getInt(key: String): Int {
        val result = get(key)
        if (result is Number) return result.toInt()
        throw DynabuffersException("field $key with result $result is not of type int")
    }

    fun getShort(key: String): Short {
        val result = get(key)
        if (result is Number) return result.toShort()
        throw DynabuffersException("field $key with result $result is not of type short")
    }

    fun getByte(key: String): Byte {
        val result = get(key)
        if (result is Number) return result.toByte()
        throw DynabuffersException("field $key with result $result is not of type byte")
    }

    fun getLong(key: String): Long {
        val result = get(key)
        if (result is Number) return result.toLong()
        throw DynabuffersException("field $key with result $result is not of type long")
    }

    fun getFloat(key: String): Float {
        val result = get(key)
        if (result is Number) return result.toFloat()
        throw DynabuffersException("field $key with result $result is not of type float")
    }

    @Suppress("UNCHECKED_CAST")
    fun getMap(key: String): Map<String, Any?> {
        val result = get(key)
        if (result is Map<*, *>) return result as Map<String, Any?>
        throw DynabuffersException("field $key with result $result is not of type map")
    }

    fun getByteArray(key: String): ByteArray {
        val result = get(key)
        if (result is ByteArray) return result
        throw DynabuffersException("field $key with result $result is not of type [byte]")
    }

    /**
     * Indicates if the given key value is defined.
     */
    private fun isDefined(key: String): Boolean {
        return when (key) {
            ":type" -> true
            SpecialKey.NAMESPACE.key -> true
            else -> clazz.options.fields.any { it.options.name == key }
        }
    }

    /**
     * Indicates if the given key value is optional.
     */
    private fun isOptional(key: String): Boolean {
        if (key == ":type") return false
        val field = clazz.options.fields.find { it.options.name == key }
        return if (field != null) field.options.dataType is OptionType else false
    }

    /**
     * Indicates if the given key value is a RefType.
     */
    private fun isRefType(key: String): Boolean {
        if (key == ":type" || key == SpecialKey.NAMESPACE.key) return false
        val field = clazz.options.fields.find { it.options.name == key }!!
        return field.options.dataType is RefType
    }

    @Suppress("UNCHECKED_CAST")
    private fun getRefType(key: String): ClassType? {
        val field = clazz.options.fields.find { it.options.name == key }!!
        val name = (field.options.dataType as RefType).options.name
        val type = tree.find {
            when (it) {
                is ClassType -> it.options.name == name
                is UnionType -> it.options.name == name
                else -> false
            }
        } ?: return null

        val source = super.get(key) as Map<String, Any?>
        return (if (type is UnionType) resolveClass(tree, type, source) else type) as ClassType
    }

    private fun resolveClass(tree: List<IType>, root: IType, source: Map<String, Any?> = this): ClassType {
        if (root is ClassType) return root
        if (root is UnionType) {
            if (source[":type"] == null) {
                return tree.filterIsInstance<ClassType>()
                        .find { it.options.name == root.options.values[0] }!!
            }
            val type = when (source[":type"]) {
                is String -> source[":type"]
                else -> root.options.values[source[":type"] as Int]
            }
            return tree.filterIsInstance<ClassType>()
                    .find { it.options.name == type }!!
        }
        throw DynabuffersException("cannot resolve class from $root")
    }

}
