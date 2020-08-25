from dynabuffers.api.ISerializable import ISerializable


class NamespaceTypeOptions:
    def __init__(self, name, list: [ISerializable]):
        self.name = name
        self.list = list


class NamespaceType:

    def __init__(self, options: NamespaceTypeOptions, nestedNamespaces: ['NamespaceType']):
        self.options = options
        self.nestedNamespaces = nestedNamespaces
