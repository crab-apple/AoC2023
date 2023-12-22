package day22

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import kotlin.test.Test

class InputParsingTest {

    @Test
    fun testParseInput() {

        assertThat(
            parseInput(
                """
                        0,0,2~2,0,2
                        1,2,8~3,4,9
                           """.trimIndent()
            ),
            Matchers.containsInAnyOrder(
                Brick(0 to 2, 0 to 0, 2 to 2),
                Brick(1 to 3, 2 to 4, 8 to 9),
            )
        )
    }
}