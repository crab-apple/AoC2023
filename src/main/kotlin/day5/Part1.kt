package day5

import utils.println
import utils.readInput
import utils.split

fun main() {
    val input = readInput("day5/input")
    Part1Solver.solve(input).println()
}

object Part1Solver {

    fun solve(input: List<String>): Long {

        val seeds = input[0].removePrefix("seeds: ").split(" ").map { it.toLong() }
        println(seeds)

        val mapInputs: List<List<String>> = input.drop(2).split { it.isBlank() }

        val maps = mapInputs.map { AlmanacMap.parse(it) }

        fun seedToLocation(seed: Long) = maps.fold(seed) { value, almanacMap -> almanacMap.map(value) }

        val locations = seeds.map { seedToLocation(it) }

        return locations.min()
    }
}

