import dk.rheasoft.kojsad.Adapter
import dk.rheasoft.kojsad.JsObjectAdapter
import dk.rheasoft.kojsad.sampleobjects.Address
import dk.rheasoft.kojsad.sampleobjects.Person
import dk.rheasoft.kojsad.sampleobjects.Tag
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class TestPersonJs() {
    @Test
    fun person_from_string() {
        val adapter =
            Adapter.createObject(
                """{
                |"firstName":"Peter Alfred",
                |"lastName":"Østergaard", 
                |"yearBorn" : 1965,
                |"address" :
                |   {
                |      "street" : "Langørvej",
                |      "number" : "103",
                |      "postalCode": 8381
                |   },
                | "tags" : [
                |   { "key" : "test", "value": "test-value" },
                |   { "key" : "test2", "value": "test-value2" }
                |   ]
                |}""".trimMargin()
            )
        val person = Person(adapter)

        assertEquals(person.firstName, "Peter Alfred")
        assertEquals(person.lastName, "Østergaard")
        assertEquals(person.yearBorn, 1965)
        assertEquals(person.address.number, "103")

        person.address.number = "103 st"
        println(person.tags)
        println(person.tags.map { "${it.key}: ${it.value}" })
        println(adapter.toJsonString())
        assertEquals(person.address.number, "103 st")

        person.lastName = "Jensen"
        person.address = Address().apply {
            street = "Skråningen"
            number = "19"
            postalCode = 8420
        }
        person.tags = person.tags + Tag().apply {
            key = "new key"
            value = "new value"
        }
        println(adapter.toJsonString())
        assertEquals(person.address.number, "19")
        assertNotEquals(person.lastName, "Østergaard")

    }

    @Test
    fun person() {
        val person = Person().apply {
            firstName = "Peter Alfred"
            lastName = "Østergaard"
            yearBorn = 1965
        }
        assertEquals(person.firstName, "Peter Alfred")
        assertEquals(person.lastName, "Østergaard")
        assertEquals(person.yearBorn, 1965)

        println(person.toJsonString())

        person.lastName = "Jensen"
        assertNotEquals(person.lastName, "Østergaard")

    }
}