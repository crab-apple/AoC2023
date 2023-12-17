package day17

import utils.Direction
import utils.Direction.EAST
import utils.Laterality
import utils.Laterality.LEFT
import utils.Laterality.RIGHT
import utils.Position
import utils.println
import utils.readInputOneString
import utils.Direction.SOUTH as SOUTH1

private val REGULAR_CRUCIBLE = CrucibleParams(0, 3)
private val ULTRA_CRUCIBLE = CrucibleParams(4, 10)

fun main() {
    val input = readInputOneString("day17/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: String): Int {
    return solve(input, REGULAR_CRUCIBLE)
}

fun solvePart2(input: String): Int {
    return solve(input, ULTRA_CRUCIBLE)
}

private fun solve(input: String, crucibleParams: CrucibleParams): Int {
    val grid = input.split("\n").map { it.map { it.digitToInt() } }
    val numRows = grid.size
    val numCols = grid[0].size

    fun inBounds(position: Position): Boolean {
        return position.row in 0..<numRows && position.col in 0..<numCols
    }

    fun heatLossAtPosition(position: Position) = grid[position.row][position.col]

    val costs = mutableMapOf<State, Int>()
    val pendingToEvaluate = mutableListOf<State>()

    val initialStates = listOf(
        State(Position(0, 0), EAST, 0),
        State(Position(0, 0), SOUTH1, 0)
    )
    for (initialState in initialStates) {
        costs[initialState] = 0
        pendingToEvaluate.add(initialState)
    }

    while (pendingToEvaluate.isNotEmpty()) {
        val currentlyEvaluating = pendingToEvaluate.removeFirst()
        var nextStates = currentlyEvaluating.nextStates(crucibleParams)
            .filter { inBounds(it.position) }

        nextStates.forEach { nextState ->

            val newCost = costs[currentlyEvaluating]!! + heatLossAtPosition(nextState.position)
            val oldCost = costs[nextState]
            if (oldCost == null || oldCost > newCost) {
                costs[nextState] = newCost
                pendingToEvaluate.add(nextState)
            }
        }
    }

    return costs.entries
        .filter { it.key.position == Position(numRows - 1, numCols - 1) }
        .filter { it.key.straightMovesSoFar >= crucibleParams.minMovesSameDirection }
        .minOf { it.value }
}

private data class State(
    val position: Position,
    val direction: Direction,
    val straightMovesSoFar: Int
) {

    fun nextStates(crucibleParams: CrucibleParams): List<State> {
        val result = mutableListOf<State>()
        if (straightMovesSoFar >= crucibleParams.minMovesSameDirection) {
            result.add(turning(LEFT))
            result.add(turning(RIGHT))
        }
        if (straightMovesSoFar < crucibleParams.maxMovesSameDirection) {
            result.add(goingStraight())
        }
        return result
    }

    fun turning(laterality: Laterality): State {
        return State(
            position.neighbour(direction.turn(laterality)),
            direction.turn(laterality),
            1
        )
    }

    fun goingStraight(): State {
        return State(
            position.neighbour(direction),
            direction,
            straightMovesSoFar + 1
        )
    }
}

data class CrucibleParams(val minMovesSameDirection: Int, val maxMovesSameDirection: Int)



