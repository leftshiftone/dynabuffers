package dynabuffers

import dynabuffers.api.IType
import dynabuffers.ast.NamespaceType
import dynabuffers.exception.DynabuffersException
import java.nio.ByteBuffer
import kotlin.experimental.and
import kotlin.streams.toList

class NamespaceResolver(private val tree: List<IType>) {

    /**
     * Gets a namespace from the given list or tries to infer ([inferDefaultNamespace]) the namespace if no names are passed.
     */
    fun getNamespace(names: List<String>?): NamespaceDescription {
        if (names == null) {
            return inferDefaultNamespace()
        }
        return getNamespace(names, getNamespacesFromRootLevel())
    }

    /**
     * Extracts the namespace description from the given map. Looks for a [SpecialKey.NAMESPACE] key which
     * should contain the stringified path of the namespace within the tree.
     */
    fun getNamespace(map: Map<String, Any?>): NamespaceDescription {
        return getNamespace((map[SpecialKey.NAMESPACE.key] as String?)?.split("."))
    }

    /**
     * Extracts the namespace description out of a serialized dynabuffers message wrapped as a [ByteBuffer]
     * WARNING: this method modifies the given ByteBuffer
     */
    fun getNamespace(serialized: ByteBuffer, namespaceDepth: Int): NamespaceDescription {
        if (namespaceDepth == 0) return AbsentNamespace

        val namespacePathBuffer = ByteArray(kotlin.math.ceil(namespaceDepth / 2.0).toInt())
        serialized.get(namespacePathBuffer)
        val path = namespacePathBuffer.toList()
            .flatMap { listOf((it.toInt() shr 4).toByte(), it and 0x0F) }
            .take(namespaceDepth)

        return getNamepacesFromPath(path)
    }

    fun getNamepacesFromPath(path: List<Byte>): NamespaceDescription {
        val nss = getNamespaceFromPath(path)
        val waypoints = nss.map { it.path.first() }
        return ConcreteNamespaceDescription(checkNotNull(nss.lastOrNull()).namespace, waypoints)
    }

    /**
     * Reassembles the namespaces from the given path - A path is the byte sequence of the namespaces position
     * within the IDL file.
     *
     * eg:
     *
     *  namespace a {  // path => [0]
     *     namespace b { .. } // path => [0, 0]
     *     namespace c { .. } // path => [0, 1]
     *  }
     *
     *  and so forth.
     */
    private fun getNamespaceFromPath(path: List<Byte>): List<ConcreteNamespaceDescription> {
        val iter = path.iterator()
        var nss = getNamespacesFromRootLevel()
                .map { it.namespace }

        var ns: NamespaceType?
        var p = emptyList<ConcreteNamespaceDescription>()

        while (iter.hasNext()) {
            val i = iter.next()
            ns = nss[i.toInt()]
            nss = ns.nestedNamespaces
            p = p.plus(ConcreteNamespaceDescription(ns, listOf(Waypoint(ns.options.name, i))))
        }
        return p
    }

    /**
     * Tries to infer the namespace by traversing through the existing namespaces extracing the first one during each step.
     * If more than one namespace is encounted during a step a [IllegalStateException] is thrown since it is not possible
     * to infer a 'default' namespace.
     */
    private fun inferDefaultNamespace(): NamespaceDescription {
        tailrec fun _resolve(namespaces: List<ConcreteNamespaceDescription>): NamespaceDescription {
            if (namespaces.isEmpty()) return AbsentNamespace
            if (namespaces.size > 1) throw DynabuffersException("Could not infer default namespace")
            val candidate = namespaces.first()
            if (candidate.namespace.nestedNamespaces.isEmpty()) return candidate
            val newCandidates = candidate.namespace.nestedNamespaces
                    .mapIndexed { index, namespaceType ->
                        ConcreteNamespaceDescription(namespaceType,
                                candidate.path.plus(Waypoint(namespaceType.options.name, index.toByte())))
                    }
            return _resolve(newCandidates)
        }

        return _resolve(getNamespacesFromRootLevel())
    }

    private fun getNamespacesFromRootLevel(): List<ConcreteNamespaceDescription> =
            tree.filterIsInstance<NamespaceType>()
                    .mapIndexed { idx, namespace -> ConcreteNamespaceDescription(namespace, listOf(Waypoint(namespace.options.name, idx.toByte()))) }

    private fun getNamespace(names: List<String>, namespaces: List<ConcreteNamespaceDescription>): ConcreteNamespaceDescription {
        val nsName = names.first()
        val ns = findNamespaceByName(nsName, namespaces)
        if (names.size == 1) return ns
        return getNamespace(names.stream().skip(1).toList(),
                ns.namespace.nestedNamespaces
                        .mapIndexed { idx, n -> ConcreteNamespaceDescription(n, ns.path.plus(Waypoint(n.options.name, idx.toByte()))) }
        )
    }

    private fun findNamespaceByName(name: String, namespaces: List<ConcreteNamespaceDescription>): ConcreteNamespaceDescription {
        return namespaces.find { it.namespace.options.name == name }
                ?: throw DynabuffersException("no namespace with name $name found")
    }
}

sealed class NamespaceDescription() {
    abstract fun joinedPath(): String
    abstract fun getWaypoints(): List<Waypoint>
}

class ConcreteNamespaceDescription(val namespace: NamespaceType, val path: List<Waypoint>) : NamespaceDescription() {
    override fun joinedPath(): String = path.joinToString(".") { it.name }
    override fun getWaypoints(): List<Waypoint> {
        return path
    }

}

data class Waypoint(val name: String, val position: Byte)
object AbsentNamespace : NamespaceDescription() {
    override fun joinedPath(): String = throw IllegalStateException("No namespace has no name")
    override fun getWaypoints(): List<Waypoint> {
        return emptyList()
    }
}
