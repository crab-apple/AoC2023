class MutableGrid private constructor(private val input: Array<Array<Char>>) {

    private var transformation: Transformation = NopTransformation(input)

    override fun toString(): String {
        val sb = StringBuilder()
        for (row in (0..<transformation.numRows()).reversed()) {
            for (col in 0..<transformation.numCols()) {
                sb.append(get(Position(col, row)))
            }
            sb.append("\n")
        }
        sb.deleteAt(sb.lastIndex)
        return sb.toString()
    }

    fun transpose() {
        transformation = Transposition(transformation)
    }

    fun rotateClockwise() {
        transformation = ClockwiseRotation(transformation)
    }

    fun rotateCounterClockwise() {
        rotateClockwise()
        rotateClockwise()
        rotateClockwise()
    }

    fun get(position: Position) = getRaw(transformation.untransform(position))

    fun set(position: Position, value: Char) = setRaw(transformation.untransform(position), value)

    private fun getRaw(position: Position): Char {
        return input[position.x][position.y]
    }

    private fun setRaw(position: Position, value: Char) {
        input[position.x][position.y] = value
    }

    data class Position(val x: Int, val y: Int) {

    }

    companion object {

        fun of(input: String): MutableGrid {
            val sanitizedInput = input.split("\n").map { it.trim() }.filter { it.isNotBlank() }
                .reversed()
            if (sanitizedInput.isEmpty()) {
                throw IllegalArgumentException("Empty input")
            }
            if (sanitizedInput.map { it.length }.distinct().size > 1) {
                throw IllegalArgumentException("Not all rows have the same length")
            }
            val numCols = sanitizedInput[0].length

            return MutableGrid((0..<numCols).map { numCol -> sanitizedInput.map { it[numCol] }.toTypedArray() }
                .toTypedArray())
        }
    }

    private class ClockwiseRotation(private val previous: Transformation) : Transformation {

        override fun untransform(position: Position): Position {
            return previous.untransform(Position(this.numRows() - 1 - position.y, position.x))
        }

        override fun numCols() = previous.numRows()

        override fun numRows() = previous.numCols()
    }

    private class Transposition(private val previous: Transformation) : Transformation {

        override fun untransform(position: Position): Position {
            return previous.untransform(Position(position.y, position.x))
        }

        override fun numCols() = previous.numRows()

        override fun numRows() = previous.numCols()
    }

    interface Transformation {

        fun untransform(position: Position): Position

        fun numCols(): Int

        fun numRows(): Int
    }

    class NopTransformation(private val input: Array<Array<Char>>) : Transformation {

        override fun untransform(position: Position) = position

        override fun numCols() = input.size

        override fun numRows() = input[0].size
    }
}