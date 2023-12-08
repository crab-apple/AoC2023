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

    // Note that the puzzle description doesn't say so, but it turns out that, starting from any node (not just the
    // ones that end with 'A') and following directions from the start of the direction list, the number of steps it
    // takes to reach a node ending with 'Z' is always a multiple of the length of the direction list.

    val map = DesertMap.parse(input)
    val cycleMap = map.nodes.keys.associateWith { map.nextNodeAfterOneCycle(it) }

    var currentNodes = map.nodes.keys.filter { it.endsWith('A') }
    var cycleCount = 0
    do {
        currentNodes = currentNodes.map { cycleMap[it]!! }
        cycleCount++
    } while (!currentNodes.all { it.endsWith('Z') })

    return cycleCount * map.directions.length
}

data class DesertMap(val directions: String, val nodes: Map<String, Pair<String, String>>) {

    fun nextNode(currentNode: String, step: Int): String {
        val direction = directions[step % directions.length]
        return navigate(currentNode, direction)
    }

    fun nextNodeAfterOneCycle(initialNode: String): String {
        return directions.fold(initialNode) { node, direction -> navigate(node, direction) }
    }

    private fun navigate(currentNode: String, direction: Char): String {
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
