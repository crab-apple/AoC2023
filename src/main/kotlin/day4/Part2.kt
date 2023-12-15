package day4

import utils.println
import utils.readInput

fun main() {
    val input = readInput("day4/input")
    Part2Solver.solve(input).println()
}

object Part2Solver {

    fun solve(input: List<String>): Int {

        val cards: List<ScratchCard> = input.map { ScratchCard.parse(it) }

        fun scoreCard(index: Int): Int {
            val card = cards[index]
            return 1 + ((index + 1) until (index + 1 + card.numMatches())).sumOf { scoreCard(it) }
        }

        return input.indices.sumOf { scoreCard(it) }
    }
}

