class ClassOptionsOptions:
    def __init__(self, primary:bool, deprecated:bool):
        self.primary = primary
        self.deprecated = deprecated


class ClassOptions:

    def __init__(self, options: ClassOptionsOptions):
        self.options = options

    def isPrimary(self):
        return self.options.primary

    def isDeprecated(self):
        return self.options.deprecated
