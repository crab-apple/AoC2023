package utils

interface Indexable<T> : Iterable<T> {

    operator fun get(index: Int): T
    operator fun set(index: Int, value: T)

    fun size(): Int

    override operator fun iterator(): Iterator<T> {
        return object : Iterator<T> {
            var index = 0
            override fun hasNext(): Boolean {
                return index < size()
            }

            override fun next(): T {
                return get(index++)
            }
        }
    }

    companion object {

        fun <T> of(arr: Array<T>): Indexable<T> = ArrayIndexable(arr)
        fun <T> of(arr: MutableList<T>): Indexable<T> = ListIndexable(arr)
    }

    private class ArrayIndexable<T>(private val arr: Array<T>) : Indexable<T> {

        override fun get(index: Int) = arr[index]

        override fun set(index: Int, value: T) {
            arr[index] = value
        }

        override fun size() = arr.size
    }

    private class ListIndexable<T>(private val arr: MutableList<T>) : Indexable<T> {

        override fun get(index: Int) = arr[index]

        override fun set(index: Int, value: T) {
            arr[index] = value
        }

        override fun size() = arr.size
    }
}