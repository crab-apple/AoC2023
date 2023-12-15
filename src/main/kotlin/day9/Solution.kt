package day9

import utils.println
import utils.readInput

fun main() {
    val input = readInput("day9/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Long {
    val sequences = parseInput(input)
    return sequences.sumOf { it.nextValue() }
}

fun solvePart2(input: List<String>): Long {
    val sequences = parseInput(input)
    return sequences.sumOf { it.previousValue() }
}

private fun parseInput(input: List<String>) =
    input.map { it.split(" ").map { it.toLong() }.let { Sequence(it) } }

