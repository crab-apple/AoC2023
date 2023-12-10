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

    @ParameterizedTest
    @MethodSource("testCasesPart2")
    fun testPart2(testCase: TestCase) {
        assertThat(
            solvePart2(
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

        @JvmStatic
        fun testCasesPart2(): List<TestCase> {

            return listOf(
                TestCase(
                    """
                .....
                .S-7.
                .|.|.
                .L-J.
                .....
                  """,
                    1
                ),
                TestCase(
                    """
                ...........
                .S-------7.
                .|F-----7|.
                .||.....||.
                .||.....||.
                .|L-7.F-J|.
                .|..|.|..|.
                .L--J.L--J.
                ...........
                  """,
                    4
                ),
                TestCase(
                    """
                ..........
                .S------7.
                .|F----7|.
                .||....||.
                .||....||.
                .|L-7F-J|.
                .|..||..|.
                .L--JL--J.
                ..........
                                  """,
                    4
                ),
                TestCase(
                    """
                .F----7F7F7F7F-7....
                .|F--7||||||||FJ....
                .||.FJ||||||||L7....
                FJL7L7LJLJ||LJ.L-7..
                L--J.L7...LJS7F-7L7.
                ....F-J..F7FJ|L7L7L7
                ....L7.F7||L7|.L7L7|
                .....|FJLJ|FJ|F7|.LJ
                ....FJL-7.||.||||...
                ....L---J.LJ.LJLJ...
                  """,
                    8
                ),
                TestCase(
                    """
                FF7FSF7F7F7F7F7F---7
                L|LJ||||||||||||F--J
                FL-7LJLJ||||||LJL-77
                F--JF--7||LJLJ7F7FJ-
                L---JF-JLJ.||-FJLJJ7
                |F|F-JF---7F7-L7L|7|
                |FFJF7L7F-JF7|JL---7
                7-L-JL7||F7|L7F-7F7|
                L.L7LFJ|||||FJL7||LJ
                L7JLJL-JLJLJL--JLJ.L
                 """,
                    10
                ),
                TestCase(
                    """
                ........
                .S----7.
                .|....|.
                .|....|.
                .|....|.
                .|....|.
                .L----J.
                ........
                  """,
                    16
                ),
            )
        }
    }

    data class TestCase(val input: String, val expected: Long)
}

