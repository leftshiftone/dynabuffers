import struct
from abc import ABC, abstractmethod


class ISerializable(ABC):

    @abstractmethod
    def size(self, value, registry):
        pass

    @abstractmethod
    def serialize(self, value, buffer, registry):
        pass

    @abstractmethod
    def deserialize(self, buffer, registry):
        pass

    def byte(self, obj):
        return bytes(obj)[0]


class ByteBuffer():

    def __init__(self, length:int, buffer:bytearray = bytearray()):
        self.length = length
        self.buffer = buffer

    def put(self, obj: bytes):
        self.buffer = self.buffer + obj

    def putShort(self, obj:int):
        self.buffer = self.buffer + obj.to_bytes(2, byteorder="big")

    def putInt(self, obj:int):
        self.buffer = self.buffer + obj.to_bytes(4, byteorder="big")

    def putFloat(self, obj:float):
        self.buffer = self.buffer + struct.pack('>f', obj)

    def putLong(self, obj:int):
        self.buffer = self.buffer + obj.to_bytes(8, byteorder="big")

    def get(self, length=None):
        if length is None:
            return self.buffer.pop(0)
        else:
            bytes = self.buffer[:length]
            self.buffer = self.buffer[length:]
            return bytes

    def getShort(self):
        bytes = self.buffer[:2]
        self.buffer = self.buffer[2:]
        return int.from_bytes(bytes, byteorder='big')

    def getInt(self):
        bytes = self.buffer[:4]
        self.buffer = self.buffer[4:]
        return int.from_bytes(bytes, byteorder='big')

    def getFloat(self):
        bytes = self.buffer[:4]
        self.buffer = self.buffer[4:]
        return struct.unpack('>f', bytes)[0]

    def getLong(self):
        bytes = self.buffer[:8]
        self.buffer = self.buffer[8:]
        return int.from_bytes(bytes, byteorder='big')

    def toBytes(self):
        return self.buffer
