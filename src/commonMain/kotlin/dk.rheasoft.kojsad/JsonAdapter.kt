package dk.rheasoft.kojsad

public expect var Adapter : JsonAdapter

interface JsonAdapter {
    fun createArray() : JsonArrayAdapter
    fun createObject() : JsonObjectAdapter
    fun createArray(json: String) : JsonArrayAdapter
    fun createObject(json: String) : JsonObjectAdapter

    fun toJsonString(): Any
}
enum class JsonElementType {
    OBJECT, ARRAY, STRING, BOOLEAN, NUMBER, NULL
}

interface JsonObjectAdapter : JsonAdapter{

    fun propertyNames() : Iterable<String>

    fun getType(property : String) : JsonElementType

    fun getObject(property : String) : JsonObjectAdapter
    fun setObject(property : String, value: JsonObjectAdapter)

    fun getArray(property : String) : JsonArrayAdapter
    fun setArray(property : String, value: JsonArrayAdapter)

    fun getString(property : String) : String
    fun setString(property : String, value : String)

    fun getNumber(property : String) : Number
    fun setNumber(property : String, value : Number)

    fun getBoolean(property : String) : Boolean
    fun setBoolean(property : String, value: Boolean)

}

interface JsonArrayAdapter : JsonAdapter {

    fun length() : Int

    fun getType(index: Int) : JsonElementType

    fun remove(index: Int)

    fun getObject(index: Int) : JsonObjectAdapter
    fun setObject(index: Int, value: JsonObjectAdapter)
    fun add(value: JsonObjectAdapter)
    fun objectIterator(): Iterator<JsonObjectAdapter> = object : Iterator<JsonObjectAdapter> {
        var index = 0
        override fun hasNext(): Boolean = index < length()
        override fun next(): JsonObjectAdapter = getObject(index++)
    }

    fun getArray(index: Int) : JsonArrayAdapter
    fun setArray(index: Int, value: JsonArrayAdapter)
    fun add(value: JsonArrayAdapter)
    fun arrayIterator(): Iterator<JsonArrayAdapter> = object : Iterator<JsonArrayAdapter> {
        var index = 0
        override fun hasNext(): Boolean = index < length()
        override fun next(): JsonArrayAdapter = getArray(index++)
    }

    fun getString(index: Int) : String
    fun setString(index: Int, value : String)
    fun add(value: String)
    fun stringIterator(): Iterator<String> = object : Iterator<String> {
        var index = 0
        override fun hasNext(): Boolean = index < length()
        override fun next(): String = getString(index++)
    }

    fun getNumber(index: Int) : Number
    fun setNumber(index: Int, value : Number)
    fun add(value: Number)
    fun numberIterator(): Iterator<Number> = object : Iterator<Number> {
        var index = 0
        override fun hasNext(): Boolean = index < length()
        override fun next(): Number = getNumber(index++)
    }

    fun getBoolean(index: Int) : Boolean
    fun setBoolean(index: Int, value: Boolean)
    fun add(value: Boolean)
    fun booleanIterator(): Iterator<Boolean> = object : Iterator<Boolean> {
        var index = 0
        override fun hasNext(): Boolean = index < length()
        override fun next(): Boolean = getBoolean(index++)
    }

}


