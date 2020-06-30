package dynabuffers.api.map

class ImplicitDynabuffersMap(map: Map<String, Any?>) : HashMap<String, Any?>(map) {
    fun getResult() = get("result")
}