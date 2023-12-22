package day22

import utils.println
import utils.readInputOneString

fun main() {
    val input = readInputOneString("day22/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: String): Long {
    return BrickStack(parseInput(input)).numDisintegrable()
}

fun solvePart2(input: String): Long {
    return BrickStack(parseInput(input)).countFalls().toLong()
}

