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

    val lines = input.lines().map { it.toList() }
    val allPositions = lines.indices.flatMap { row -> lines[0].indices.map { Position(row, it) } }.toSet()

    fun valAt(pos: Position) = lines[pos.row.toInt()][pos.col.toInt()]

    val rocks = allPositions.filter { valAt(it) == '#' }.toSet()

    val startingPosition = allPositions.single { valAt(it) == 'S' }

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


    return reachabilities.filter { it.value <= targetSteps && it.value % 2 == targetSteps % 2 }.count().toLong()
}

fun solvePart2(input: String): Long {
    return 0
}

