package day7

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import kotlin.test.Test

class HandTest {

    @Test
    fun fiveOfAKind() {
        assertThat(
            Hand("AAAAA").type(),
            `is`(Hand.Type.FIVE_OF_A_KIND)
        )
    }

    @Test
    fun fourOfAKind() {
        assertThat(
            Hand("AA8AA").type(),
            `is`(Hand.Type.FOUR_OF_A_KIND)
        )
    }

    @Test
    fun fullHouse() {
        assertThat(
            Hand("23332").type(),
            `is`(Hand.Type.FULL_HOUSE)
        )
    }

    @Test
    fun threeOfAKind() {
        assertThat(
            Hand("TTT98").type(),
            `is`(Hand.Type.THREE_OF_A_KIND)
        )
    }

    @Test
    fun twoPair() {
        assertThat(
            Hand("23432").type(),
            `is`(Hand.Type.TWO_PAIR)
        )
    }

    @Test
    fun onePair() {
        assertThat(
            Hand("A23A4").type(),
            `is`(Hand.Type.ONE_PAIR)
        )
    }

    @Test
    fun highCard() {
        assertThat(
            Hand("23456").type(),
            `is`(Hand.Type.HIGH_CARD)
        )
    }
}