package dynabuffers

import dynabuffers.api.IAnnotation
import dynabuffers.api.IRegistry
import dynabuffers.api.ISerializable
import dynabuffers.api.IType
import dynabuffers.ast.ClassType
import dynabuffers.ast.EnumType
import dynabuffers.ast.UnionType
import dynabuffers.ast.annotation.*
import dynabuffers.ast.structural.Annotation
import dynabuffers.exception.DynabuffersException

class Registry(
    private val tree: List<IType>,
    private val listeners: ArrayList<(String) -> Unit>
) : IRegistry {

    override fun resolveAnnotation(annotation: Annotation): IAnnotation {
        when (annotation.options.name) {
            "NotBlank" -> return NotBlank(annotation.options.values)
            "MinLength" -> return MinLength(annotation.options.values)
            "MaxLength" -> return MaxLength(annotation.options.values)
            "GreaterThan" -> return GreaterThan(annotation.options.values)
            "GreaterEquals" -> return GreaterEquals(annotation.options.values)
            "LowerThan" -> return LowerThan(annotation.options.values)
            "LowerEquals" -> return LowerEquals(annotation.options.values)
            else -> throw DynabuffersException("unknown annotation ${annotation.options.name}")
        }
    }

    override fun addNotification(notification: String) {
        listeners.forEach { it(notification) }
    }

    override fun createForSchema(schema: List<IType>): IRegistry {
        return Registry(schema, listeners)
    }

    override fun resolve(name: String): ISerializable {
        return tree.filterIsInstance<ISerializable>().find {
            if (it is ClassType && it.options.name == name) return@find true
            if (it is EnumType && it.options.name == name) return@find true
            if (it is UnionType && it.options.name == name) return@find true

            return@find false
        } ?: throw DynabuffersException("invalid reference $name")
    }
}