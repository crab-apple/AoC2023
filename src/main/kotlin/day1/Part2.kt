package day1

import println
import readInput

fun main() {
    val input = readInput("day1/input")
    Part2Solver.solve(input).println()
}

object Part2Solver {
    fun solve(input: List<String>): Int = input.sumOf { solveLine(it) }

    private fun solveLine(s: String): Int {

        val digitTokens = (1..9).toSet()
            .map { Token(it.toString(), it) }

        val wordTokens = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
            .mapIndexed { index, rep -> Token(rep, index + 1) }

        val availableTokens = digitTokens.plus(wordTokens)

        val firstOccurringDigit = availableTokens.filter { s.contains(it.rep) }.minBy { s.indexOf(it.rep) }
        val lastOccurringDigit = availableTokens.filter { s.contains(it.rep) }.maxBy { s.lastIndexOf(it.rep) }

        return firstOccurringDigit.value * 10 + lastOccurringDigit.value
    }

    data class Token(val rep: String, val value: Int)
}
