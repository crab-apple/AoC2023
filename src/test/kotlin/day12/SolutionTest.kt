package day12

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

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

    @ParameterizedTest
    @MethodSource("countArrangementsTestCases")
    fun `count arrangements`(testCase: TestCase) {
        assertThat(
            countArrangements(testCase.input, testCase.groups),
            `is`(testCase.expectedOutput)
        )
    }

    @Test
    fun `count arrangements large input`() {
        assertThat(
            countArrangements(
                ".??..??...?##.?.??..??...?##.?.??..??...?##.?.??..??...?##.?.??..??...?##.",
                listOf(1, 1, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3, 1, 1, 3)
            ),
            `is`(16384)
        )

        assertThat(
            countArrangements(
                "???#??????",
                listOf(1, 1, 1)
            ),
            `is`(16)
        )
        assertThat(
            countArrangements(
                repeatString("???#??????", 2),
                repeat(listOf(1, 1, 1), 2)
            ),
            `is`(587)
        )
        assertThat(
            countArrangements(
                repeatString("???#??????", 3),
                repeat(listOf(1, 1, 1), 3)
            ),
            `is`(24486)
        )
        assertThat(
            countArrangements(
                repeatString("???#??????", 4),
                repeat(listOf(1, 1, 1), 4)
            ),
            `is`(1080018)
        )
        assertThat(
            countArrangements(
                repeatString("???#??????", 5),
                repeat(listOf(1, 1, 1), 4)
            ),
            `is`(5173570)
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

    @Test
    fun testRepeat() {
        assertThat(
            repeat(listOf(1, 2, 3), 3),
            `is`(listOf(1, 2, 3, 1, 2, 3, 1, 2, 3))
        )
    }

    @Test
    fun testRepeatString() {
        assertThat(
            repeatString("##", 3),
            `is`("##?##?##")
        )
    }

    companion object {

        @JvmStatic
        fun countArrangementsTestCases() = listOf(
            TestCase("Empty", "", listOf(), 1),
            TestCase("Group size 1, matching", "#", listOf(1), 1),
            TestCase("Group size 1, not matching", "#", listOf(), 0),
            TestCase("Group size 2, matching", "##", listOf(2), 1),
            TestCase("Group size 2, too big", "##", listOf(1), 0),
            TestCase("Group size 2, too small", "##", listOf(3), 0),
            TestCase("Leading dots, matching", "..##", listOf(2), 1),
            TestCase("Leading dots, too big", "..##", listOf(1), 0),
            TestCase("Leading dots, too small", "..##", listOf(3), 0),
            TestCase("Trailing dots, matching", "##..", listOf(2), 1),
            TestCase("Trailing dots, too big", "##..", listOf(1), 0),
            TestCase("Trailing dots, too small", "##..", listOf(3), 0),
            TestCase("Two groups, matching", "..##..##..", listOf(2, 2), 1),
            TestCase("Two groups, first too big", "..###..##..", listOf(2, 2), 0),
            TestCase("Two groups, second too big", "..##..###..", listOf(2, 2), 0),
            TestCase("Two groups, first too small", "..#..##..", listOf(2, 2), 0),
            TestCase("Two groups, second too small", "..##..#..", listOf(2, 2), 0),
            TestCase("With question marks", "?..##..##..", listOf(2, 2), 1),
            TestCase(
                "Hash followed by many question marks, single group",
                "#???????????????????????????",
                listOf(10),
                1
            ),
            TestCase("?#", listOf(1, 1), 0),
            TestCase("??#", listOf(1, 1), 1),
            TestCase("??", listOf(1, 1), 0),
            TestCase("???", listOf(1, 1), 1),
            TestCase("???.###", listOf(1, 1, 3), 1),
            TestCase(".??..??...?##.", listOf(1, 1, 3), 4),
            TestCase("?#?#?#?#?#?#?#?", listOf(1, 3, 1, 6), 1),
            TestCase("????.#...#...", listOf(4, 1, 1), 1),
            TestCase("????.######..#####.", listOf(1, 6, 5), 4),
            TestCase("?###????????", listOf(3, 2, 1), 10),
        )
    }

    data class TestCase(val name: String, val input: String, val groups: List<Int>, val expectedOutput: Long) {

        constructor(input: String, groups: List<Int>, expectedOutput: Long) : this(input, input, groups, expectedOutput)

        override fun toString(): String {
            return name
        }
    }
}