package day10

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class SolutionTest {

    @ParameterizedTest
    @MethodSource("testCasesPart1")
    fun testPart1(testCase: TestCase) {
        assertThat(
            solvePart1(
                testCase.input
                    .trimIndent().lines()
            ),
            `is`(testCase.expected)
        )
    }

    companion object {

        @JvmStatic
        fun testCasesPart1(): List<TestCase> {

            return listOf(
                TestCase(
                    """
                -L|F7
                7S-7|
                L|7||
                -L-J|
                L|-JF
                   """,
                    4
                ),
                TestCase(
                    """
                ..F7.
                .FJ|.
                SJ.L7
                |F--J
                LJ...
                   """,
                    8
                ),
            )
        }
    }

    data class TestCase(val input: String, val expected: Long)
}

