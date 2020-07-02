package dynabuffers.ast

import dynabuffers.api.ISerializable
import dynabuffers.api.IType

data class NamespaceType(val options: NamespaceTypeOptions) : IType {

    data class NamespaceTypeOptions(val name: String, val list: List<ISerializable>)

}
