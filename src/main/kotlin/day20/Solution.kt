package day20

import utils.println
import utils.readInputOneString

fun main() {
    val input = readInputOneString("day20/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: String): Long {
    val network = ModuleNetwork.parse(input)
    repeat(1000) { network.pushButton() }
    return network.lowCount * network.highCount
}

fun solvePart2(input: String): Long {
    return 0
}

