package dk.rheasoft.kojsad

open class ObjectAdapter(val adapter: JsonObjectAdapter) {
    protected fun stringDelegate() = JsonStringDelegate(adapter)
    protected fun intDelegate() = JsonIntDelegate(adapter)
    protected fun longDelegate() = JsonLongDelegate(adapter)
    protected fun doubleDelegate() = JsonDoubleDelegate(adapter)
    protected fun booleanDelegate() = JsonBooleanDelegate(adapter)

    protected fun <T: ObjectAdapter> objectDelegate(construct: (adapter: JsonObjectAdapter) -> T) = JsonObjectDelegate(construct, adapter)

    protected fun <T: ObjectAdapter> listDelegate(construct: (adapter: JsonObjectAdapter) -> T) = JsonListDelegate(construct, adapter)

    public fun toJsonString() = adapter.toJsonString()

    override fun toString(): String {
        return this::class.simpleName + "> " + toJsonString()
    }
}