from typing import Dict, Any

from dynabuffers.api.ISerializable import ISerializable, ByteBuffer
from dynabuffers.ast.datatype.ArrayType import ArrayType, ArrayTypeOptions
from dynabuffers.ast.datatype.BooleanType import BooleanType
from dynabuffers.ast.datatype.ByteType import ByteType
from dynabuffers.ast.datatype.FloatType import FloatType
from dynabuffers.ast.datatype.IntType import IntType
from dynabuffers.ast.datatype.LongType import LongType
from dynabuffers.ast.datatype.ShortType import ShortType
from dynabuffers.ast.datatype.StringType import StringType, StringTypeOptions


class MapTypeOptions:
    def __init__(self, charset: str):
        self.charset = charset


class MapType(ISerializable):

    def __init__(self, options: MapTypeOptions):
        self.options = options

    def size(self, value, registry):
        size = 2
        for key in value:
            if key is None or value[key] is None:
                continue

            key_type = self._get_key_type(key)
            val_type = self._get_val_type(value[key])

            size += 4
            size += key_type.size(key, registry)
            size += val_type.size(value[key], registry)

        return size

    def serialize(self, value, buffer: ByteBuffer, registry):
        buffer.putShort(len(value))
        for key in value:
            if key is None or value[key] is None:
                continue

            key_type = self._get_key_type(key)
            val_type = self._get_val_type(value[key])

            key_size = key_type.size(key, registry)
            val_size = val_type.size(value[key], registry)

            ordinal = self._type_to_ordinal(value[key])
            buffer.putInt(self.merge_header(ordinal, key_size + val_size))
            key_type.serialize(key, buffer, registry)
            val_type.serialize(value[key], buffer, registry)

    def deserialize(self, buffer: ByteBuffer, registry):
        result:Dict[str, Any] = dict()

        counter = 0
        amount = buffer.getShort()
        while counter < amount:
            header = buffer.getInt()
            (type, length) = self.unmerge_header(header)

            key_type = StringType(StringTypeOptions(self.options.charset))
            val_type = self._ordinal_to_type(type)

            raw_value = buffer.get(length)
            entry = ByteBuffer.wrap(raw_value)

            k = key_type.deserialize(entry, registry)
            v = val_type.deserialize(entry, registry)

            result[k] = v
            counter += 1

        return result

    def _get_val_type(self, obj) -> ISerializable:
        if isinstance(obj, str):
            return StringType(StringTypeOptions(self.options.charset))
        if isinstance(obj, dict):
            return self
        if isinstance(obj, bool):
            return BooleanType()
        if isinstance(obj, float):
            return FloatType()
        if isinstance(obj, int):
            return IntType()
        if isinstance(obj, bytes):
            return ByteType()
        if isinstance(obj, list):
            return ArrayType(ArrayTypeOptions(self._get_val_type(obj[0])))
        raise NameError("cannot handle value " + str(obj))

    def _get_key_type(self, obj) -> ISerializable:
        if isinstance(obj, str):
            return StringType(StringTypeOptions(self.options.charset))
        raise NameError("cannot handle value " + str(obj))

    def _type_to_ordinal(self, obj):
        if isinstance(obj, str):
            return 0
        if isinstance(obj, bool):
            return 10
        if isinstance(obj, bytes) and len(obj) == 1:
            return 20
        if isinstance(obj, float):
            return 30
        if isinstance(obj, int):
            return 40
        if isinstance(obj, bytes) and len(obj) == 8:
            return 50
        if isinstance(obj, bytes) and len(obj) == 2:
            return 60
        if isinstance(obj, dict):
            return 70
        if isinstance(obj, list):
            return 80 + self._type_to_ordinal(obj[0])

        raise NameError("cannot handle value " + str(obj))

    def _ordinal_to_type(self, obj):
        if obj == 0:
            return StringType(StringTypeOptions(self.options.charset))
        if obj == 10:
            return BooleanType()
        if obj == 20:
            return ByteType()
        if obj == 30:
            return FloatType()
        if obj == 40:
            return IntType()
        if obj == 50:
            return LongType()
        if obj == 60:
            return ShortType()
        if obj == 70:
            return self
        if obj == 80:
            return ArrayType(ArrayTypeOptions(StringType(StringTypeOptions(self.options.charset))))
        if obj == 81:
            return ArrayType(ArrayTypeOptions(BooleanType()))
        if obj == 82:
            return ArrayType(ArrayTypeOptions(ByteType()))
        if obj == 83:
            return ArrayType(ArrayTypeOptions(FloatType()))
        if obj == 84:
            return ArrayType(ArrayTypeOptions(IntType()))
        if obj == 85:
            return ArrayType(ArrayTypeOptions(LongType()))
        if obj == 86:
            return ArrayType(ArrayTypeOptions(ShortType()))
        if obj == 87:
            return ArrayType(ArrayTypeOptions(self))

        raise NameError("cannot handle value " + str(obj))

    def merge_header(self, a:int, b:int) -> int:
        right = 0xFFFFFF
        return a << 24 | (b & right)

    def unmerge_header(self, c:int) -> (int, int):
        right = 0xFFFFFF
        return c >> 24, c & right
