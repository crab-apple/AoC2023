package day7

data class Hand(private val cards: String) {

    enum class Type {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND,
    }

    fun type(): Type {

        val grouped: List<List<Char>> = cards.groupBy { it }.values
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

    companion object {

        fun comparator(): Comparator<Hand> {
            return compareBy<Hand> { it.type() }
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
}
