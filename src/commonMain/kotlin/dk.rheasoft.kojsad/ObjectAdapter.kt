package dk.rheasoft.kojsad

open class ObjectAdapter(val adapter: JsonObjectAdapter) {

    public fun toJsonString() = adapter.toJsonString()

    override fun toString(): String {
        return this::class.simpleName + "> " + toJsonString()
    }
}