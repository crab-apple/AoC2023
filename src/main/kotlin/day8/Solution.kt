package day8

import utils.println
import utils.readInput

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

fun solvePart2(input: List<String>): Long {

    /*
         Note that the puzzle description doesn't say so, but it turns out that:

         - starting from any node (not just the ones that end with 'A') and following directions from the start of the
           direction list, the number of steps it takes to reach a node ending with 'Z' is always a multiple of the length
           of the direction list.

         - the paths that are followed starting from the nodes that end with 'A' do not intersect each other. So the map
           is actually N distinct, non-overlapping maps, where N is the number of starting nodes.

         - within each submap, the number of direction cycles necessary to go from A to Z is the same as the number
           necessary to go from Z to Z
    */

    val map = DesertMap.parse(input)
    val cycleMap = map.nodes.keys.associateWith { map.nextNodeAfterOneCycle(it) }
    val startingNodes = map.nodes.keys.filter { it.endsWith('A') }

    val cycleLengths: List<Long> =
        startingNodes.map { startingNode -> countUntil(startingNode, { cycleMap[it]!! }, { it.endsWith('Z') }) }
            .map { it.toLong() }

    return cycleLengths.reduce { a, b -> a * b } * map.directions.length
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

fun <T> countUntil(initial: T, transform: (T) -> T, endCondition: (T) -> Boolean): Int {
    var current = initial;
    var count = 0
    do {
        current = transform(current)
        count++
    } while (!endCondition(current))
    return count;
}
