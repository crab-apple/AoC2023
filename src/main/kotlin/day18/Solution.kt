package day18

import utils.println
import utils.readInputOneString

fun main() {
    val input = readInputOneString("day18/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: String): Int {
    val trench = Trench.parse(input)
    return trench.capacity()
}

fun solvePart2(input: String): Int {
    return 0
}
