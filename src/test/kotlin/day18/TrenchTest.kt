package day18

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import utils.Laterality.LEFT
import utils.Laterality.RIGHT

class TrenchTest {

    @Test
    fun testParse() {
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
        assertThat(
            trench.corners,
            contains(
                Corner(0, 2, RIGHT),
                Corner(2, 2, LEFT),
                Corner(2, 4, LEFT),
                Corner(0, 4, RIGHT),
                Corner(0, 6, RIGHT),
                Corner(4, 6, RIGHT),
                Corner(4, 0, RIGHT),
                Corner(0, 0, RIGHT),
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
        )

        assertThat(
            trench.drawEdges(), `is`(
                """
            @######
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

    @Test
    fun `simplify trench, convex with limit before`() {
        val trench = Trench.parse(
            """
        R 2
        D 2
        R 2
        D 2
        L 4
        U 4
    """.trimIndent()
        )

        assertThat(
            trench.simplify().first.drawEdges(),
            `is`(
                """
                @##
                #.#
                #.#
                #.#
                ###
            """.trimIndent()
            )
        )

        assertThat(
            trench.simplify().second,
            `is`(-6)
        )
    }

    @Test
    fun `simplify trench, convex with limit after`() {

        val trench = Trench.parse(
            """
        R 4
        D 2
        L 2
        D 2
        L 2
        U 4
    """.trimIndent()
        )

        assertThat(
            trench.simplify().first.drawEdges(),
            `is`(
                """
                @##
                #.#
                #.#
                #.#
                ###
            """.trimIndent()
            )
        )

        assertThat(
            trench.simplify().second,
            `is`(-6)
        )
    }

    @Test
    fun `simplify trench, convex with limit before and after`() {

        val trench = Trench.parse(
            """
        R 2
        U 2
        R 2
        D 2
        R 2
        D 2
        L 6
        U 2
    """.trimIndent()
        )

        assertThat(
            trench.simplify().first.drawEdges(),
            `is`(
                """
                @######
                #.....#
                #######
            """.trimIndent()
            )
        )

        assertThat(
            trench.simplify().second,
            `is`(-6)
        )
    }

    @Test
    fun `simplify trench, concave`() {

        val trench = Trench.parse(
            """
        R 2
        D 3
        R 2
        U 5
        R 2
        D 7
        L 6
        U 7
    """.trimIndent()
        )

        println(trench.drawEdges())

        assertThat(
            trench.simplify().first.drawEdges(),
            `is`(
                """
                ....###
                ....#.#
                @####.#
                #.....#
                #.....#
                #.....#
                #.....#
                #######
            """.trimIndent()
            )
        )

        assertThat(
            trench.simplify().second,
            `is`(3)
        )
    }
}