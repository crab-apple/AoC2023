package day19

import utils.println
import utils.readInputOneString
import utils.split

fun main() {
    val input = readInputOneString("day18/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: String): Long {
    val ruleLines = input.lines().split { it.isEmpty() }[0]
    val partLines = input.lines().split { it.isEmpty() }[1]

    val parts = partLines.map { Part.parse(it) }

    val acceptedIndexes = listOf(0, 2, 4)
    return acceptedIndexes.map { parts[it] }.sumOf { it.addRatings() }
}

fun solvePart2(input: String): Long {
    return 0
}

