package day7

import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals

class SolutionTest {

    private val exampleInput = """
         32T3K 765
         T55J5 684
         KK677 28
         KTJJT 220
         QQQJA 483
""".trimIndent()

    @Test
    @Disabled
    fun testPart1() {
        assertEquals(
            6440,
            solvePart1(exampleInput.lines())
        )
    }
}

