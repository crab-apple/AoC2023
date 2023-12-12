package day12

import println
import readInput

fun main() {
    val input = readInput("day12/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Long {
    return input.sumOf { line ->
        val str = line.split(" ").first()
        val groups = line.split(" ").last().split(",").map { it.toInt() }
        countArrangements(str, groups).toLong()
    }
}

fun solvePart2(input: List<String>): Long {
    return input.size.toLong()
}

fun findPossibleStrings(s: String): List<String> {
    val accumulator = mutableListOf<String>()
    findPossibleStrings(s, accumulator)
    return accumulator
}

fun findPossibleStrings(s: String, accumulator: MutableList<String>) {
    if (!s.contains('?')) {
        accumulator.add(s)
        return
    }
    findPossibleStrings(s.replaceFirst('?', '.'), accumulator)
    findPossibleStrings(s.replaceFirst('?', '#'), accumulator)
}

fun countArrangements(input: String, groups: List<Int>): Int {
    return findPossibleStrings(input).count { groupsOf(it) == groups }
}

fun groupsOf(input: String): List<Int> {
    return input.split(".")
        .filter { it.isNotBlank() }
        .map { it.length }
}
