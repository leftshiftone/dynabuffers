[![CircleCI branch](https://img.shields.io/circleci/project/github/leftshiftone/dynabuffers/master.svg?style=flat-square)](https://circleci.com/gh/leftshiftone/canon)
[![GitHub tag (latest SemVer)](https://img.shields.io/github/tag/leftshiftone/dynabuffers.svg?style=flat-square)](https://github.com/leftshiftone/canon/tags)

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
* arrays
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

Classes and fields can be deprecated and/or optional.
````
class Color(deprecated) { 
   name:string = red
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

## Release

A release must be triggered locally by running `./gradlew final -x bintrayUpload -Prelease.scope=(minor|major)`.
This will recrate the necessary tag and circleci will take care of the rest.
