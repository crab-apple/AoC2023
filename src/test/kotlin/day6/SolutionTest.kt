package day6

import kotlin.test.Test
import kotlin.test.assertEquals

class SolutionTest {

    private val exampleInput = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()

    @Test
    fun testPart1() {
        assertEquals(
            288,
            solvePart1(exampleInput.lines())
        )
    }

    @Test
    fun testPart2() {
        assertEquals(
            71503,
            solvePart2(exampleInput.lines())
        )
    }
}

