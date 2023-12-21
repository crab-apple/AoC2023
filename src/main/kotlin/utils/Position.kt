package utils

import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sign

data class Position(val row: Long, val col: Long) {

    constructor(row: Int, col: Int) : this(row.toLong(), col.toLong())

    fun neighbour(direction: Direction) = moving(direction, 1)

    fun allNeighbours() = Direction.entries.map { neighbour(it) }

    fun moving(direction: Direction, distance: Long): Position {

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

    fun abs(): Long {
        return abs(row) + abs(col)
    }

    fun until(other: Position): List<Position> {
        if (other.row != this.row && other.col != this.col) {
            throw IllegalArgumentException("The other position must be in the same row or column")
        }

        if (listOf(other.row, this.row, other.col, this.col).any { it.absoluteValue > Int.MAX_VALUE }) {
            throw IllegalArgumentException("These are very big numbers!")
        }

        return if (other.row == this.row) {
            progression(col.toInt(), other.col.toInt())
                .map { Position(this.row, it.toLong()) }
        } else {
            progression(row.toInt(), other.row.toInt())
                .map { Position(it.toLong(), this.col) }
        }
    }

    private fun progression(fromExclusive: Int, toInclusive: Int) =
        IntProgression.fromClosedRange(fromExclusive, toInclusive, (toInclusive - fromExclusive).sign)
            .drop(1)
}