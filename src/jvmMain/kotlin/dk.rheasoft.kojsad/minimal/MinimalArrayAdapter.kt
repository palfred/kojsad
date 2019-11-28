package dk.rheasoft.kojsad.minimal

import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonArray
import com.eclipsesource.json.JsonObject
import com.eclipsesource.json.JsonValue
import dk.rheasoft.kojsad.JsonArrayAdapter
import dk.rheasoft.kojsad.JsonElementType
import dk.rheasoft.kojsad.JsonObjectAdapter

class MinimalArrayAdapter(val json: JsonArray = JsonArray()) : JsonArrayAdapter {
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

    override fun length(): Int = json.size()

    override fun getType(index: Int): JsonElementType {
        val elm = json.get(index)
        return when {
            elm.isObject -> JsonElementType.OBJECT
            elm.isArray -> JsonElementType.ARRAY
            elm.isNumber -> JsonElementType.NUMBER
            elm.isBoolean -> JsonElementType.BOOLEAN
            elm.isString-> JsonElementType.STRING
            else -> JsonElementType.NULL
        }
    }

    override fun remove(index: Int) {
        json.remove(index)
    }

    override fun getObject(index: Int): JsonObjectAdapter = MinimalObjectAdapter(json.get(index).asObject())

    override fun setObject(index: Int, value: JsonObjectAdapter) {
        json.set(index, (value as MinimalObjectAdapter).json)
    }

    override fun add(value: JsonObjectAdapter) {
        json.add((value as MinimalObjectAdapter).json)
    }

    override fun add(value: JsonArrayAdapter) {
        json.add((value as MinimalArrayAdapter).json)
    }

    override fun add(value: String) {
        json.add(value)
    }

    override fun add(value: Number) {
        when (value) {
            is Long -> json.add(value)
            is Double ->  json.add(value)
            is Int ->  json.add(value)
            is Float ->  json.add(value)
        }
    }

    override fun add(value: Boolean) {
        json.add(value)
    }

    override fun getArray(index: Int): JsonArrayAdapter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setArray(index: Int, value: JsonArrayAdapter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getString(index: Int): String = json.get(index).asString()

    override fun setString(index: Int, value: String) {
        json.set(index, value)
    }

    override fun getNumber(index: Int): Number {
        val strValue = (json.get(index) as JsonValue).toString()
        return strValue.toLongOrNull() ?: strValue.toDouble()
    }

    override fun setNumber(index: Int, value: Number) {
        when (value) {
            is Long -> json.set(index, value)
            is Double -> json.set(index, value)
            is Int -> json.set(index, value)
            is Float -> json.set(index, value)
        }
    }

    override fun getBoolean(index: Int): Boolean =  (json.get(index) as JsonValue).asBoolean()

    override fun setBoolean(index: Int, value: Boolean) {
        json.set(index, value)
    }

    override fun toJsonString() = json.toString()

}