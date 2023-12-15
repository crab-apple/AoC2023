package utils

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import utils.MutableGrid.Position

class MutableGridTest {

    @Test
    fun testToString() {
        val string = """
            ABC
            DEF
        """.trimIndent()
        val grid = MutableGrid.of(string)
        assertThat(grid.toString(), `is`(string))
    }

    @Test
    fun testDimensions() {
        val string = """
            ABC
            DEF
        """.trimIndent()
        val grid = MutableGrid.of(string)
        assertThat(grid.numCols(), `is`(3))
        assertThat(grid.numRows(), `is`(2))
    }

    @Test
    fun testGetValues() {
        val grid = MutableGrid.of(
            """
                ABC
                DEF
            """.trimIndent()
        )
        assertThat(grid.get(Position(0, 0)), `is`('D'))
        assertThat(grid.get(Position(1, 0)), `is`('E'))
        assertThat(grid.get(Position(2, 0)), `is`('F'))
        assertThat(grid.get(Position(0, 1)), `is`('A'))
        assertThat(grid.get(Position(1, 1)), `is`('B'))
        assertThat(grid.get(Position(2, 1)), `is`('C'))
    }

    @Test
    fun testTranspose() {
        val grid = MutableGrid.of(
            """
            AB
            CD
            EF
        """
        )

        grid.transpose()


        assertThat(grid.get(Position(0, 0)), `is`('E'))
        assertThat(grid.get(Position(1, 0)), `is`('C'))
        assertThat(grid.get(Position(2, 0)), `is`('A'))
        assertThat(grid.get(Position(0, 1)), `is`('F'))
        assertThat(grid.get(Position(1, 1)), `is`('D'))
        assertThat(grid.get(Position(2, 1)), `is`('B'))

        assertThat(
            grid.toString(),
            `is`(
                """
                FDB
                ECA
            """.trimIndent()
            )
        )
    }

    @Test
    fun testRotateClockwise() {

        val grid = MutableGrid.of(
            """
            AB
            CD
            EF
        """
        )


        grid.rotateClockwise()


        assertThat(grid.get(Position(0, 0)), `is`('F'))
        assertThat(grid.get(Position(1, 0)), `is`('D'))
        assertThat(grid.get(Position(0, 1)), `is`('E'))
        assertThat(
            grid.toString(),
            `is`(
                """
                ECA
                FDB
            """.trimIndent()
            )
        )

        grid.rotateClockwise()

        assertThat(grid.get(Position(0, 0)), `is`('B'))
        assertThat(grid.get(Position(1, 0)), `is`('A'))
        assertThat(grid.get(Position(0, 1)), `is`('D'))
        assertThat(
            grid.toString(),
            `is`(
                """
                FE
                DC
                BA
            """.trimIndent()
            )
        )
        grid.rotateClockwise()
        assertThat(
            grid.toString(),
            `is`(
                """
                BDF
                ACE
            """.trimIndent()
            )
        )
        grid.rotateClockwise()
        assertThat(
            grid.toString(),
            `is`(
                """
            AB
            CD
            EF
            """.trimIndent()
            )
        )
    }

    @Test
    fun testRotateCounterclockwise() {

        val grid = MutableGrid.of(
            """
            AB
            CD
            EF
        """
        )
        grid.rotateCounterClockwise()
        assertThat(
            grid.toString(),
            `is`(
                """
                BDF
                ACE
            """.trimIndent()
            )
        )
    }

    @Test
    fun testTransposeThenRotate() {
        val grid = MutableGrid.of(
            """
            AB
            CD
            EF
        """
        )

        grid.transpose()
        grid.rotateClockwise()

        assertThat(
            grid.toString(),
            `is`(
                """
                EF
                CD
                AB
            """.trimIndent()
            )
        )
    }

    @Test
    fun testRotateThenTranspose() {
        val grid = MutableGrid.of(
            """
            AB
            CD
            EF
        """
        )

        grid.rotateClockwise()
        grid.transpose()

        assertThat(
            grid.toString(),
            `is`(
                """
                BA
                DC
                FE
            """.trimIndent()
            )
        )
    }

    @Test
    fun testTransformAndSet() {
        val grid = MutableGrid.of(
            """
            AB
            CD
        """
        )

        grid.rotateClockwise()
        grid.set(Position(1, 0), '#')

        assertThat(
            grid.toString(),
            `is`(
                """
                CA
                D#
            """.trimIndent()
            )
        )
    }

    @Test
    fun `test access and iterate columns`() {

        val grid = MutableGrid.of(
            """
            ABC
            DEF
        """
        )

        assertThat(grid.columns().size(), `is`(3))
        assertThat(grid.columns()[0][0], `is`('D'))
        assertThat(grid.columns()[0][1], `is`('A'))
        assertThat(grid.columns()[1][0], `is`('E'))
        assertThat(grid.columns()[1][1], `is`('B'))
        assertThat(grid.columns()[2][0], `is`('F'))
        assertThat(grid.columns()[2][1], `is`('C'))

        assertThat(
            grid.columns().joinToString("|") { it.joinToString("") },
            `is`("DA|EB|FC")
        )

        grid.rotateClockwise()
        assertThat(grid.columns().size(), `is`(2))
        assertThat(grid.columns()[0][0], `is`('F'))
        assertThat(grid.columns()[0][1], `is`('E'))
        assertThat(grid.columns()[0][2], `is`('D'))
        assertThat(grid.columns()[1][0], `is`('C'))
        assertThat(grid.columns()[1][1], `is`('B'))
        assertThat(grid.columns()[1][2], `is`('A'))

        assertThat(
            grid.columns().joinToString("|") { it.joinToString("") },
            `is`("FED|CBA")
        )
    }

    @Test
    fun `test set values through columns`() {

        val grid = MutableGrid.of(
            """
            ...
            ...
            ...
        """
        )

        grid.columns()[0][0] = 'A'
        grid.columns()[1][0] = 'a'

        grid.rotateClockwise()
        grid.columns()[0][0] = 'B'
        grid.columns()[1][0] = 'b'

        grid.rotateClockwise()
        grid.columns()[0][0] = 'C'
        grid.columns()[1][0] = 'c'

        grid.rotateClockwise()
        grid.columns()[0][0] = 'D'
        grid.columns()[1][0] = 'd'

        grid.rotateClockwise()

        grid.columns()[1][1] = '@'

        assertThat(

            grid.toString(),
            `is`(
                """
                DcC
                d@b
                AaB
            """.trimIndent()
            )
        )
    }

    @Test
    fun `test access and iterate rows`() {

        val grid = MutableGrid.of(
            """
            ABC
            DEF
        """
        )

        assertThat(grid.rows().size(), `is`(2))
        assertThat(grid.rows()[0][0], `is`('D'))
        assertThat(grid.rows()[0][1], `is`('E'))
        assertThat(grid.rows()[0][2], `is`('F'))
        assertThat(grid.rows()[1][0], `is`('A'))
        assertThat(grid.rows()[1][1], `is`('B'))
        assertThat(grid.rows()[1][2], `is`('C'))

        assertThat(
            grid.rows().joinToString("|") { it.joinToString("") },
            `is`("DEF|ABC")
        )

        grid.rotateClockwise()
        assertThat(grid.rows().size(), `is`(3))
        assertThat(grid.rows()[0][0], `is`('F'))
        assertThat(grid.rows()[0][1], `is`('C'))
        assertThat(grid.rows()[1][0], `is`('E'))
        assertThat(grid.rows()[1][1], `is`('B'))
        assertThat(grid.rows()[2][0], `is`('D'))
        assertThat(grid.rows()[2][1], `is`('A'))
    }

    @Test
    fun `test set values through rows`() {

        val grid = MutableGrid.of(
            """
            ...
            ...
            ...
        """
        )

        grid.rows()[0][0] = 'A'
        grid.rows()[1][0] = 'a'

        grid.rotateClockwise()
        grid.rows()[0][0] = 'B'
        grid.rows()[1][0] = 'b'

        grid.rotateClockwise()
        grid.rows()[0][0] = 'C'
        grid.rows()[1][0] = 'c'

        grid.rotateClockwise()
        grid.rows()[0][0] = 'D'
        grid.rows()[1][0] = 'd'

        grid.rotateClockwise()

        grid.rows()[1][1] = '@'

        assertThat(

            grid.toString(),
            `is`(
                """
                DdC
                a@c
                AbB
            """.trimIndent()
            )
        )
    }

    @Test
    fun handlesManyRotations() {

        val grid = MutableGrid.of(
            """
            ABC
            DEF
        """
        )

        for (i in 1..<100_000) {
            grid.rotateClockwise()
            val str = grid.toString()
            if (i % 4 == 0) {
                assertThat(
                    str,
                    `is`(
                        """
                            ABC
                            DEF
                        """.trimIndent()
                    )
                )
            }
        }
    }
}
