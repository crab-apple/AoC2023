package day2

import println
import readInput

fun main() {
    val input = readInput("day2/input")
    Part2Solver.solve(input).println()
}

object Part2Solver {

    fun solve(input: List<String>): Int = input.map { Game.parse(it) }
        .sumOf { it.minimumPossibleBag().power() }
}

