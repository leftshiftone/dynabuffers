from dynabuffers.api.ISerializable import ISerializable
from dynabuffers.ast.ClassType import ClassType
from dynabuffers.ast.EnumType import EnumType
from dynabuffers.ast.UnionType import UnionType
from dynabuffers.ast.annotation.GreaterEquals import GreaterEquals
from dynabuffers.ast.annotation.GreaterThan import GreaterThan
from dynabuffers.ast.annotation.LowerEquals import LowerEquals
from dynabuffers.ast.annotation.LowerThan import LowerThan
from dynabuffers.ast.annotation.MaxLength import MaxLength
from dynabuffers.ast.annotation.MinLength import MinLength
from dynabuffers.ast.annotation.NotBlank import NotBlank


class Registry(object):

    def __init__(self, tree: [ISerializable], listeners):
        self.tree = tree
        self.listeners = listeners

    def resolveAnnotation(self, name, args):
        if name == "GreaterThan":
            return GreaterThan(args)
        if name == "GreaterEquals":
            return GreaterEquals(args)
        if name == "LowerThan":
            return LowerThan(args)
        if name == "LowerEquals":
            return LowerEquals(args)
        if name == "MaxLength":
            return MaxLength(args)
        if name == "MinLength":
            return MinLength(args)
        if name == "NotBlank":
            return NotBlank(args)
        raise ValueError("unknown annotation " + str(name))

    def createForSchema(self, schema: [ISerializable]):
        return Registry(schema, self.listeners)

    def resolve(self, name: str):
        for element in self.tree:
            if isinstance(element, ClassType) and element.options.name == name:
                return element
            if isinstance(element, EnumType) and element.options.name == name:
                return element
            if isinstance(element, UnionType) and element.options.name == name:
                return element

    def addNotification(self, notification: str):
        for listener in self.listeners:
            listener(notification)
