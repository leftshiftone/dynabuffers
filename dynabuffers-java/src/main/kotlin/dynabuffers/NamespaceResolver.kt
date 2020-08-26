package dynabuffers

import dynabuffers.api.IType
import dynabuffers.ast.NamespaceType
import dynabuffers.exception.DynabuffersException
import kotlin.streams.toList

class NamespaceResolver(private val tree: List<IType>) {
    fun getNamespace(names: List<String>?): NamespaceType? {
        if (names == null) {
            return getDefaultNamespace()
        }
        return getNamespace(names, getNamespacesFromRootLevel())
    }

    private fun getDefaultNamespace(): NamespaceType? {
        var namespaces = getNamespacesFromRootLevel()
        if (namespaces.size != 1) return null
        while (namespaces[0].nestedNamespaces.size == 1) {
            namespaces = namespaces[0].nestedNamespaces
        }
        return namespaces[0]
    }

    private fun getNamespacesFromRootLevel() = tree.filterIsInstance<NamespaceType>()

    private fun getNamespace(names: List<String>, namespaces: List<NamespaceType>): NamespaceType {
        val nsName = names.first()
        val ns = findNamespaceByName(nsName, namespaces)
                ?: throw DynabuffersException("no namespace with name $nsName found")
        if (names.size == 1) return ns
        return getNamespace(names.stream().skip(1).toList(), ns.nestedNamespaces)
    }

    private fun findNamespaceByName(name: String, namespaces: List<NamespaceType>?): NamespaceType? {
        return namespaces?.find { it.options.name == name }
    }
}