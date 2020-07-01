package dynabuffers.api.map

import dynabuffers.api.ISerializable

class ImplicitDynabuffersMap(map: Map<String, Any?>,
                             tree: List<ISerializable>,
                             root: ISerializable) : DynabuffersMap(map, tree, root) {
    fun getValue() = get("value")
}