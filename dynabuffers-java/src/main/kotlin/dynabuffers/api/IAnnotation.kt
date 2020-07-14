package dynabuffers.api

interface IAnnotation {
    fun validate(fieldName:String, obj: Any?)
}
