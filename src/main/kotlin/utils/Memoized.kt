package utils

class Memoized<in T, out R>(val f: (T) -> R) : (T) -> R {

    private val values = mutableMapOf<T, R>()
    override fun invoke(x: T): R {
        return values.getOrPut(x, { f(x) })
    }

    fun clearCache() {
        values.clear()
    }
}

fun <T, R> ((T) -> R).memoize(): Memoized<T, R> = Memoized(this)

