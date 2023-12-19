package day19

import utils.println
import utils.readInputOneString
import utils.split

fun main() {
    val input = readInputOneString("day19/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: String): Long {
    val ruleLines = input.lines().split { it.isEmpty() }[0]
    val partLines = input.lines().split { it.isEmpty() }[1]

    val ruleSet = RuleSet(ruleLines.map { Rule.parse(it) })
    val parts = partLines.map { Part.parse(it) }

    println(ruleSet)

    return parts.filter { ruleSet.accepted(it) }.sumOf { it.addRatings() }
}

fun solvePart2(input: String): Long {
    return 0
}

