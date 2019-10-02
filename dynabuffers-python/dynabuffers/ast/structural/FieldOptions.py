class FieldOptionsOptions:
    def __init__(self, deprecated:bool):
        self.deprecated = deprecated

class FieldOptions:

    def __init__(self, options: FieldOptionsOptions):
        self.options = options

    def isDeprecated(self):
        return self.options.deprecated
