package dynabuffers.api

import dynabuffers.ast.structural.Annotation


interface IRegistry {
    fun resolve(name: String): ISerializable
    fun resolveAnnotation(annotation: Annotation):IAnnotation
    fun addNotification(notification: String)
}
