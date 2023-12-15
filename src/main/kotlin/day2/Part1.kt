package day2

import utils.println
import utils.readInput

fun main() {
    val input = readInput("day2/input")
    Part1Solver.solve(input).println()
}

object Part1Solver {

    fun solve(input: List<String>): Int = input.map { Game.parse(it) }
        .filter { it.bagIsPossible(Cubes(Color.RED to 12, Color.GREEN to 13, Color.BLUE to 14)) }
        .sumOf { it.id }
}

