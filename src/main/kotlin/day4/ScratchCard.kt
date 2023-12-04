package day4

data class ScratchCard(private val winningNumbers: List<Int>, private val numbersYouHave: Set<Int>) {

    fun numMatches() = winningNumbers.count { numbersYouHave.contains(it) }

    companion object Parser {

        fun parse(s: String): ScratchCard {
            val numbers = s.substringAfter(":")
            val winningNumbers: List<Int> =
                numbers.split("|").first().split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            val numbersYouHave: Set<Int> =
                numbers.split("|").last().split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()
            return ScratchCard(winningNumbers, numbersYouHave)
        }
    }
}