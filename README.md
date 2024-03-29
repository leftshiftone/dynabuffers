[![CircleCI branch](https://img.shields.io/circleci/project/github/leftshiftone/dynabuffers/master.svg?style=flat-square)](https://circleci.com/gh/leftshiftone/dynabuffers)
[![GitHub tag (latest SemVer)](https://img.shields.io/github/tag/leftshiftone/dynabuffers.svg?style=flat-square)](https://github.com/leftshiftone/dynabuffers/tags)


![Maven Central](https://img.shields.io/maven-central/v/one.leftshift.dynabuffers/dynabuffers?style=flat-square)
[![PyPI](https://img.shields.io/pypi/v/dynabuffers?style=flat-square)](https://pypi.org/project/dynabuffers/)
# DynaBuffers

DynaBuffers is a cross platform serialization library architected for dynamic serialization while taking maximum memory efficiency into account. 
It allows you to directly serialize from map and deserializing into map structures while still supporting forwards/backwards compatibility.

## Supported programming languages
* Java
* Python
* Javascript (in progress)
* Typescript (in progress)

## Why use DynaBuffers

* *Ease of integration* - DynaBuffers don't use auto generated classes but instead creates the serializer/deserializer logic on the fly. 
* *Memory efficiency and speed* - Only the information described in the schema definition gets stored in the byte buffer. No additional data is allocated.
* *Schema evolution* - DynaBuffers supports schema evolution by defining class attributes as deprecated and/or optional.
* *No code footprint* - No generated code is needed.

## Setup
In order to generate the ANTLR sources invoke the gradle tasks generateGrammarSource and generateGrammarSourcePython.

## Schema definition

DynaBuffers supports the top level entities *class*, *enum* and *union*.

### Class

Classes are the date transfer objects whose data are described by fields. Fields must have a name and a datatype and
can have options. DynaBuffers supports the following datatypes:
* string
* byte
* short
* int
* long
* float
* boolean
* array
* option
* map
* enums
* unions
* other classes 

````
class Color { name:string }

class Image {
   type:string
   size:short
   data:[byte]
}

class Product(primary) {
   name:string
   price:float
   image:Image
   color:Color
}
````

Classes and fields can be deprecated and optional. Optional fields are defined by defining a default value.
````
class Color(deprecated) { 
   name:string = "red"
   rgb:string(deprecated) 
}
````

When multiple classes are defined DynaBuffers need to know which class is the primary class.
````
class A(primary) {
   content:string
   classB:B
   classC:C
}
class B {
   content:string
}
class C {
   content:string
}
````

Dynabuffers also supports scalar value serialization by using an implicit class.
An Implicit class adds the map wrapper automatically around the scalar value.

````
class Data(implicit) {
   value:[byte]
}

engine.serialize("text".getBytes())
````

As default, the deserialization method returns a DynabuffersMap instance which has 
access to the schema and validates each access on the map.

````
class Data { 
   strVal:string 
   intVal:int
}

val map = engine.deserialize(engine.serialize({"strVal":"text", "intVal":0))

map.get("strVal") // returns "text"
map.get("intVal") // returns 0

map.getString("strVal") // returns "text"
map.getInt("intVal") // returns 0

map.get("unknown") // throws an exception
map.getInt("strVal") // throws an exception
````

In the case of an implicit class deserialization, the return value of the deserialize funciton
is an ImplicitDynabuffersMap instance. This implementation extends DynabuffersMap and has
an additional function getValue(), which returns the scalar value.

### Enum

Enums are enumerations of static values.

````
enum Color { RED GREEN BLUE }

class Product {
   name:string
   color:Color
}
````

### Union

Unions can be used to group multiple classes.

> **WARNING**: The order of classes in a union should not be changed. New classes should always be appended at the end!

````
class MessageA { type:string }
class MessageB { type:string }
class MessageC { type:string }

union Message { 
   MessageA
   MessageB
   MessageC
}

class Request {
   type:Message
}
````

By using the field *:type* as a type hint it is possible to define which union class type to use.
The *:type* field refers to the union class position e.g. 0 for the first element and so on.
Alternatively, the class name can also be used as an alias for the *:type* field. 

````
class MessageA { content:string }
class MessageB { content:string }

union Message { 
   MessageA
   MessageB
}

engine.serialize({"content":"text", ":type":0)
engine.serialize({"content":"text", ":type":"MessageA")
````

Union types can also be declared as primary or implicit.

### Namespace
Namespaces can be used to group class/union/enum types to logical groups.

````
namespace request {
   class Data { content: string }
}
namespace response {
   class Data { content: string }
}

val bytes = engine.serialize(mapOf("content" to "text"),"request")
//or
val bytes = engine.serialize(mapOf("content" to "text", ":namespace" to "request"))

engine.deserialize(bytes) // => mapOf("content" to "text", ":namespace" to "request")
````

Please note that namespaces can be nested in other namespaces

````
namespace abc{
    class DataLevel0 {
                value0: string
            }
    namespace def {
        class DataLevel1 {
                value1: int
            }
        namespace ghi {
            class DataLevel2 {
                value2: float
            }
        }
    }
}

#Serializing DataLevel2
engine.serialize(
    mapOf("value2" to 0.2f),
    listOf("abc", "def", "ghi")
)

#Serializing DataLevel1
engine.serialize(
    mapOf("value1" to 3),
    listOf("abc", "def")
)

#Serializing DataLevel0
engine.serialize(
    mapOf("value0" to "someString"),
    listOf("abc")
)
````

## Validation
By using annotations it is possible to declare validation rules for class fields.
Dynabuffers has the following built-in annotations:

| name          | description                                                     | attributes | datatype    |
|---------------|-----------------------------------------------------------------|------------|-------------|        
| GreaterThan   | Target value must be greater than the configured value          | size:int   | int & float |
| LowerThan     | Target value must be lower than the configured value            | size:int   | int & float |
| GreaterEquals | Target value must be greater equals the configured value        | size:int   | int & float |
| LowerEquals   | Target value must be lower equals the configured value          | size:int   | int & float |
| MaxLength     | Target value length must be lower equals the configured value   | size:int   | string      |
| MinLength     | Target value length must be greater equals the configured value | size:int   | string      |
| NotBlank      | Target must not be blank                                        | none       | string      |

````
class Product {
   @NotBlank
   @MinLength(3)
   @MaxLength(10)
   name:string
   @GreaterThan(0)
   price:float
}  
````

## Usage Kotlin
````
val engine = Dynabuffers.parse("class Color { name:string }")
val bytes = engine.serialize(mapOf("name" to "red"))
val map = engine.deserialize(bytes)
````

## Usage Python
````
engine = Dynabuffers.parse("class Color { name:string }")
bytes = engine.serialize({"name" : "red"})
map = engine.deserialize(bytes)
````

## Development

### Release
Releases are triggered locally. Just a tag will be pushed and CI takes care of the rest.

#### Major
Run `./gradlew final -x sendReleaseEmail -Prelease.scope=major` locally.

#### Minor
Run `./gradlew final -x sendReleaseEmail -Prelease.scope=minor` locally.

#### Patch
Must be released from branch (e.g. `release/1.0.x`)

Run `./gradlew final -x sendReleaseEmail -Prelease.scope=patch` locally.
