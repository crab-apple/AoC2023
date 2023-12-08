package day8

import println
import readInput

fun main() {
    val input = readInput("day8/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Int {

    val directions = input[0]

    val nodes: Map<String, Pair<String, String>> = input.drop(2)
        .associateBy(
            { it.split("=")[0].trim() },
            {
                it.split("=")[1].trim()
                    .removeSurrounding("(", ")")
                    .split(", ")
                    .let { it[0] to it[1] }
            }
        )

    var currentNode = "AAA"
    var count = 0
    do {
        val direction = directions[count % directions.length]
        currentNode = when (direction) {
            'L' -> nodes[currentNode]!!.first
            'R' -> nodes[currentNode]!!.second
            else -> throw IllegalStateException()
        }
        count++
    } while (currentNode != "ZZZ")

    return count
}

fun solvePart2(input: List<String>): Int {
    return input.size
}
