package dk.rheasoft.kojsad

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class JsonString : ReadWriteProperty<ObjectAdapter, String> {
    override fun getValue(thisRef: ObjectAdapter, property: KProperty<*>): String =
        thisRef.adapter.getString(property.name)

    override fun setValue(thisRef: ObjectAdapter, property: KProperty<*>, value: String) {
        thisRef.adapter.setString(property.name, value)
    }
}

class JsonInt : ReadWriteProperty<ObjectAdapter, Int> {
    override fun getValue(thisRef: ObjectAdapter, property: KProperty<*>): Int =
        thisRef.adapter.getNumber(property.name).toInt()

    override fun setValue(thisRef: ObjectAdapter, property: KProperty<*>, value: Int) {
        thisRef.adapter.setNumber(property.name, value)
    }
}

class JsonLong : ReadWriteProperty<ObjectAdapter, Long> {
    override fun getValue(thisRef: ObjectAdapter, property: KProperty<*>): Long =
        thisRef.adapter.getNumber(property.name).toLong()

    override fun setValue(thisRef: ObjectAdapter, property: KProperty<*>, value: Long) {
        thisRef.adapter.setNumber(property.name, value)
    }
}

class JsonDouble : ReadWriteProperty<ObjectAdapter, Double> {
    override fun getValue(thisRef: ObjectAdapter, property: KProperty<*>): Double =
        thisRef.adapter.getNumber(property.name).toDouble()

    override fun setValue(thisRef: ObjectAdapter, property: KProperty<*>, value: Double) {
        thisRef.adapter.setNumber(property.name, value)
    }
}

class JsonBooleanDelegate  : ReadWriteProperty<ObjectAdapter, Boolean> {
    override fun getValue(thisRef: ObjectAdapter, property: KProperty<*>): Boolean =
        thisRef.adapter.getBoolean(property.name)

    override fun setValue(thisRef: ObjectAdapter, property: KProperty<*>, value: Boolean) {
        thisRef.adapter.setBoolean(property.name, value)
    }
}

class JsonObject<T : ObjectAdapter>(val construct: (adapter: JsonObjectAdapter) -> T) : ReadWriteProperty<ObjectAdapter, T>{
    override fun getValue(thisRef: ObjectAdapter, property: KProperty<*>): T =
        construct(thisRef.adapter.getObject(property.name))

    override fun setValue(thisRef: ObjectAdapter, property: KProperty<*>, value: T) {
        thisRef.adapter.setObject(property.name, value.adapter)
    }
}

class JsonList<T : ObjectAdapter>(val construct: (adapter: JsonObjectAdapter) -> T) : ReadWriteProperty<ObjectAdapter, List<T>>{
    override fun getValue(thisRef: ObjectAdapter, property: KProperty<*>): List<T> {
        val array = thisRef.adapter.getArray(property.name)
        val adaptedList = array.objectIterator().asSequence() //
            .map { construct(it) } //
            .toList()
        return adaptedList
    }

    override fun setValue(thisRef: ObjectAdapter, property: KProperty<*>, value: List<T>) {
        // TODO map to right type of JsonArrayAdapter
        val array = thisRef.adapter.createArray()
        value.map { it.adapter }.forEach { array.add(it) }
        thisRef.adapter.setArray(property.name, array)
    }
}

// TODO Object and Array/List