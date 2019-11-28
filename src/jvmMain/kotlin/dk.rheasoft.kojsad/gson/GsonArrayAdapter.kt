package dk.rheasoft.kojsad.gson

import com.github.salomonbrys.kotson.set
import com.github.salomonbrys.kotson.typedToJsonTree
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import dk.rheasoft.kojsad.JsonArrayAdapter
import dk.rheasoft.kojsad.JsonElementType
import dk.rheasoft.kojsad.JsonObjectAdapter

class GsonArrayAdapter(val json: JsonArray = JsonArray()) : JsonArrayAdapter {
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

    override fun length(): Int = json.size()

    override fun getType(index: Int): JsonElementType {
        val elm = json.get(index)
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

    override fun remove(index: Int) {
        json.remove(index)
    }


    override fun getObject(index: Int): JsonObjectAdapter = GsonObjectAdapter(json.get(index).asJsonObject)

    override fun setObject(index: Int, value: JsonObjectAdapter) {
        json.set(index, (value as GsonObjectAdapter).json)
    }

    override fun add(value: JsonObjectAdapter) {
        json.add((value as GsonObjectAdapter).json)
    }

    override fun add(value: JsonArrayAdapter) {
        json.add((value as GsonArrayAdapter).json)
    }

    override fun add(value: String) {
        json.add(value)
    }

    override fun add(value: Number) {
        json.add(value)
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

    override fun getString(index: Int): String = json.get(index).asString

    override fun setString(index: Int, value: String) {
        json.set(index, value)
    }

    override fun getNumber(index: Int): Number =  json.get(index).asNumber

    override fun setNumber(index: Int, value: Number) {
        json.set(index, value)
    }

    override fun getBoolean(index: Int): Boolean =  json.get(index).asBoolean

    override fun setBoolean(index: Int, value: Boolean) {
        json.set(index, value)
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