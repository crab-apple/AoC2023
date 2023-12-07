package day7

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import kotlin.test.Test

class HandRankerWithJokersTest {

    private val ranker = HandRankerWithJokers()

    @Test
    fun fiveOfAKind() {
        assertThat(
            ranker.type(Hand("AAAAA")),
            `is`(Hand.Type.FIVE_OF_A_KIND)
        )
    }

    @Test
    fun fourOfAKind() {
        assertThat(
            ranker.type(Hand("AA8AA")),
            `is`(Hand.Type.FOUR_OF_A_KIND)
        )
    }

    @Test
    fun fullHouse() {
        assertThat(
            ranker.type(Hand("23332")),
            `is`(Hand.Type.FULL_HOUSE)
        )
    }

    @Test
    fun threeOfAKind() {
        assertThat(
            ranker.type(Hand("TTT98")),
            `is`(Hand.Type.THREE_OF_A_KIND)
        )
    }

    @Test
    fun twoPair() {
        assertThat(
            ranker.type(Hand("23432")),
            `is`(Hand.Type.TWO_PAIR)
        )
    }

    @Test
    fun onePair() {
        assertThat(
            ranker.type(Hand("A23A4")),
            `is`(Hand.Type.ONE_PAIR)
        )
    }

    @Test
    fun highCard() {
        assertThat(
            ranker.type(Hand("23456")),
            `is`(Hand.Type.HIGH_CARD)
        )
    }

    @Test
    fun fiveOfAKindWithJokers() {
        listOf(
            Hand("AAJAA"),
            Hand("AAJJA"),
            Hand("AJJJA"),
            Hand("JJJJA"),
            Hand("JJJJJ"),
        ).forEach {
            assertThat(
                ranker.type(it),
                `is`(Hand.Type.FIVE_OF_A_KIND)
            )
        }
    }

    @Test
    fun fourOfAKindWithJokers() {
        listOf(
            Hand("AKJAA"),
            Hand("AKJJA"),
            Hand("JKJJA"),
        ).forEach {
            assertThat(
                ranker.type(it),
                `is`(Hand.Type.FOUR_OF_A_KIND)
            )
        }
    }

    @Test
    fun fullHouseWithJokers() {
        listOf(
            Hand("AKJKA"),
        ).forEach {
            assertThat(
                ranker.type(it),
                `is`(Hand.Type.FULL_HOUSE)
            )
        }
    }

    @Test
    fun threeOfAKindWithJokers() {
        listOf(
            Hand("AKJQA"),
            Hand("JKJQA"),
        ).forEach {
            assertThat(
                ranker.type(it),
                `is`(Hand.Type.THREE_OF_A_KIND)
            )
        }
    }

    @Test
    fun onePairWithJokers() {
        listOf(
            Hand("AKJQ9"),
        ).forEach {
            assertThat(
                ranker.type(it),
                `is`(Hand.Type.ONE_PAIR)
            )
        }
    }

    @Test
    fun rankDifferentTypes() {
        assertThat(
            listOf(Hand("KK677"), Hand("32T3K")).sortedWith(ranker.comparator()),
            `is`(listOf(Hand("32T3K"), Hand("KK677")))
        )
    }

    @Test
    fun rankSameType() {
        assertThat(
            listOf(Hand("AAKAA"), Hand("AATAA"), Hand("AA3AA")).sortedWith(ranker.comparator()),
            `is`(listOf(Hand("AA3AA"), Hand("AATAA"), Hand("AAKAA")))
        )
    }

    @Test
    fun rankWithJokers() {
        assertThat(
            listOf(Hand("JKKK2"), Hand("TTTT2")).sortedWith(ranker.comparator()),
            `is`(listOf(Hand("JKKK2"), Hand("TTTT2")))
        )
    }
}