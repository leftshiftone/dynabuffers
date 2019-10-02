from dynabuffers.api.IAnnotation import IAnnotation
from dynabuffers.ast.structural import Value


class NotBlank(IAnnotation):

    def __init__(self, args:[Value]):
        self.args = args
        if len(args) != 0:
            raise ValueError("too much annotation values")

    def validate(self, fieldName, value):
        if len(str(value).strip()) == 0:
            raise ValueError("(" + str(fieldName) + ":" + str(value) + ") is blank")
