package day7

import day7.Hand.Type

class SimpleHandRanker : HandRanker {

    override fun type(hand: Hand): Type {
        val grouped: List<List<Char>> = hand.cards.groupBy { it }.values
            .sortedByDescending { it.size }

        if (grouped[0].size == 5) {
            return Type.FIVE_OF_A_KIND
        }

        if (grouped[0].size == 4) {
            return Type.FOUR_OF_A_KIND
        }

        if (grouped[0].size == 3 && grouped[1].size == 2) {
            return Type.FULL_HOUSE
        }

        if (grouped[0].size == 3) {
            return Type.THREE_OF_A_KIND
        }

        if (grouped[0].size == 2 && grouped[1].size == 2) {
            return Type.TWO_PAIR
        }

        if (grouped[0].size == 2) {
            return Type.ONE_PAIR
        }

        return Type.HIGH_CARD
    }

    override fun comparator(): Comparator<Hand> {
        return compareBy<Hand> { type(it) }
            .thenComparing({ it.cards }, this::lexicographic)
    }

    private fun lexicographic(a: String, b: String): Int {
        val order = "23456789TJQKA"
        val valA = order.indexOf(a[0])
        val valB = order.indexOf(b[0])
        if (valA == valB) {
            return lexicographic(a.drop(1), b.drop(1))
        }
        return valA - valB
    }
}