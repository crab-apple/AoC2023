package day16

import utils.Direction
import utils.Direction.EAST
import utils.Direction.NORTH
import utils.Direction.SOUTH
import utils.Direction.WEST
import utils.Position
import utils.println
import utils.readInputOneString

fun main() {
    val input = readInputOneString("day16/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: String): Int {
    return makeEnergyMap(input).count { it == '#' }
}

fun solvePart2(input: String): Int {

    val numRows = input.lines().size
    val numCols = input.lines()[0].length

    val startingDirections: List<Pair<Position, Direction>> = listOf<Pair<Position, Direction>>()
        .plus((0..<numRows).map { Pair(Position(it, 0), EAST) })
        .plus((0..<numRows).map { Pair(Position(it, numCols - 1), WEST) })
        .plus((0..<numCols).map { Pair(Position(0, it), SOUTH) })
        .plus((0..<numCols).map { Pair(Position(0, numRows - 1), NORTH) })

    return startingDirections.maxOf { makeEnergyMap(input, it).count { it == '#' } }
}

fun makeEnergyMap(input: String): String {
    return makeEnergyMap(input, Pair(Position(0, 0), EAST))
}

private fun makeEnergyMap(input: String, startingDirection: Pair<Position, Direction>): String {
    val artifactsGrid = input.lines()

    fun withinBounds(position: Position): Boolean {
        return artifactsGrid.indices.contains(position.row) && artifactsGrid[0].indices.contains(position.col)
    }

    val energized = mutableSetOf<Position>()
    val pendingToEvaluate = mutableListOf<Pair<Position, Direction>>()
    val evaluated = mutableSetOf<Pair<Position, Direction>>()

    pendingToEvaluate.add(startingDirection)

    while (pendingToEvaluate.isNotEmpty()) {
        val current = pendingToEvaluate.removeFirst()
        val position = current.first
        val direction = current.second

        energized.add(position)

        val nextDirections = mutableListOf<Direction>()
        val currentArtifact = artifactsGrid[position.row][position.col]

        if (currentArtifact == '\\') {
            nextDirections.add(
                when (direction) {
                    EAST -> SOUTH
                    NORTH -> WEST
                    SOUTH -> EAST
                    WEST -> NORTH
                }
            )
        } else if (currentArtifact == '/') {
            nextDirections.add(
                when (direction) {
                    EAST -> NORTH
                    NORTH -> EAST
                    SOUTH -> WEST
                    WEST -> SOUTH
                }
            )
        } else if (currentArtifact == '|') {
            nextDirections.addAll(
                when (direction) {
                    EAST -> listOf(NORTH, SOUTH)
                    WEST -> listOf(NORTH, SOUTH)
                    NORTH -> listOf(NORTH)
                    SOUTH -> listOf(SOUTH)
                }
            )
        } else if (currentArtifact == '-') {
            nextDirections.addAll(
                when (direction) {
                    EAST -> listOf(EAST)
                    WEST -> listOf(WEST)
                    NORTH -> listOf(EAST, WEST)
                    SOUTH -> listOf(EAST, WEST)
                }
            )
        } else {
            nextDirections.add(direction)
        }

        for (nextDirection in nextDirections) {
            val next = Pair(position.neighbour(nextDirection), nextDirection)
            if (withinBounds(next.first)) {
                if (evaluated.add(next)) {
                    pendingToEvaluate.add(next)
                }
            }
        }
    }

    val result = artifactsGrid.indices.joinToString("\n") { rowNum ->
        artifactsGrid[0].indices.map { colNum -> if (energized.contains(Position(rowNum, colNum))) '#' else '.' }
            .joinToString("")
    }

    return result
}

