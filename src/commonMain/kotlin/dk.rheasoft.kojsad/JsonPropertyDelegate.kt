package dk.rheasoft.kojsad

import kotlin.reflect.KProperty

class JsonStringDelegate(val adapter: JsonObjectAdapter) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String = adapter.getString(property.name)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        adapter.setString(property.name, value)
    }
}

class JsonIntDelegate(val adapter: JsonObjectAdapter) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int = adapter.getNumber(property.name).toInt()

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        adapter.setNumber(property.name, value)
    }
}

class JsonLongDelegate(val adapter: JsonObjectAdapter) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Long = adapter.getNumber(property.name).toLong()

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
        adapter.setNumber(property.name, value)
    }
}

class JsonDoubleDelegate(val adapter: JsonObjectAdapter) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Double = adapter.getNumber(property.name).toDouble()

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Double) {
        adapter.setNumber(property.name, value)
    }
}

class JsonBooleanDelegate(val adapter: JsonObjectAdapter) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Boolean = adapter.getBoolean(property.name)

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
        adapter.setBoolean(property.name, value)
    }
}

class JsonObjectDelegate<T : ObjectAdapter>(val construct: (adapter: JsonObjectAdapter) -> T,  val adapter: JsonObjectAdapter) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = construct(adapter.getObject(property.name))

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        adapter.setObject(property.name, value.adapter)
    }
}

class JsonListDelegate<T : ObjectAdapter>(val construct: (adapter: JsonObjectAdapter) -> T,  val adapter: JsonObjectAdapter) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): List<T> {
        val array = adapter.getArray(property.name)
        val adaptedList = array.objectIterator().asSequence() //
            .map { construct(it) } //
            .toList()
        return adaptedList
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: List<T>) {
        // TODO map to right type of JsonArrayAdapter
        val array = adapter.createArray()
        value.map { it.adapter }.forEach { array.add(it) }
        adapter.setArray(property.name, array)
    }
}

// TODO Object and Array/List