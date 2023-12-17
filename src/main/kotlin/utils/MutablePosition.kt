package utils

data class MutablePosition(var row: Int, var col: Int) {

    fun neighbour(direction: Direction): Position {
        return when (direction) {
            Direction.NORTH -> Position(row - 1, col)
            Direction.SOUTH -> Position(row + 1, col)
            Direction.EAST -> Position(row, col + 1)
            Direction.WEST -> Position(row, col - 1)
        }
    }

    fun move(direction: Direction) {
        when (direction) {
            Direction.NORTH -> row--
            Direction.SOUTH -> row++
            Direction.EAST -> col++
            Direction.WEST -> col--
        }
    }
}