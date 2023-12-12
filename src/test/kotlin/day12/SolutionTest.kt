package day12

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    fun `find all possibilities given string without question marks`() {
        assertThat(
            findPossibleStrings(".##..##...##."),
            containsInAnyOrder(
                ".##..##...##."
            )
        )
    }

    @Test
    fun `find all possibilities given string with question marks`() {
        assertThat(
            findPossibleStrings(".?..??...##."),
            containsInAnyOrder(
                ".........##.",
                ".....#...##.",
                "....#....##.",
                "....##...##.",
                ".#.......##.",
                ".#...#...##.",
                ".#..#....##.",
                ".#..##...##.",
            )
        )
    }

    @Test
    fun `count arrangements`() {
        assertThat(
            countArrangements(
                ".??..??...?##.", listOf(1, 1, 3)
            ),
            `is`(4)
        )
    }

    @Test
    fun testPart1Example() {
        assertThat(
            solvePart1(
                """
            ???.### 1,1,3
            .??..??...?##. 1,1,3
            ?#?#?#?#?#?#?#? 1,3,1,6
            ????.#...#... 4,1,1
            ????.######..#####. 1,6,5
            ?###???????? 3,2,1
        """.trimIndent().lines()
            ),
            `is`(21)
        )
    }
}