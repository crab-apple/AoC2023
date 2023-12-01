package day1

import println
import readInput

fun main() {
    val input = readInput("day1/input")
    solve(input).println()
}

fun solve(input: List<String>): Int = input.sumOf { solveLine(it) }

private fun solveLine(s: String) = s.first { it.isDigit() }.digitToInt() * 10 + s.last { it.isDigit() }.digitToInt()
