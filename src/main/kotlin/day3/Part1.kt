package day3

import println
import readInput

fun main() {
    val input = readInput("day3/input")
    Part1Solver.solve(input).println()
}

object Part1Solver {
    fun solve(input: List<String>): Int {
        val schematic = Schematic(input)
        val engineParts = schematic.numbers()
            .filter { number -> schematic.symbols().any { number.positionIsAdjacent(it.position) } }
        return engineParts.sumOf { it.value }
    }
}

