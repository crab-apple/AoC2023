package day7

import day7.Hand.Type

class HandRankerWithJokers : HandRanker {

    override fun type(hand: Hand): Type {

        val numJokers = hand.cards.count { it == 'J' }
        val nonJokers = hand.cards.filter { it != 'J' }

        val sizes: List<Int> = nonJokers.groupBy { it }.values
            .map { it.size }
            .sortedByDescending { it }

        val firstGroup = (sizes.getOrNull(0) ?: 0) + numJokers
        val secondGroup = sizes.getOrNull(1) ?: 0

        if (firstGroup == 5) {
            return Type.FIVE_OF_A_KIND
        }

        if (firstGroup == 4) {
            return Type.FOUR_OF_A_KIND
        }

        if (firstGroup == 3 && secondGroup == 2) {
            return Type.FULL_HOUSE
        }

        if (firstGroup == 3) {
            return Type.THREE_OF_A_KIND
        }

        if (firstGroup == 2 && secondGroup == 2) {
            return Type.TWO_PAIR
        }

        if (firstGroup == 2) {
            return Type.ONE_PAIR
        }

        return Type.HIGH_CARD
    }

    override fun comparator(): Comparator<Hand> {
        return compareBy<Hand> { type(it) }
            .thenComparing({ it.cards }, this::lexicographic)
    }

    private fun lexicographic(a: String, b: String): Int {
        val order = "J23456789TQKA"
        val valA = order.indexOf(a[0])
        val valB = order.indexOf(b[0])
        if (valA == valB) {
            return lexicographic(a.drop(1), b.drop(1))
        }
        return valA - valB
    }
}