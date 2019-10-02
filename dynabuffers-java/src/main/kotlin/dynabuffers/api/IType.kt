package dynabuffers.api

interface IType {
    fun long(obj: Any) = obj.toString().toLong()
}
