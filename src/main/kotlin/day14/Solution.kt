package day14

import println
import readInput
import transpose

fun main() {
    val input = readInput("day14/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Long {
    return transpose(input).sumOf { bringRocksToStart(it) }.toLong()
}

fun solvePart2(input: List<String>): Long {
    return input.size.toLong()
}

fun bringRocksToStart(s: String): Int {
    val stops = s.mapIndexedNotNull { index, c -> if (c == '#') index else null }
        .plus(-1)

    return stops.sumOf { stop ->
        val count = s.substring(stop + 1).substringBefore('#').count { it == 'O' }
        (s.length - (stop + 1)) * count - ((count * (count - 1)) / 2)
    }
}


