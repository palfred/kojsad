package dk.rheasoft.kojsad.minimal

import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonArray
import com.eclipsesource.json.JsonObject
import com.eclipsesource.json.JsonValue
import dk.rheasoft.kojsad.JsonArrayAdapter
import dk.rheasoft.kojsad.JsonElementType
import dk.rheasoft.kojsad.JsonObjectAdapter

class MinimalObjectAdapter(val json: JsonObject = JsonObject()) : JsonObjectAdapter {
    override fun createArray(): JsonArrayAdapter = MinimalArrayAdapter()
    override fun createObject(): JsonObjectAdapter = MinimalObjectAdapter()

    override fun createArray(json: String): JsonArrayAdapter {
        val jsonObject: JsonArray = Json.parse(json).asArray()
        return MinimalArrayAdapter(jsonObject)
    }

    override fun createObject(json: String): JsonObjectAdapter {
        val jsonObject: JsonObject = Json.parse(json).asObject()
        return MinimalObjectAdapter(jsonObject)
    }

    override fun propertyNames(): Iterable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(property: String): JsonElementType {
        val elm = json.get(property)
        return when {
                elm.isObject -> JsonElementType.OBJECT
                elm.isArray -> JsonElementType.ARRAY
                elm.isNumber -> JsonElementType.NUMBER
                elm.isBoolean -> JsonElementType.BOOLEAN
                elm.isString -> JsonElementType.STRING
                else -> JsonElementType.NULL
        }
    }


    override fun getObject(property: String): JsonObjectAdapter = MinimalObjectAdapter(json.get(property) as JsonObject)

    override fun setObject(property: String, value: JsonObjectAdapter) {
        json.set(property, (value as MinimalObjectAdapter).json)
    }

    override fun getArray(property: String): JsonArrayAdapter = MinimalArrayAdapter(json.get(property) as JsonArray)

    override fun setArray(property: String, value: JsonArrayAdapter) {
        json.set(property, (value as MinimalArrayAdapter).json)
    }

    override fun getString(property: String): String = json.get(property).asString()

    override fun setString(property: String, value: String) {
        json.set(property, value)
    }

    override fun getNumber(property: String): Number {
        val strValue = (json.get(property) as JsonValue).toString()
        return strValue.toLongOrNull() ?: strValue.toDouble()
    }

    override fun setNumber(property: String, value: Number) {
        when (value) {
            is Long -> json.set(property, value)
            is Double -> json.set(property, value)
            is Int -> json.set(property, value)
            is Float -> json.set(property, value)
        }

    }

    override fun getBoolean(property: String): Boolean =  json.get(property).asBoolean()

    override fun setBoolean(property: String, value: Boolean) {
        json.set(property, value)
    }

    override fun toJsonString() = json.toString()

}