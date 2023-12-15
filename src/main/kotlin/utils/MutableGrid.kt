package utils

class MutableGrid<T> private constructor(private val baseGrid: BaseGrid<T>) {

    private val transformations = TransformationStack()
    private var cachedDimensions: Position? = null

    override fun toString(): String {
        return rows().reversed().joinToString("\n") { it.joinToString("") }
    }

    fun transpose() {
        transformations.add(Transposition())
        cachedDimensions = null
    }

    fun rotateClockwise() {
        transformations.add(ClockwiseRotation())
        cachedDimensions = null
    }

    fun rotateCounterClockwise() {
        rotateClockwise()
        rotateClockwise()
        rotateClockwise()
    }

    fun get(position: Position) = baseGrid[transformations.untransformPosition(position, dimensions())]

    fun set(position: Position, value: T) {
        baseGrid[transformations.untransformPosition(position, dimensions())] = value
    }

    private fun dimensions(): Position {
        if (cachedDimensions == null) {
            cachedDimensions = transformations.transformDimensions(baseGrid.dimensions())
        }
        return cachedDimensions!!
    }

    fun numCols() = dimensions().x

    fun numRows() = dimensions().y

    fun columns(): Indexable<Indexable<T>> {

        return object : Indexable<Indexable<T>> {

            override fun get(index: Int): Indexable<T> {
                val colNum = index
                return object : Indexable<T> {
                    override fun get(index: Int): T {
                        return get(Position.of(colNum, index))
                    }

                    override fun set(index: Int, value: T) {
                        return set(Position.of(colNum, index), value)
                    }

                    override fun size(): Int {
                        return numRows()
                    }
                }
            }

            override fun set(index: Int, value: Indexable<T>) {
                throw UnsupportedOperationException()
            }

            override fun size(): Int {
                return numCols()
            }
        }
    }

    fun rows(): Indexable<Indexable<T>> {

        return object : Indexable<Indexable<T>> {

            override fun get(index: Int): Indexable<T> {
                val rowNum = index
                return object : Indexable<T> {
                    override fun get(index: Int): T {
                        return get(Position.of(index, rowNum))
                    }

                    override fun set(index: Int, value: T) {
                        return set(Position.of(index, rowNum), value)
                    }

                    override fun size(): Int {
                        return numCols()
                    }
                }
            }

            override fun set(index: Int, value: Indexable<T>) {
                throw UnsupportedOperationException()
            }

            override fun size(): Int {
                return numRows()
            }
        }
    }

    data class Position(val x: Int, val y: Int) {
        companion object Factory {

            private val map = Array(500) { Array<Position?>(500) { null } }

            fun of(x: Int, y: Int): Position {
                if (map[x][y] == null) {
                    map[x][y] = Position(x, y)
                }
                return map[x][y]!!
            }
        }
    }

    companion object {

        fun of(input: String): MutableGrid<Char> {
            val sanitizedInput = input.split("\n").map { it.trim() }.filter { it.isNotBlank() }
                .reversed()
            if (sanitizedInput.isEmpty()) {
                throw IllegalArgumentException("Empty input")
            }
            if (sanitizedInput.map { it.length }.distinct().size > 1) {
                throw IllegalArgumentException("Not all rows have the same length")
            }
            val numCols = sanitizedInput[0].length

            return MutableGrid(BaseGrid((0..<numCols).map { numCol ->
                sanitizedInput.map { it[numCol] }.toTypedArray<Char>()
            }.toTypedArray<Array<Char>>()))
        }
    }

    private class TransformationStack : Transformation {

        private val list = mutableListOf<Transformation>()

        fun add(transformation: Transformation) {
            list.add(transformation)

            if (list.size >= 4 && list.takeLast(4).all { it is ClockwiseRotation }) {
                repeat(4) { list.removeLast() }
            }
        }

        override fun transformPosition(position: Position, dimensions: Position): Position {

            var rPos = position
            var rDim = dimensions

            for (tr in list) {
                rPos = tr.transformPosition(rPos, rDim)
                rDim = tr.transformDimensions(rDim)
            }

            return rPos
        }

        override fun untransformPosition(position: Position, dimensions: Position): Position {
            if (list.size > 10) {
                throw RuntimeException()
            }

            var rPos = position
            var rDim = dimensions

            for (tr in list.reversed()) {
                rPos = tr.untransformPosition(rPos, rDim)
                rDim = tr.untransformDimensions(rDim)
            }

            return rPos
        }

        override fun transformDimensions(dimensions: Position): Position {
            var r = dimensions
            for (tr in list) {
                r = tr.transformDimensions(r)
            }
            return r
        }

        override fun untransformDimensions(dimensions: Position): Position {

            var r = dimensions
            for (tr in list.reversed()) {
                r = tr.transformDimensions(r)
            }
            return r

//            return list.foldRight(dimensions) { tr, dim -> tr.untransformDimensions(dim) }
        }
    }

    private interface Transformation {

        fun transformPosition(position: Position, dimensions: Position): Position

        fun untransformPosition(position: Position, dimensions: Position): Position

        fun transformDimensions(dimensions: Position): Position
        fun untransformDimensions(dimensions: Position): Position
    }

    private class ClockwiseRotation : Transformation {

        override fun transformPosition(position: Position, dimensions: Position): Position {
            return Position.of(position.y, dimensions.x - position.x - 1)
        }

        override fun untransformPosition(position: Position, dimensions: Position): Position {
            return Position.of(dimensions.y - position.y - 1, position.x)
        }

        override fun transformDimensions(dimensions: Position): Position = Position.of(dimensions.y, dimensions.x)

        override fun untransformDimensions(dimensions: Position): Position = Position.of(dimensions.y, dimensions.x)
    }

    private class Transposition : Transformation {

        override fun transformPosition(position: Position, dimensions: Position): Position {
            return Position.of(position.y, position.x)
        }

        override fun untransformPosition(position: Position, dimensions: Position): Position {
            return Position.of(position.y, position.x)
        }

        override fun transformDimensions(dimensions: Position): Position = Position.of(dimensions.y, dimensions.x)

        override fun untransformDimensions(dimensions: Position): Position = Position.of(dimensions.y, dimensions.x)
    }

    private class BaseGrid<T>(val data: Array<Array<T>>) {

        operator fun get(position: Position): T {
            return data[position.x][position.y]
        }

        operator fun set(position: Position, value: T) {
            data[position.x][position.y] = value
        }

        fun dimensions() = Position.of(numCols(), numRows())

        fun numCols() = data.size

        fun numRows() = data[0].size
    }
}