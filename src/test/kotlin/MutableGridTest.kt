import MutableGrid.Position
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

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
}
