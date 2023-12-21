package day21

import utils.Position
import utils.println
import utils.readInputOneString
import java.util.*

fun main() {
    val input = readInputOneString("day21/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: String): Long {
    return solvePart1(input, 64)
}

fun solvePart1(input: String, targetSteps: Int): Long {

    val plot = Plot(input)
    val startingPosition = plot.allPositions.single { plot.valAt(it) == 'S' }
    return plot.computeReachabilities(startingPosition)
        .filter { it.value <= targetSteps && it.value % 2 == targetSteps % 2 }.count().toLong()
}

fun solvePart2(input: String): Long {

    // If there were no rocks, the positions reachable within N steps would be all those positions at a Manhattan
    // distance of N or fewer. And the positions reachable at exactly N steps would be about half of those - half
    // of them would be reachable and half wouldn't, depending on whether N is even or odd.

    // If N is very large, then for the biggest portion of the map, all you care about is whether the Manhattan distance
    // to that point is even or odd. That is assuming that all the empty positions are reachable - if some aren't because
    // they are surrounded by rocks, we can just replace them with rocks beforehand.

    // So the difficult part seems to be at the edges.

    // Because the edges of the plot are empty, all the repetitions that are not in a direct line with the plot
    // can be reached walking along the edges and entering the repetition at a corner. That leaves us with only 4
    // repetitions to compute separately, I believe

    // Perhaps the input is crafted in such a way that, even for repetitions that are in a direct line with the initial
    // plot, the shortest path to any destination point can also pass through the corners. We'll have to test that.

    return 0
}

private class Plot(input: String) {

    private val lines = input.lines().map { it.toList() }
    val allPositions = lines.indices.flatMap { row -> lines[0].indices.map { Position(row, it) } }.toSet()
    val rocks = allPositions.filter { valAt(it) == '#' }.toSet()
    fun valAt(pos: Position) = lines[pos.row.toInt()][pos.col.toInt()]

    fun computeReachabilities(startingPosition: Position): Map<Position, Int> {
        val reachabilities = mutableMapOf<Position, Int>()
        val pendingToVisit = LinkedList<Pair<Position, Int>>()

        pendingToVisit.add(Pair(startingPosition, 0))

        while (pendingToVisit.isNotEmpty()) {
            val current = pendingToVisit.remove()
            val pos = current.first
            val steps = current.second
            if (reachabilities.putIfAbsent(pos, steps) != null) {
                continue
            }
            pos.allNeighbours()
                .filter { allPositions.contains(it) }
                .filter { !rocks.contains(it) }
                .forEach {
                    pendingToVisit.add(Pair(it, steps + 1))
                }
        }
        return reachabilities
    }
}

