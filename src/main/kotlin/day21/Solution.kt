package day21

import utils.Position
import utils.println
import utils.readInputOneString

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
    val allPositions = lines.indices.flatMap { row -> lines[0].indices.map { Position(row, it) } }

    fun valAt(pos: Position) = lines[pos.row.toInt()][pos.col.toInt()]

    val rocks = allPositions.filter { valAt(it) == '#' }

    val startingPosition = allPositions.single { valAt(it) == 'S' }
    val reachabilities = allPositions.associateWith { mutableSetOf<Int>() }

    val pendingToEvaluate = mutableSetOf<Pair<Position, Int>>()
    pendingToEvaluate.add(Pair(startingPosition, 0))

    while (pendingToEvaluate.isNotEmpty()) {
        val current = pendingToEvaluate.first()
        pendingToEvaluate.remove(current)
        val pos = current.first
        val steps = current.second
        if (steps <= targetSteps && reachabilities[pos]!!.add(steps)) {
            pos.allNeighbours()
                .filter { allPositions.contains(it) }
                .filter { !rocks.contains(it) }
                .forEach { pendingToEvaluate.add(Pair(it, steps + 1)) }
        }
    }

    return reachabilities.filter { it.value.contains(targetSteps) }.count().toLong()
}

fun solvePart2(input: String): Long {
    return 0
}

