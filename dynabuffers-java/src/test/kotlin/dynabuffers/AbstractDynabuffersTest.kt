package dynabuffers

import org.junit.jupiter.api.Assertions

abstract class AbstractDynabuffersTest {

    protected fun assertMap(engine: DynabuffersEngine, map: Map<String, Any>) {
        val result = engine.deserialize(engine.serialize(map))
        assertMap(map, result)
    }

    protected fun assertMap(engine: DynabuffersEngine, input: Map<String, Any>, expectedMap: Map<String,Any>) {
        val result = engine.deserialize(engine.serialize(input))
        assertMap(expectedMap, result)
    }

    protected fun assertMap(map1: Map<*, *>, map2: Map<*, *>) {
        Assertions.assertEquals(map1.size, map2.size)
        map1.forEach { k, v ->
            Assertions.assertTrue(map2.containsKey(k))
            when (v) {
                is ByteArray -> Assertions.assertTrue(v.toList() == (map2.get(k) as ByteArray).toList())
                is Array<*> -> Assertions.assertArrayEquals(v, map2[k] as Array<*>)
                is Map<*, *> -> assertMap(v, map2[k] as Map<*, *>)
                else -> Assertions.assertTrue(v!! == map2[k]) {"$v != ${map2[k]}"}
            }
        }
    }

}
