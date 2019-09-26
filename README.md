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
