package day3

import println
import readInput

fun main() {
    val input = readInput("day3/input")
    Part2Solver.solve(input).println()
}

object Part2Solver {

    fun solve(input: List<String>): Int {

        val schematic = Schematic(input)

        val numbers = schematic.numbers()

        return schematic.symbols()
            .filter { it.value == '*' }
            .sumOf { symbol ->
                val adjacentParts = numbers.filter { it.positionIsAdjacent(symbol.position) }
                val isGear = adjacentParts.size == 2
                if (isGear) {
                    adjacentParts[0].value * adjacentParts[1].value
                } else 0
            }
    }
}

