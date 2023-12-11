package day11

import println
import readInput
import kotlin.math.abs

fun main() {
    val input = readInput("day11/input")
    solvePart1(input).println()
    solvePart2(input, 1_000_000).println()
}

fun solvePart1(input: List<String>): Long {
    return solvePart2(input, 2)
}

fun solvePart2(input: List<String>, expansionFactor: Int): Long {

    var galaxies: List<Pair<Int, Int>> = input.flatMapIndexed { numRow, row ->
        row.mapIndexedNotNull { numCol, char ->
            if (char == '#') Pair(
                numCol,
                numRow
            ) else null
        }
    }

    val emptyRows = input.indices.filter { !input[it].contains('#') }
    val emptyColumns = input[0].indices.filter { col -> input.none { it[col] == '#' } }

    emptyRows.sortedDescending().forEach { emptyRowNum ->
        galaxies = galaxies.map {
            if (it.second > emptyRowNum) Pair(it.first, it.second + expansionFactor - 1) else it
        }
    }
    emptyColumns.sortedDescending().forEach { emptyColNum ->
        galaxies = galaxies.map {
            if (it.first > emptyColNum) Pair(it.first + expansionFactor - 1, it.second) else it
        }
    }

    return galaxies.sumOf { a -> galaxies.sumOf { b -> distance(a, b) } } / 2
}

fun distance(galaxyA: Pair<Int, Int>, galaxyB: Pair<Int, Int>): Long {
    return abs(galaxyA.first - galaxyB.first) + abs(galaxyA.second - galaxyB.second).toLong()
}

fun expandSpace(lines: List<String>): List<String> {
    return expandRows(expandColumns(lines))
}

private fun expandColumns(lines: List<String>): List<String> {
    return transpose(expandRows(transpose(lines)))
}

private fun expandRows(lines: List<String>): List<String> {
    return lines.flatMap { if (!it.contains("#")) listOf(it, it) else listOf(it) }
}

private fun transpose(lines: List<String>): List<String> {
    return lines[0].indices.map { index -> lines.map { it[index] } }.map { it.joinToString("") }
}
