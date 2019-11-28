package dk.rheasoft.kojsad.sampleobjects

import dk.rheasoft.kojsad.Adapter
import dk.rheasoft.kojsad.JsonObjectAdapter
import dk.rheasoft.kojsad.ObjectAdapter

class Person(jsonAdapter: JsonObjectAdapter = Adapter.createObject()) : ObjectAdapter(jsonAdapter) {
    var firstName: String by stringDelegate()
    var lastName: String by stringDelegate()
    var yearBorn: Int by intDelegate()
    var address: Address by objectDelegate(::Address)
    var tags: List<Tag>  by listDelegate(::Tag)
}

class Address(jsonAdapter: JsonObjectAdapter = Adapter.createObject()) : ObjectAdapter(jsonAdapter) {
    var street: String by stringDelegate()
    var number: String by stringDelegate()
    var postalCode: Int by intDelegate()
}

class Tag(jsonAdapter: JsonObjectAdapter = Adapter.createObject()) : ObjectAdapter(jsonAdapter) {
    var key: String by stringDelegate()
    var value: String by stringDelegate()
}
