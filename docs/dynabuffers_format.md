# Dynabuffers serialization format

The following document describes the serialization format of the dynabuffers library.

## Header

```

             |---           Version          ---| 
1 Byte:  [   0    0    0    0    0    0    0    0   ]


             |--- Flags  ---|    |---   NIL  ---|
2 Byte:  [   0    0    0    0    0    0    0    0   ]

             |--- Namespace Path Indicator   ---|
3 Byte:  [   0    0    0    0    0    0    0    0   ]

...

16 Byte:  [   0    0    0    0    0    0    0    0   ]

```

---

**Version**

Type: __8bit (1 byte)__

Values: __0-255__

Description:

Holds the dynabuffers major version as a single byte value. 



---

**Flags**

Type: __4bit__

Values: __0/1 (Bit set or unset)__

Description:

Represents the possible set flags.

|Bit | 3                | 2               | 1        | 0        |
|----| ---------------- | --------------- | -------- | -------- |
|Flag| Compression Flag | Encryption Flag | Reserved | Reserved |



---

**Namespace Information Length (NIL)**

Type: __4bit__

Values: __0-16__

Description:

Denotes the how many bytes containing namespace path indicators follow.

Example:

Namespace D should be selected. This requires a NIL of 3.


```
namespace A {              # 0
    namespace B {}
    namespace C {          # 1
        namespace D {}     # 0
    }
}
```

The namespace path would be: `[0,1,0]` to select namespace D.


---

**Namespace Path Indicator**

Type: __8bit__

Values: __0-255__

Description:

The encoded selected position of a namespace. See: **Namespace Information Length**
