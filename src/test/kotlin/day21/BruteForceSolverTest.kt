package day21

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import utils.readInputOneString
import kotlin.test.Test

class BruteForceSolverTest {

    val exampleInput = """
    ...........
    .....###.#.
    .###.##..#.
    ..#.#...#..
    ....#.#....
    .##..S####.
    .##..#...#.
    .......##..
    .##.#.####.
    .##..##.##.
    ...........
    """.trimIndent()


    @ParameterizedTest
    @MethodSource("part2TestParams")
    fun testPart2BruteForce(testCase: TestCase) {
        assertThat(BruteForceSolver().solve(exampleInput, testCase.targetSteps), `is`(testCase.expectedResult))
    }


    companion object {

        @JvmStatic
        fun part2TestParams() = listOf(
            TestCase(4, 9),
            TestCase(6, 16),
            TestCase(10, 50),
            TestCase(15, 115),
            TestCase(25, 353),
            TestCase(30, 537),
            TestCase(35, 740),
            TestCase(40, 989),
            TestCase(50, 1594),
            TestCase(100, 6536),
//            TestCase(200, 26538),
        )
    }

    data class TestCase(val targetSteps: Long, val expectedResult: Long)
}