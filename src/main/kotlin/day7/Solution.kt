package day7

import println
import readInput

fun main() {
    val input = readInput("day7/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Int {

    val handsWithBids = input.map {
        val hand = Hand(it.split(" ")[0])
        val bid = it.split(" ")[1].toInt()
        hand to bid
    }

    return handsWithBids.sortedWith { a, b -> Hand.comparator().compare(a.first, b.first) }
        .mapIndexed { index, pair -> (index + 1) * pair.second }
        .sum()
}

fun solvePart2(input: List<String>): Int {
    return input.size
}
