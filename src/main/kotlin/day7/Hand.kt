package day7

class Hand(private val cards: String) {

    enum class Type {
        FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD
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
}
