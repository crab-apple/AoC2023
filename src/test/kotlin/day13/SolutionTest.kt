package day13

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SolutionTest {

    @Test
    fun testPart1() {
        assertEquals(
            405,
            solvePart1(
                """
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
            )
        )
    }

    @Test
    fun testMirrorsAt() {
        val input = listOf("foo", "bar", "baz", "baz", "bar")
        assertFalse(mirrorsAt(input, 1))
        assertFalse(mirrorsAt(input, 2))
        assertTrue(mirrorsAt(input, 3))
        assertFalse(mirrorsAt(input, 4))
    }
}

