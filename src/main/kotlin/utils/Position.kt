package utils

import kotlin.math.abs
import kotlin.math.sign

data class Position(val row: Int, val col: Int) {

    fun neighbour(direction: Direction) = moving(direction, 1)

    fun moving(direction: Direction, distance: Int): Position {

        val delta = when (direction) {
            Direction.NORTH -> Position(-distance, 0)
            Direction.SOUTH -> Position(distance, 0)
            Direction.EAST -> Position(0, distance)
            Direction.WEST -> Position(0, -distance)
        }

        return plus(delta)
    }

    operator fun plus(other: Position): Position {
        return Position(row + other.row, col + other.col)
    }

    operator fun minus(other: Position): Position {
        return Position(row - other.row, col - other.col)
    }

    fun abs(): Int {
        return abs(row) + abs(col)
    }

    fun until(other: Position): List<Position> {
        if (other.row != this.row && other.col != this.col) {
            throw IllegalArgumentException("The other position must be in the same row or column")
        }
        return if (other.row == this.row) {
            progression(col, other.col)
                .map { Position(this.row, it) }
        } else {
            progression(row, other.row)
                .map { Position(it, this.col) }
        }
    }

    private fun progression(fromExclusive: Int, toInclusive: Int) =
        IntProgression.fromClosedRange(fromExclusive, toInclusive, (toInclusive - fromExclusive).sign)
            .drop(1)
}