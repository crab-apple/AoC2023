package day19

import day19.Category.A
import day19.Category.M
import day19.Category.S
import day19.Category.X
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class NumAcceptedPartsTest {

    @Test
    fun `simple range predicates`() {
        assertThat(
            RangeSetPredicate(
                mapOf(
                    X to IntRange(1, 1),
                    M to IntRange(1, 1),
                    A to IntRange(1, 1),
                    S to IntRange(1, 1),
                )
            ).numAcceptedParts(),
            `is`(1)
        )

        assertThat(
            RangeSetPredicate(
                mapOf(
                    X to IntRange(1, 1),
                    M to IntRange(1, 100),
                    A to IntRange(1, 1),
                    S to IntRange(1, 1),
                )
            ).numAcceptedParts(),
            `is`(100)
        )
    }

    @Test
    fun `and-ing range predicates`() {
        assertThat(
            RangeSetPredicate.parse("x<2")
                .and(RangeSetPredicate.parse("m<2"))
                .and(RangeSetPredicate.parse("a<3"))
                .and(RangeSetPredicate.parse("s>3998"))
                .numAcceptedParts(),
            `is`(4)
        )
    }
}