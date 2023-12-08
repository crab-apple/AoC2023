package day8

import println
import readInput

fun main() {
    val input = readInput("day8/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Int {

    val map = DesertMap.parse(input)

    var currentNode = "AAA"
    var count = 0
    do {
        currentNode = map.nextNode(currentNode, count)
        count++
    } while (currentNode != "ZZZ")

    return count
}

fun solvePart2(input: List<String>): Int {

    val map = DesertMap.parse(input)

    var currentNodes = map.nodes.keys.filter { it.endsWith('A') }
    var count = 0
    do {
        currentNodes = currentNodes.map { map.nextNode(it, count) }
        count++
    } while (!currentNodes.all { it.endsWith('Z') })

    return count
}

data class DesertMap(val directions: String, val nodes: Map<String, Pair<String, String>>) {

    fun nextNode(currentNode: String, step: Int): String {
        val direction = directions[step % directions.length]
        return when (direction) {
            'L' -> nodes[currentNode]!!.first
            'R' -> nodes[currentNode]!!.second
            else -> throw IllegalStateException()
        }
    }

    companion object {

        fun parse(input: List<String>): DesertMap {

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

            return DesertMap(directions, nodes)
        }
    }
}
