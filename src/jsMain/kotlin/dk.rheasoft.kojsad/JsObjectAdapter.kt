package dk.rheasoft.kojsad


import dk.rheasoft.kojsad.JsonArrayAdapter
import dk.rheasoft.kojsad.JsonElementType
import dk.rheasoft.kojsad.JsonObjectAdapter
import kotlin.js.Json

class JsObjectAdapter(val json: Json = JSON.parse("{}")) : JsonObjectAdapter {
    override fun propertyNames(): Iterable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(property: String): JsonElementType {
        val elm: Any? = json.get(property)
        return when {
            elm == null ->  JsonElementType.NULL
            elm is String -> JsonElementType.STRING
            elm is Number -> JsonElementType.NUMBER
            elm is Boolean -> JsonElementType.BOOLEAN
            else -> JsonElementType.OBJECT
        }
    }


    override fun getObject(property: String): JsonObjectAdapter = JsObjectAdapter(json[property] as Json)
    override fun setObject(property: String, value: JsonObjectAdapter) {
        json[property] = (value as JsObjectAdapter).json
    }

    override fun getArray(property: String): JsonArrayAdapter = JsArrayAdapter(json[property] as Array<Any?>)
    override fun setArray(property: String, value: JsonArrayAdapter) {
        json[property] = (value as JsArrayAdapter).json
    }

    override fun getString(property: String): String = json[property] as String

    override fun setString(property: String, value: String) {
        json.set(property, value)
    }

    override fun getNumber(property: String): Number =  json[property] as Number

    override fun setNumber(property: String, value: Number) {
        json[property] = value
    }

    override fun getBoolean(property: String): Boolean =  json[property] as Boolean

    override fun setBoolean(property: String, value: Boolean) {
        json[property] = value
    }

    override fun createArray(): JsonArrayAdapter = JsArrayAdapter()
    override fun createArray(json: String): JsonArrayAdapter {
        val array: Array<Any?> = JSON.parse(json)
        return JsArrayAdapter(array)
    }

    override fun createObject(): JsonObjectAdapter = JsObjectAdapter()
    override fun createObject(json: String): JsonObjectAdapter {
        val jsonObject: Json = JSON.parse(json) as Json
        return JsObjectAdapter(jsonObject)
    }

    override fun toJsonString() = JSON.stringify(json)

    companion object {

        fun fromString(jsonStr : String): JsObjectAdapter {
            val jsonObject: Json = JSON.parse(jsonStr) as Json
            return JsObjectAdapter(jsonObject)
        }
    }
}