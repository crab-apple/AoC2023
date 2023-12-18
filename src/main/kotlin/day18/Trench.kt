package day18

import utils.Direction
import utils.Direction.EAST
import utils.Direction.NORTH
import utils.Direction.SOUTH
import utils.Direction.WEST
import utils.Position

class Trench(val edges: Set<Position>) {

    fun capacity(): Int {
        // Note that this only works as long as walls don't touch each other (i.e. the interior
        // positions from one contiguous area) and the position at (1,1) is inside the trench. This
        // seems to be the case for the input
        val rows = edges.groupBy { it.row }.values.map { edgesInRow -> edgesInRow.map { it.col }.sorted() }

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
        val minRow = edges.minOf { it.row }
        val maxRow = edges.maxOf { it.row }
        val minCol = edges.minOf { it.col }
        val maxCol = edges.maxOf { it.col }
        return (minRow..maxRow).map { row ->
            (minCol..maxCol).map { col -> Position(row, col) }
                .map { if (edges.contains(it)) '#' else '.' }.joinToString("")
        }.joinToString("\n")
    }

    companion object Parser {

        fun parse(input: String): Trench {
            return InputLine.parse(input).flatMap { line -> List(line.numSteps) { line.direction } }
                .runningFold(Position(0, 0)) { pos, direction -> pos.neighbour(direction) }
                .toSet()
                .let { Trench(it) }
        }
    }
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