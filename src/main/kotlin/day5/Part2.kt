package day5

import utils.println
import utils.readInput
import utils.split

fun main() {
    val input = readInput("day5/input")
    Part2Solver.solve(input).println()
}

object Part2Solver {

    fun solve(input: List<String>): Long {

        val seedRanges = input[0].removePrefix("seeds: ").split(" ").map { it.toLong() }
            .chunked(2)
            .map { it[0] until it[0] + it[1] }

        val maps = input.drop(2).split { it.isBlank() }.map { AlmanacMap.parse(it) }

        val locationRanges = maps.fold(seedRanges) { ranges, map -> ranges.flatMap { map.mapRange(it) } }

        return locationRanges
            .filter { !it.isEmpty() }
            .minOf { it.first }
    }
}

