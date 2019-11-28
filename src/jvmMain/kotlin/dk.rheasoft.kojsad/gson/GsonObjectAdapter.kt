package dk.rheasoft.kojsad.gson

import com.github.salomonbrys.kotson.set
import com.github.salomonbrys.kotson.typedToJsonTree
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import dk.rheasoft.kojsad.JsonArrayAdapter
import dk.rheasoft.kojsad.JsonElementType
import dk.rheasoft.kojsad.JsonObjectAdapter

class GsonObjectAdapter(val json: JsonObject = JsonObject()) : JsonObjectAdapter {
    override fun createArray(): JsonArrayAdapter = GsonArrayAdapter()
    override fun createObject(): JsonObjectAdapter = GsonObjectAdapter()

    override fun createArray(json: String): JsonArrayAdapter {
        val jsonObject: JsonArray = JsonParser().parse(json).asJsonArray
        return GsonArrayAdapter(jsonObject)
    }

    override fun createObject(json: String): JsonObjectAdapter {
        val jsonObject: JsonObject = JsonParser().parse(json).asJsonObject
        return GsonObjectAdapter(jsonObject)
    }

    override fun propertyNames(): Iterable<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(property: String): JsonElementType {
        val elm = json.get(property)
        return when {
            elm.isJsonObject -> JsonElementType.OBJECT
            elm.isJsonArray -> JsonElementType.ARRAY
            elm.isJsonPrimitive -> (elm as JsonPrimitive).let {
                when {
                    it.isNumber -> JsonElementType.NUMBER
                    it.isBoolean -> JsonElementType.BOOLEAN
                    else -> JsonElementType.STRING
                }
            }
            else -> JsonElementType.NULL
        }
    }


    override fun getObject(property: String): JsonObjectAdapter = GsonObjectAdapter(json.get(property).asJsonObject)

    override fun setObject(property: String, value: JsonObjectAdapter) {
        json.set(property, (value as GsonObjectAdapter).json)
    }

    override fun getArray(property: String): JsonArrayAdapter = GsonArrayAdapter(json.getAsJsonArray(property))

    override fun setArray(property: String, value: JsonArrayAdapter) {
        json.set(property, (value as GsonArrayAdapter).json)
    }

    override fun getString(property: String): String = json.getAsJsonPrimitive(property).asString

    override fun setString(property: String, value: String) {
        json.set(property, value)
    }

    override fun getNumber(property: String): Number =  json.getAsJsonPrimitive(property).asNumber

    override fun setNumber(property: String, value: Number) {
        json.set(property, value)
    }

    override fun getBoolean(property: String): Boolean =  json.getAsJsonPrimitive(property).asBoolean

    override fun setBoolean(property: String, value: Boolean) {
        json.set(property, value)
    }

    override fun toJsonString() = gsonPretty.toJson(json)

    companion object {
        val gsonBuilder: GsonBuilder
            get() = GsonBuilder()

        val gson: Gson
            get() = gsonBuilder.create()

        val gsonPretty: Gson
            get() = gsonBuilder.setPrettyPrinting().create()
    }
}