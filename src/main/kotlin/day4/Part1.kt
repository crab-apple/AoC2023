package day4

import println
import readInput
import kotlin.math.pow

fun main() {
    val input = readInput("day4/input")
    Part1Solver.solve(input).println()
}

object Part1Solver {

    fun solve(input: List<String>) = input.sumOf { lineScore(it) }

    private fun lineScore(it: String): Int {
        val numbers = it.substringAfter(":")
        val winningNumbers: List<Int> =
            numbers.split("|").first().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val numbersIHave: Set<Int> =
            numbers.split("|").last().split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()

        val numMatches = winningNumbers.count { numbersIHave.contains(it) }
        if (numMatches == 0) {
            return 0
        }
        return 2.0.pow(numMatches - 1).toInt()
    }
}

