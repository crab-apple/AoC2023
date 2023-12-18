package utils

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import kotlin.test.Test

class PositionTest {

    @Test
    fun testUntil() {

        assertThat(
            Position(2, 3).until(Position(2, 6)),
            contains(
                Position(2, 4),
                Position(2, 5),
                Position(2, 6),
            )
        )

        assertThat(
            Position(2, 6).until(Position(2, 3)),
            contains(
                Position(2, 5),
                Position(2, 4),
                Position(2, 3),
            )
        )

        assertThat(
            Position( 3,2).until(Position( 6,2)),
            contains(
                Position( 4,2),
                Position( 5,2),
                Position( 6,2),
            )
        )

        assertThat(
            Position( 6,2).until(Position( 3,2)),
            contains(
                Position( 5,2),
                Position( 4,2),
                Position( 3,2),
            )
        )
    }
}