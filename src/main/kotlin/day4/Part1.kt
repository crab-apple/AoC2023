package day4

import utils.println
import utils.readInput
import kotlin.math.pow

fun main() {
    val input = readInput("day4/input")
    Part1Solver.solve(input).println()
}

object Part1Solver {

    fun solve(input: List<String>) = input.sumOf { lineScore(it) }

    private fun lineScore(it: String): Int {
        val scratchCard = ScratchCard.parse(it)

        if (scratchCard.numMatches() == 0) {
            return 0
        }
        return 2.0.pow(scratchCard.numMatches() - 1).toInt()
    }
}

