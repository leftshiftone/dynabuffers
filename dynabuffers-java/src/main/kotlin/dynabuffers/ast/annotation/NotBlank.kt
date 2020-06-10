package dynabuffers.ast.annotation

import dynabuffers.api.IAnnotation
import dynabuffers.ast.structural.Value
import dynabuffers.exception.DynabuffersException

class NotBlank(val args:List<Value>) : IAnnotation {

    init {
        require(args.isEmpty())
    }

    override fun validate(fieldName:String, obj:Any?) {
        if (obj.toString().isBlank()) {
            throw DynabuffersException("($fieldName:'$obj') value is blank")
        }
    }
}
