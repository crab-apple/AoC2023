package day11

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class SolutionTest {

    @Test
    fun testPart1Example() {
        assertThat(
            solvePart1(
                """
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#..... 
        """.trimIndent().lines()
            ),
            `is`(374)
        )
    }

    @ParameterizedTest
    @MethodSource("expandSpaceTestCases")
    fun testExpandSpace(testCase: ExpandSpaceTestCase) {
        assertThat(
            expandSpace(testCase.input.trimIndent().lines()),
            `is`(testCase.expectedOutput.trimIndent().lines())
        )
    }

    companion object {

        @JvmStatic
        fun expandSpaceTestCases() = listOf(
            ExpandSpaceTestCase(
                "Nothing to expand",
                """
        .#
        #.
        """,

                """
        .#
        #.
        """,
            ),
            ExpandSpaceTestCase(
                "With empty rows",
                """
        ..#
        ...
        #..
        ...
        ##.
        """,

                """
        ..#
        ...
        ...
        #..
        ...
        ...
        ##.
        """,
            ),
            ExpandSpaceTestCase(
                "With empty columns",
                """
        ..#.#
        #...#
        ....#
        """,

                """
        ...#..#
        #.....#
        ......#
        """,
            )
        )
    }

    data class ExpandSpaceTestCase(val name: String, val input: String, val expectedOutput: String) {

        override fun toString(): String {
            return name
        }
    }
}

