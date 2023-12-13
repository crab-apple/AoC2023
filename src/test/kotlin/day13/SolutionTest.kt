package day13

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SolutionTest {

    val exampleInput = """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                #.#.##.#.

                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#
 """.trimIndent().lines()

    @Test
    fun testPart1() {
        assertEquals(405, solvePart1(exampleInput))
    }

    @Test
    fun testPart2() {
        assertEquals(400, solvePart2(exampleInput))
    }

    @Test
    fun testMirrorsAt() {
        val input = listOf("foo", "bar", "baz", "baz", "bar")
        assertFalse(mirrorsAt(input, 1))
        assertFalse(mirrorsAt(input, 2))
        assertTrue(mirrorsAt(input, 3))
        assertFalse(mirrorsAt(input, 4))
    }

    @Test
    fun testScore() {
        assertThat(
            score(
                """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.
            """.trimIndent().lines()
            ), `is`(5)
        )

        assertThat(
            score(
                """
            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#
            """.trimIndent().lines()
            ), `is`(400)
        )
    }

    @Test
    fun testScoreWithSmudge() {
        assertThat(
            scoreWithSmudge(
                """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.
            """.trimIndent().lines()
            ), `is`(300)
        )

        assertThat(
            scoreWithSmudge(
                """
            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#
            """.trimIndent().lines()
            ), `is`(100)
        )
    }

    @Test
    fun testSmudge() {
        val pattern = listOf(
            "##",
            ".."
        )
        assertThat(smudge(pattern, 0), `is`(listOf(".#", "..")))
        assertThat(smudge(pattern, 1), `is`(listOf("#.", "..")))
        assertThat(smudge(pattern, 2), `is`(listOf("##", "#.")))
        assertThat(smudge(pattern, 3), `is`(listOf("##", ".#")))
    }
}

