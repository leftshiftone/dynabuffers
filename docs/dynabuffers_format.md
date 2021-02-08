# Dynabuffers serialization format

The following document describes the serialization format of the dynabuffers library.

## Header

```

             |---           Version          ---| 
1 Byte:  [   0    0    0    0    0    0    0    0   ]


             |-- ND  --|    |---    Flags    ---|
2 Byte:  [   0    0    0    0    0    0    0    0   ]

             |---  NPI   ---|    |---  NPI   ---|
3 Byte:  [   0    0    0    0    0    0    0    0   ]

...

6 Byte:  [   0    0    0    0    0    0    0    0   ]

```

---

**Version**

Type: __8bit (1 byte)__

Values: __0-255__

Description:

Holds the DynaBuffers major version as a single byte value.


---

**Namespace Depth (ND)**

Type: __3bit__

Values: __0-7__

Description:

Denotes how many namespace path indicators are present.

Example:
Namespace D should be selected. This requires a Namespace Depth of 3.

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

**Flags**

Type: __5bit__

Values: __0/1 (Bit set or unset)__

Description:

Represents the possible set flags.

|Bit | 4                | 3               | 2        | 1        | 0        |
|----| ---------------- | --------------- | -------- | -------- | -------- |
|Flag| Compression Flag | Encryption Flag | Reserved | Reserved | Reserved |

---

**Namespace Path Indicator (NPI)**

Type: __4bit__

Values: __0-15__

Description:

The encoded selected position of a namespace. See: **Namespace Information Length**
