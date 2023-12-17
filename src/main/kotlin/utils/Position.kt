package utils

data class Position(val row: Int, val col: Int) {

    fun neighbour(direction: Direction): Position {
        return when (direction) {
            Direction.NORTH -> Position(row - 1, col)
            Direction.SOUTH -> Position(row + 1, col)
            Direction.EAST -> Position(row, col + 1)
            Direction.WEST -> Position(row, col - 1)
        }
    }
}