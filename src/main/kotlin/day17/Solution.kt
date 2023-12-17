package day17

import utils.Direction
import utils.Direction.EAST
import utils.Laterality
import utils.Laterality.LEFT
import utils.Laterality.RIGHT
import utils.Position
import utils.println
import utils.readInputOneString

private const val MAX_MOVES_SAME_DIRECTION = 3

fun main() {
    val input = readInputOneString("day17/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: String): Int {

    val grid = input.split("\n").map { it.map { it.digitToInt() } }
    val numRows = grid.size
    val numCols = grid[0].size

    fun inBounds(position: Position): Boolean {
        return position.row in 0..<numRows && position.col in 0..<numCols
    }

    fun heatLossAtPosition(position: Position) = grid[position.row][position.col]

    val costs = mutableMapOf<State, Int>()
    val pendingToEvaluate = mutableListOf<State>()

    val initialState = State(Position(0, 0), EAST, MAX_MOVES_SAME_DIRECTION)
    costs[initialState] = 0
    pendingToEvaluate.add(initialState)

    while (pendingToEvaluate.isNotEmpty()) {
        val currentlyEvaluating = pendingToEvaluate.removeFirst()
        var nextStates = currentlyEvaluating.nextStates()
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
        .filter { it.key.position==Position(numRows-1,numCols-1) }
        .minOf { it.value }
}

fun solvePart2(input: String): Int {
    return input.length
}

private data class State(val position: Position, val direction: Direction, val straightMovesLeft: Int) {

    fun nextStates(): List<State> {
        val result = mutableListOf<State>()
        result.add(turning(LEFT))
        result.add(turning(RIGHT))
        if (straightMovesLeft > 0) {
            result.add(goingStraight())
        }
        return result
    }

    fun turning(laterality: Laterality): State {
        return State(
            position.neighbour(direction.turn(laterality)),
            direction.turn(laterality),
            MAX_MOVES_SAME_DIRECTION - 1
        )
    }

    fun goingStraight(): State {
        return State(
            position.neighbour(direction),
            direction,
            straightMovesLeft - 1
        )
    }
}



