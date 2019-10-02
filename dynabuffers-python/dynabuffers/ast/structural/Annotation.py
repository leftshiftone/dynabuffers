from dynabuffers.ast.structural import Value


class AnnotationOptions:
    def __init__(self, name:str, values:[Value]):
        self.name = name
        self.values = values

class Annotation:

    def __init__(self, options: AnnotationOptions):
        self.options = options
