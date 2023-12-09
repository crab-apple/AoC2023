package day9

import kotlin.test.Test
import kotlin.test.assertEquals

class SolutionTest {

    @Test
    fun testPart1() {
        assertEquals(
            114,
            solvePart1(
                """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45
           """.trimIndent().lines()
            )
        )
    }
}

