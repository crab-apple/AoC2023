package day9

import println
import readInput

fun main() {
    val input = readInput("day9/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Long {

    val sequences = input.map { it.split(" ").map { it.toLong() }.let { Sequence(it) } }

    return sequences.sumOf { it.nextValue() }
}

fun solvePart2(input: List<String>): Int {
    return input.size
}

