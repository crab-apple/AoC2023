package utils

class InPlaceMutableGrid<T> private constructor(
    twoDArray: Array<Array<T>>,
) : MutableGrid<T> {

    private val data: MutableList<MutableList<T?>>
    private var numCols: Int
    private var numRows: Int

    init {
        numCols = twoDArray.size
        numRows = twoDArray[0].size

        val biggerDimension = numCols.coerceAtLeast(numRows)

        data = (0..<biggerDimension).map {
            (0..<biggerDimension).map { null }.toMutableList<T?>()
        }.toMutableList()

        (0..<numCols).forEach { col ->
            (0..<numRows).forEach { row ->
                data[col][row] = twoDArray[col][row]
            }
        }
    }

    override fun toString(): String {
        return rows().reversed().joinToString("\n") { it.joinToString("") }
    }

    override fun transpose() {
        data.indices.forEach { col ->
            data.indices.forEach { row ->
                if (col > row) {
                    swap(col, row, row, col)
                }
            }
        }
        swapDimensions()
    }

    override fun rotateClockwise() {

        val dataCopy = data.map { it.toMutableList() }.toList()

        (0..<numCols).forEach { col ->
            (0..<numRows).forEach { row ->
                dataCopy[row][numCols - col - 1] = data[col][row]
            }
        }

        data.indices.forEach { col ->
            data.indices.forEach { row ->
                data[col][row] = dataCopy[col][row]
            }
        }

        swapDimensions()
    }

    private fun swapDimensions() {
        val temp = numCols
        numCols = numRows
        numRows = temp
    }

    private fun swap(xA: Int, yA: Int, xB: Int, yB: Int) {
        val temp = data[xA][yA]
        data[xA][yA] = data[xB][yB]
        data[xB][yB] = temp
    }

    override fun rotateCounterClockwise() {
        rotateClockwise()
        rotateClockwise()
        rotateClockwise()
    }

    override fun get(x: Int, y: Int): T {
        return data[x][y]!!
    }

    override fun set(x: Int, y: Int, value: T) {
        data[x][y] = value
    }

    override fun numCols() = numCols

    override fun numRows() = numRows

    override fun columns(): Indexable<Indexable<T>> {

        return object : Indexable<Indexable<T>> {

            override fun get(index: Int): Indexable<T> {
                val colNum = index
                return object : Indexable<T> {
                    override fun get(index: Int): T {
                        return data[colNum][index]!!
                    }

                    override fun set(index: Int, value: T) {
                        data[colNum][index] = value
                        return
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

    override fun rows(): Indexable<Indexable<T>> {

        return object : Indexable<Indexable<T>> {

            override fun get(index: Int): Indexable<T> {
                val rowNum = index
                return object : Indexable<T> {
                    override fun get(index: Int): T {
                        return data[index][rowNum]!!
                    }

                    override fun set(index: Int, value: T) {
                        data[index][rowNum] = value
                        return
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

    companion object {

        fun of(input: String): InPlaceMutableGrid<Char> {
            val sanitizedInput = input.split("\n").map { it.trim() }.filter { it.isNotBlank() }
                .reversed()
            if (sanitizedInput.isEmpty()) {
                throw IllegalArgumentException("Empty input")
            }
            if (sanitizedInput.map { it.length }.distinct().size > 1) {
                throw IllegalArgumentException("Not all rows have the same length")
            }
            val numCols = sanitizedInput[0].length

            val twoDArray = (0..<numCols).map { numCol ->
                sanitizedInput.map { it[numCol] }.toTypedArray<Char>()
            }.toTypedArray<Array<Char>>()
            return InPlaceMutableGrid(twoDArray)
        }
    }
}