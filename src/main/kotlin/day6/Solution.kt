package day6

import println
import readInput

fun main() {
    val input = readInput("day6/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Long {
    val races = Race.parseRaceTable(input)
    return races.map { it.numWaysToBeatRecord() }.reduce { a, b -> a * b }
}

fun solvePart2(input: List<String>): Long {
    val races = Race.parseRaceTable(input.map { it.filter { ch -> ch != ' ' } })
    return races.map { it.numWaysToBeatRecord() }.reduce { a, b -> a * b }
}


