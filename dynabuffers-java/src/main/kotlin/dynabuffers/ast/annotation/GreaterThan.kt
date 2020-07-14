package dynabuffers.ast.annotation

import dynabuffers.api.IAnnotation
import dynabuffers.ast.structural.Value
import dynabuffers.exception.DynabuffersException

class GreaterThan(private val args:List<Value>) : IAnnotation {

    init {
        require(args.size == 1)
        require(args[0].isNumeric()) {"${args[0].options.value} is not a number"}
    }

    override fun validate(fieldName:String, obj:Any?) {
        if (obj.toString().toFloat() <= args[0].asFloat()) {
            throw DynabuffersException("($fieldName:'$obj') is lower equals ${args[0].asFloat()}")
        }
    }
}
