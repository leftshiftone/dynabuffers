package dynabuffers.ast.structural

import dynabuffers.api.IType

class Annotation(val options: AnnotationTypeOptions) : IType {

    data class AnnotationTypeOptions(val name: String, val values: List<Value>)

}
