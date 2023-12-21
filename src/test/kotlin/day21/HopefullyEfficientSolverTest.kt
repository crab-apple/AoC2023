package day21

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test

class HopefullyEfficientSolverTest {

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
    fun comparisonTestRegularSizedPlot(testCase: TestCase) {

        // We make up an input that has straight lines through the middle, just like the real input. Then we check
        // that the "clever" solution, which relies on this property, gives the same result as the brute force solution

        val input = """
            ...........
            .###..##.#.
            .#.#..#..#.
            .#......#..
            .####.#....
            .....S.....
            .##......#.
            .......##..
            .##.#.##.#.
            .##...#.##.
            ...........
    """.trimIndent()

        comparisonTest(input, testCase.targetSteps)
    }

    @Test
    fun testPerformance() {

        val input = """
            ...........
            .###..##.#.
            .#.#..#..#.
            .#......#..
            .####.#....
            .....S.....
            .##......#.
            .......##..
            .##.#.##.#.
            .##...#.##.
            ...........
    """.trimIndent()

        HopefullyEfficientSolver().solve(input, 1000)
    }

    @ParameterizedTest
    @MethodSource("part2TestParams")
    fun comparisonTestSmallishPlot(testCase: TestCase) {

        val input = """
            .......
            .#..##.
            .#..##.
            ...S...
            .##.##.
            .#..##.
            .......
    """.trimIndent()

        comparisonTest(input, testCase.targetSteps)
    }

    @ParameterizedTest
    @MethodSource("part2TestParams")
    fun comparisonTestSmallPlot(testCase: TestCase) {

        val input = """
            .....
            .#...
            ..S..
            .#.#.
            .....
    """.trimIndent()

        comparisonTest(input, testCase.targetSteps)
    }

    @ParameterizedTest
    @MethodSource("part2TestParams")
    fun comparisonTestEmptyPlot(testCase: TestCase) {

        val input = """
            ...
            .S.
            ...
    """.trimIndent()
        comparisonTest(input, testCase.targetSteps)
    }

    @Test
    fun testEmptyPlot() {

        val input = """
            ...
            .S.
            ...
    """.trimIndent()

        var result: HopefullyEfficientSolver.SolutionDetails
        val solver = HopefullyEfficientSolver()

        result = solver.solveDetailed(input, 0)
        assertThat(result.reps, `is`(0))
        assertThat(result.maxIntraPlotDistance, `is`(4))
        assertThat(result.cornerMin, `is`(0))
        assertThat(result.cornerMax, `is`(0))
        assertThat(result.answer, `is`(1))
        assertThat(result.numReachablePlots, `is`(1))
        assertThat(result.numEvaluatedPlots, `is`(1))

        result = solver.solveDetailed(input, 1)
        assertThat(result.reps, `is`(0))
        assertThat(result.maxIntraPlotDistance, `is`(4))
        assertThat(result.cornerMin, `is`(0))
        assertThat(result.cornerMax, `is`(0))
        assertThat(result.answer, `is`(4))
        assertThat(result.numReachablePlots, `is`(1))
        assertThat(result.numEvaluatedPlots, `is`(1))

        result = solver.solveDetailed(input, 2)
        assertThat(result.reps, `is`(1))
        assertThat(result.maxIntraPlotDistance, `is`(4))
        assertThat(result.cornerMin, `is`(0))
        assertThat(result.cornerMax, `is`(0))
        assertThat(result.answer, `is`(9))
        assertThat(result.numReachablePlots, `is`(5))
        assertThat(result.numEvaluatedPlots_origin, `is`(1))
        assertThat(result.numEvaluatedPlots_subsumed, `is`(0))
        assertThat(result.numEvaluatedPlots_straights, `is`(4))
        assertThat(result.numEvaluatedPlots_corners, `is`(0))
        assertThat(result.numEvaluatedPlots, `is`(5))

        result = solver.solveDetailed(input, 3)
        assertThat(result.reps, `is`(1))
        assertThat(result.maxIntraPlotDistance, `is`(4))
        assertThat(result.cornerMin, `is`(0))
        assertThat(result.cornerMax, `is`(0))
        assertThat(result.answer, `is`(16))
        assertThat(result.numReachablePlots, `is`(5))
        assertThat(result.numEvaluatedPlots, `is`(5))

        result = solver.solveDetailed(input, 4)
        assertThat(result.reps, `is`(1))
        assertThat(result.maxIntraPlotDistance, `is`(4))
        assertThat(result.cornerMin, `is`(0))
        assertThat(result.cornerMax, `is`(1))
        assertThat(result.answer, `is`(25))
        assertThat(result.numReachablePlots, `is`(9))
        assertThat(result.numEvaluatedPlots, `is`(9))

        result = solver.solveDetailed(input, 5)
//        assertThat(result.answer, `is`(25))
//        assertThat(result.numReachablePlots, `is`(9))
//        assertThat(result.numEvaluatedPlots, `is`(9))
//        assertThat(result.reps, `is`(1))
        assertThat(result.maxIntraPlotDistance, `is`(4))
        assertThat(result.cornerMin, `is`(1))
        assertThat(result.cornerMax, `is`(1))

        result = solver.solveDetailed(input, 6)
        assertThat(result.cornerMin, `is`(1))
        assertThat(result.cornerMax, `is`(1))

        result = solver.solveDetailed(input, 7)
        assertThat(result.cornerMin, `is`(1))
        assertThat(result.cornerMax, `is`(2))

        result = solver.solveDetailed(input, 8)
        assertThat(result.cornerMin, `is`(2))
        assertThat(result.cornerMax, `is`(2))

        result = solver.solveDetailed(input, 15)
        assertThat(result.cornerMin, `is`(4))
        assertThat(result.cornerMax, `is`(4))
        assertThat(result.numReachablePlots_subsumed, `is`(24))
        assertThat(result.numEvaluatedPlots_subsumed, `is`(24))
        assertThat(result.numReachablePlots_origin, `is`(1))
        assertThat(result.numEvaluatedPlots_origin, `is`(1))
        assertThat(result.numReachablePlots_straights, `is`(20))
        assertThat(result.numEvaluatedPlots_straights, `is`(20))
        assertThat(result.numReachablePlots_corners, `is`(16))
        assertThat(result.numEvaluatedPlots_corners, `is`(16))
        assertThat(result.numReachablePlots, `is`(61))
        assertThat(result.numEvaluatedPlots, `is`(61))

        assertThat(result.answer_origin, `is`(4))
        assertThat(result.subsumedEvenPerQuadrant, `is`(4))
        assertThat(result.subsumedOddPerQuadrant, `is`(2))
        assertThat(result.answer_subsumed, `is`(26 * 4))
    }

    private fun comparisonTest(input: String, targetSteps: Long) {
        assertThat(
            HopefullyEfficientSolver().solve(input, targetSteps),
            `is`(BruteForceSolver().solve(input, targetSteps))
        )
    }

    companion object {

        @JvmStatic
        fun part2TestParams() = listOf(
            TestCase(4),
            TestCase(6),
            TestCase(10),
            TestCase(15),
            TestCase(25),
            TestCase(30),
            TestCase(35),
            TestCase(40),
            TestCase(50),
            TestCase(100),
//            TestCase(200, 26538),
        )
    }

    data class TestCase(val targetSteps: Long)
}