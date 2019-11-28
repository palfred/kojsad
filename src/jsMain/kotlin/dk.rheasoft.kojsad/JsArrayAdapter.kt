package dk.rheasoft.kojsad

import kotlin.js.Json

class JsArrayAdapter(var json: Array<Any?> = arrayOf()) : JsonArrayAdapter {
    override fun createArray(): JsonArrayAdapter = JsArrayAdapter()
    override fun createObject(): JsonObjectAdapter = JsObjectAdapter()

    override fun createArray(json: String): JsonArrayAdapter {
        val jsonArr: Array<Any?> = JSON.parse(json)
        return JsArrayAdapter(jsonArr)
    }

    override fun createObject(json: String): JsonObjectAdapter {
        val jsonObject: Json = JSON.parse(json)
        return JsObjectAdapter(jsonObject)
    }

    override fun length(): Int = json.size

    override fun getType(index: Int): JsonElementType {
        val elm = json.get(index)
        return when {
            elm is Array<*> -> JsonElementType.ARRAY
            elm is Number -> JsonElementType.NUMBER
            elm is Boolean -> JsonElementType.BOOLEAN
            elm is String -> JsonElementType.STRING
            else -> JsonElementType.OBJECT
        }
    }

    override fun remove(index: Int) {
        json = json.toMutableList().apply{removeAt(index)}.toTypedArray()
    }

    override fun getObject(index: Int): JsonObjectAdapter = JsObjectAdapter(json.get(index) as Json)

    override fun setObject(index: Int, value: JsonObjectAdapter) {
        json.set(index, (value as JsObjectAdapter).json)
    }
    private fun internalAdd(value: Any) {
        json = json.toMutableList().apply{add(value)}.toTypedArray()
    }
    override fun add(value: JsonObjectAdapter) {
        internalAdd((value as JsObjectAdapter).json)
    }

    override fun add(value: JsonArrayAdapter) {
        internalAdd((value as JsArrayAdapter).json)
    }

    override fun add(value: String) {
        internalAdd(value)
    }

    override fun add(value: Number) {
        internalAdd(value)
    }

    override fun add(value: Boolean) {
        internalAdd(value)
    }

    override fun getArray(index: Int): JsonArrayAdapter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setArray(index: Int, value: JsonArrayAdapter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getString(index: Int): String = json.get(index) as String

    override fun setString(index: Int, value: String) {
        json.set(index, value)
    }

    override fun getNumber(index: Int): Number =  json.get(index) as Number

    override fun setNumber(index: Int, value: Number) {
        json.set(index, value)
    }

    override fun getBoolean(index: Int): Boolean =  json.get(index) as Boolean

    override fun setBoolean(index: Int, value: Boolean) {
        json.set(index, value)
    }

    override fun toJsonString() = JSON.stringify(json)

}