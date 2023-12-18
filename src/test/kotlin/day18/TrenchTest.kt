package day18

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import utils.Position

class TrenchTest {

    @Test
    fun testParse() {
        val trench = Trench.parse(
            """
            R 1 (#70c710)
            D 2 (#0dc571)
            L 1 (#5713f0)
            U 2 (#d2c081)
        """.trimIndent()
        )
        assertThat(
            trench.edges, containsInAnyOrder(
                Position(0, 0),
                Position(0, 1),
                Position(1, 0),
                Position(1, 1),
                Position(2, 0),
                Position(2, 1),
            )
        )
    }

    @Test
    fun testDrawEdges() {
        val trench = Trench.parse(
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
        );

        assertThat(
            trench.drawEdges(), `is`(
                """
            #######
            #.....#
            ###...#
            ..#...#
            ..#...#
            ###.###
            #...#..
            ##..###
            .#....#
            .######
            """.trimIndent()
            )
        )
    }

    @Test
    fun `test capacity, no gaps across any row`() {
        val trench = Trench.parse(
            """
        R 2 (#70c710)
        D 2 (#70c710)
        R 2 (#70c710)
        D 2 (#70c710)
        L 4 (#70c710)
        U 4 (#70c710)
    """.trimIndent()
        )

        assertThat(trench.capacity(), `is`(21))
    }

    @Test
    fun `test capacity, with gaps across rows`() {
        val trench = Trench.parse(
            """
        R 2 (#70c710)
        D 2 (#70c710)
        R 2 (#70c710)
        U 2 (#70c710)
        R 2 (#70c710)
        D 4 (#70c710)
        L 6 (#70c710)
        U 4 (#70c710)
    """.trimIndent()
        )

        assertThat(trench.capacity(), `is`(33))
    }
}