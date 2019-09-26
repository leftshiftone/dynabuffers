package dynabuffers.ast

import java.nio.ByteBuffer

abstract class AbstractAST {
    abstract fun size(value: Any, registry: IRegistry): Int
    abstract fun serialize(value: Any, buffer: ByteBuffer, registry: IRegistry)
    abstract fun deserialize(buffer: ByteBuffer, registry: IRegistry): Any

    interface IRegistry {
        fun resolve(name: String): AbstractAST
        fun addNotification(notification:String)
    }

    protected fun long(obj: Any) = obj.toString().toLong()
}
