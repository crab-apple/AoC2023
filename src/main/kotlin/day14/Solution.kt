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
    return transpose(input).sumOf { bringRocksToStartAndScore(it) }.toLong()
}

fun solvePart2(input: List<String>): Long {
    return input.size.toLong()
}

fun bringRocksToStartAndScore(s: String): Int {
    return score(bringRocksToStart(s))
}

private fun score(s: String): Int {
    return s.mapIndexed { index, ch -> if (ch == 'O') s.length - index else 0 }.sum()
}

fun bringRocksToStart(s: String): String {
    return s.mapIndexedNotNull { index, c -> if (c == '#') index else null }
        .plus(-1)
        .sorted().joinToString("") { stop ->
            val sectionUntilNextStop = s.substring(stop + 1).substringBefore('#')
            "#" + sectionUntilNextStop.toList().sortedDescending().joinToString("")
        }
        .drop(1)
}


