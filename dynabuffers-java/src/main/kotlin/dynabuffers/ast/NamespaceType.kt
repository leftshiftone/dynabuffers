package dynabuffers.ast

import dynabuffers.api.ISerializable
import dynabuffers.api.IType

data class NamespaceType(val options: NamespaceTypeOptions, val nestedNamespaces : List<NamespaceType> = listOf()) : IType {

    data class NamespaceTypeOptions(val name: String, val list: List<ISerializable>)

}
