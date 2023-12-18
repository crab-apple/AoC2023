package day18

import utils.Direction
import utils.Direction.EAST
import utils.Direction.NORTH
import utils.Direction.SOUTH
import utils.Direction.WEST
import utils.Laterality
import utils.Position

/**
 * Note this class makes a number of assumptions, which seem to hold true for the given input:
 * - The walls don't touch each other (i.e. the interior positions from one contiguous area)
 * - The cycle runs clockwise
 * - The cycle starts running east
 */
class Trench private constructor(val corners: List<Corner>) {

    fun capacity(): Int {

        val edges = edges()

        val interiorPositions = mutableSetOf<Position>()
        val pendingToVisit = mutableListOf(Position(1, 1))

        while (pendingToVisit.isNotEmpty()) {
            val position = pendingToVisit.removeLast()
            if (edges.contains(position) || interiorPositions.contains(position)) {
                continue
            }
            interiorPositions.add(position)
            for (direction in Direction.entries) {
                pendingToVisit.add(position.neighbour(direction))
            }
        }

        return interiorPositions.size + edges.size
    }

    fun drawEdges(): String {

        val edges = edges()

        val minRow = edges.minOf { it.row }
        val maxRow = edges.maxOf { it.row }
        val minCol = edges.minOf { it.col }
        val maxCol = edges.maxOf { it.col }
        return (minRow..maxRow).map { row ->
            (minCol..maxCol).map { col -> Position(row, col) }
                .map { if (it == Position(0, 0)) '@' else if (edges.contains(it)) '#' else '.' }.joinToString("")
        }.joinToString("\n")
    }

    private fun edges(): Set<Position> {
        val edges = mutableSetOf<Position>()

        var currentPosition = Position(0, 0)

        edges.add(currentPosition)
        for (corner in corners) {
            for (position in (currentPosition.until(corner.position))) {
                edges.add(position)
            }
            currentPosition = corner.position
        }
        return edges
    }

    companion object Parser {

        fun parse(input: String): Trench {
            val inputLines = InputLine.parse(input)

            val corners = mutableListOf<Corner>()
            var currentPosition = Position(0, 0)
            var currentDirection = inputLines.last().direction
            inputLines.forEach { line ->
                val turningSide = Laterality.entries.first { currentDirection.turn(it) == line.direction }
                corners.add(Corner(currentPosition, turningSide))
                currentPosition = currentPosition.moving(line.direction, line.numSteps)
                currentDirection = line.direction
            }

            corners.add(corners.removeFirst())

            return Trench(corners)
        }
    }
}

data class Corner(val position: Position, val side: Laterality) {
    constructor(row: Int, col: Int, side: Laterality) : this(Position(row, col), side)
}

private class InputLine(val direction: Direction, val numSteps: Int) {
    companion object Parser {

        fun parse(input: String): List<InputLine> {
            return input.lines().map { line ->
                InputLine(
                    parseDirection(line.split(" ")[0]),
                    line.split(" ")[1].toInt()
                )
            }
        }

        private fun parseDirection(input: String) = when (input) {
            "U" -> NORTH
            "D" -> SOUTH
            "R" -> EAST
            "L" -> WEST
            else -> throw IllegalArgumentException("")
        }
    }
}