package day18

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import utils.Direction.EAST
import utils.Direction.NORTH
import utils.Direction.SOUTH
import utils.Direction.WEST
import kotlin.test.Test

class InputLineTest {

    @Test
    fun testParseHexadecimal() {
        assertThat(
            InputLine.parseHexadecimal(
                """
        R 6 (#70c710)
        D 5 (#0dc571)
        L 2 (#5713f0)
        D 2 (#d2c081)
        R 2 (#59c680)
        D 2 (#411b91)
        L 5 (#8ceee2)
        U 2 (#caa173)
        L 1 (#1b58a2)
        U 2 (#caa171)
        R 2 (#7807d2)
        U 3 (#a77fa3)
        L 2 (#015232)
        U 2 (#7a21e3)
    """.trimIndent()
            ),
            Matchers.contains(
                InputLine(EAST, 461937),
                InputLine(SOUTH, 56407),
                InputLine(EAST, 356671),
                InputLine(SOUTH, 863240),
                InputLine(EAST, 367720),
                InputLine(SOUTH, 266681),
                InputLine(WEST, 577262),
                InputLine(NORTH, 829975),
                InputLine(WEST, 112010),
                InputLine(SOUTH, 829975),
                InputLine(WEST, 491645),
                InputLine(NORTH, 686074),
                InputLine(WEST, 5411),
                InputLine(NORTH, 500254),
            )
        )
    }
}