import dk.rheasoft.kojsad.Adapter
import dk.rheasoft.kojsad.JsonObjectAdapter
import dk.rheasoft.kojsad.ObjectAdapter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StixLikeTest {
    open class StixObject(adapter: JsonObjectAdapter = Adapter.createObject()) : ObjectAdapter(adapter) {
        val id : String by stringDelegate()
        val type : String by stringDelegate()
    }

    open class Bundle(adapter: JsonObjectAdapter = Adapter.createObject()) : StixObject(adapter) {
        var objects : List<StixObject> by listDelegate {
            when (it.getString("type")) {
                "Vulnerabilty" -> Vulnerabilty(it)
                "Sighting" -> Sighting(it)
                else -> StixObject(it)
             }
        }
    }

    open class Vulnerabilty(adapter: JsonObjectAdapter = Vulnerabilty.new()) : StixObject(adapter) {
        var description by stringDelegate()

        companion object {
            fun new () : JsonObjectAdapter = Adapter.createObject().apply {
                setString("id", "vulnerabilty-some-new-id")
                setString("type", "Vulnerabilty")
            }
        }
    }

    open class Sighting(adapter: JsonObjectAdapter = Sighting.new()) : StixObject(adapter) {
        var where by stringDelegate()

        companion object {
            fun new () : JsonObjectAdapter = Adapter.createObject().apply {
                setString("id", "sighting-some-new-id")
                setString("type", "Sighting")
            }
        }
    }

    @Test
    fun bundle_1() {
        val bundle = Bundle().apply {
            objects = listOf(
                Vulnerabilty().apply {
                    description = "This is a vulnerability description"
                },
                Sighting().apply {
                    where = "This is where it happend"
                }
            )
        }
        print(bundle)
        val objects = bundle.objects
        assertEquals(2, objects.size)
        assertTrue(objects[0] is Vulnerabilty)
        assertTrue(objects[1] is Sighting)
    }

    @Test
    fun bundle_from_string() {
        val bundle = Bundle(Adapter.createObject("""
            {
              "type" : "Bundle",
              "id" : "Bundle-aasdasd-asdasdas",
              "objects": [
                {
                  "id": "vulnerabilty-some-new-id",
                  "type": "Vulnerabilty",
                  "description": "This is a vulnerability description"
                },
                {
                  "id": "sighting-some-new-id",
                  "type": "Sighting",
                  "where": "This is where it happend"
                }
              ]
            }
        """.trimIndent()))
        print(bundle)
        val objects = bundle.objects
        assertEquals(2, objects.size)
        assertTrue(objects[0] is Vulnerabilty)
        assertTrue(objects[1] is Sighting)
    }
}