from typing import List

from dynabuffers.api.ISerializable import ISerializable
from dynabuffers.api.map.DynabuffersMap import DynabuffersMap


class ImplicitDynabuffersMap(DynabuffersMap):

    def __init__(self, map: dict, tree: List[ISerializable], root: ISerializable):
        super().__init__(map, tree, root)

    def get_value(self):
        return self.get("value")