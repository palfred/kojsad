package dk.rheasoft.kojsad.sampleobjects

import dk.rheasoft.kojsad.*

class Person(jsonAdapter: JsonObjectAdapter = Adapter.createObject()) : ObjectAdapter(jsonAdapter) {
    var firstName by JsonString()
    var lastName by JsonString()
    var yearBorn  by JsonInt()
    var address: Address by JsonObject(::Address)
    var tags: List<Tag>  by JsonList(::Tag)
}

class Address(jsonAdapter: JsonObjectAdapter = Adapter.createObject()) : ObjectAdapter(jsonAdapter) {
    var street: String by  JsonString()
    var number: String by  JsonString()
    var postalCode: Int by JsonInt()
}

class Tag(jsonAdapter: JsonObjectAdapter = Adapter.createObject()) : ObjectAdapter(jsonAdapter) {
    var key by JsonString()
    var value by  JsonString()
}
