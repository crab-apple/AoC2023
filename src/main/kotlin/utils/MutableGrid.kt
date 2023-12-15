package utils

interface MutableGrid<T> {

    override fun toString(): String
    fun transpose()
    fun rotateClockwise()
    fun rotateCounterClockwise()
    fun get(x: Int, y: Int): T
    fun set(x: Int, y: Int, value: T)
    fun numCols(): Int
    fun numRows(): Int
    fun columns(): Indexable<Indexable<T>>
    fun rows(): Indexable<Indexable<T>>
}