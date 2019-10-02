from abc import ABC, abstractmethod


class IAnnotation(ABC):

    @abstractmethod
    def validate(self, fieldName, value):
        pass
