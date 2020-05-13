import struct
from abc import ABC, abstractmethod
from struct import unpack


class ISerializable(ABC):

    @abstractmethod
    def size(self, value, registry):
        pass

    @abstractmethod
    def serialize(self, value, buffer: 'ByteBuffer', registry):
        pass

    @abstractmethod
    def deserialize(self, buffer: 'ByteBuffer', registry):
        pass

    def byte(self, obj):
        return bytes(obj)[0]


class ByteBuffer():

    def __init__(self, length:int, buffer:bytearray = bytearray()):
        self.length = length
        self.buffer = buffer

    @staticmethod
    def wrap(buffer: bytearray) -> 'ByteBuffer':
        return ByteBuffer(len(buffer), buffer)

    def put(self, obj: bytes):
        self.buffer = self.buffer + obj

    def putShort(self, obj:int):
        self.buffer = self.buffer + struct.pack('>h', obj)

    def putInt(self, obj:int):
        self.buffer = self.buffer + struct.pack('>i', obj)

    def putFloat(self, obj:float):
        self.buffer = self.buffer + struct.pack('>f', obj)

    def putLong(self, obj:int):
        self.buffer = self.buffer + struct.pack('>q', obj)

    def has_remaining(self):
        return len(self.buffer) > 0

    def get(self, length=None):
        if length is None:
            return self.buffer.pop(0)
        else:
            bytes = self.buffer[:length]
            self.buffer = self.buffer[length:]
            return bytes

    def getShort(self):
        buffer = self.buffer[:2]
        self.buffer = self.buffer[2:]
        # https://stackoverflow.com/questions/45187101/converting-bytearray-to-short-int-in-python
        return unpack('>h', buffer)[0]

    def getInt(self):
        buffer = self.buffer[:4]
        self.buffer = self.buffer[4:]
        return unpack('>i', buffer)[0]

    def getFloat(self):
        bytes = self.buffer[:4]
        self.buffer = self.buffer[4:]
        return struct.unpack('>f', bytes)[0]

    def getLong(self):
        bytes = self.buffer[:8]
        self.buffer = self.buffer[8:]
        return struct.unpack('>q', bytes)[0]

    def toBytes(self):
        return self.buffer
