package dynabuffers.api.map

import dynabuffers.api.IType

class ImplicitDynabuffersMap(map: Map<String, Any?>,
                             tree: List<IType>,
                             root: IType) : DynabuffersMap(map, tree, root) {
    fun getValue() = get("value")
}