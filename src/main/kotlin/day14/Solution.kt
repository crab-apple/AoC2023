package day14

import println
import readInput
import rotateClockwise
import rotateCounterClockwise
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

    // Find the cycle length
    val cycleLength: Int
    var cycleLengthCandidate = 1
    while (true) {
        var workingGrid = input
        val results = mutableListOf<Int>()
        for (i in 0..10) {
            workingGrid = cycle(workingGrid, cycleLengthCandidate)
            results.add(score(rotateCounterClockwise(workingGrid)))
        }
        if (results.takeLast(5).distinct().size == 1) {
            cycleLength = cycleLengthCandidate
            break
        }
        cycleLengthCandidate++
    }

    var workingGrid = input
    workingGrid = cycle(workingGrid, 1_000_000_000 % cycleLength)
    workingGrid = cycle(workingGrid, cycleLength * 10)
    return score(rotateCounterClockwise(workingGrid)).toLong()
}

fun cycle(grid: List<String>, times: Int): List<String> {
    var workingGrid = grid
    for (i in 1..times) {
        workingGrid = cycle(workingGrid)
    }
    return workingGrid
}

fun cycle(grid: List<String>): List<String> {
    var workingGrid = grid
    workingGrid = rotateCounterClockwise(workingGrid)
    workingGrid = bringRocksToStart(workingGrid)
    workingGrid = rotateClockwise(workingGrid)
    workingGrid = bringRocksToStart(workingGrid)
    workingGrid = rotateClockwise(workingGrid)
    workingGrid = bringRocksToStart(workingGrid)
    workingGrid = rotateClockwise(workingGrid)
    workingGrid = bringRocksToStart(workingGrid)
    workingGrid = rotateClockwise(workingGrid)
    workingGrid = rotateClockwise(workingGrid)
    return workingGrid
}

fun bringRocksToStartAndScore(s: String): Int {
    return score(bringRocksToStart(s))
}

private fun score(grid: List<String>): Int = grid.sumOf { score(it) }

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

fun bringRocksToStart(grid: List<String>): List<String> {
    return grid.map { bringRocksToStart(it) }
}


