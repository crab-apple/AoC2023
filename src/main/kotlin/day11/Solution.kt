package day11

import println
import readInput
import kotlin.math.abs

fun main() {
    val input = readInput("day11/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Long {
    val space = expandSpace(input)
    val galaxies: List<Pair<Int, Int>> = space.flatMapIndexed { numRow, row ->
        row.mapIndexedNotNull { numCol, char ->
            if (char == '#') Pair(
                numCol,
                numRow
            ) else null
        }
    }

    return galaxies.sumOf { a -> galaxies.sumOf { b -> distance(a, b) } } / 2
}

fun solvePart2(input: List<String>): Long {
    return input.size.toLong()
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
