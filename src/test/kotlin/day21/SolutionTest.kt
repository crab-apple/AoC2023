package day21

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Disabled
import utils.readInputOneString
import kotlin.test.Test

class SolutionTest {

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

    @Test
    fun testPart1() {
        assertThat(
            solvePart1(exampleInput, 6),
            `is`(16)
        )
    }

    @Test
    fun testPart1RealInput() {
        assertThat(
            solvePart1(readInputOneString("day21/input")),
            `is`(3699)
        )
    }

    @Test
    fun testPart2RealInput() {
        assertThat(
            solvePart2(readInputOneString("day21/input")),
            `is`(613391294577878)
        )
    }
}