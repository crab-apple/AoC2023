package day21

import utils.println
import utils.readInputOneString

fun main() {
    val input = readInputOneString("day21/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: String): Long {
    return solvePart1(input, 64)
}

fun solvePart1(input: String, targetSteps: Int): Long {
    val plot = Plot(input)
    val startingPosition = plot.allPositions.single { plot.valAt(it) == 'S' }
    return plot.computeReachabilities(startingPosition).countReachable(targetSteps.toLong())
}

fun solvePart2(input: String) = HopefullyEfficientSolver().solve(input, 26501365)

