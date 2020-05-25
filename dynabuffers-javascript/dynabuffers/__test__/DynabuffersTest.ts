var assert = require('assert');
import {EnumType, EnumTypeOptions} from "../ast/EnumType"

describe('Array', function() {
    describe('#indexOf()', function() {
        it('should return -1 when the value is not present', function() {
            const enumType = new EnumType(new EnumTypeOptions());

            assert.equal(enumType.size("abcd", null), 1);
        });
    });
});
