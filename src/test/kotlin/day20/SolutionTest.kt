package day20

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Disabled
import utils.readInputOneString
import kotlin.test.Test

class SolutionTest {

    @Test
    fun testPart1Example1() {
        assertThat(
            solvePart1(
                """
                    broadcaster -> a, b, c
                    %a -> b
                    %b -> c
                    %c -> inv
                    &inv -> a
                """.trimIndent()
            ),
            `is`(32000000)
        )
    }

    @Test
    fun testPart1Example2() {
        assertThat(
            solvePart1(
                """
                    broadcaster -> a
                    %a -> inv, con
                    &inv -> b
                    %b -> con
                    &con -> output
                """.trimIndent()
            ),
            `is`(11687500)
        )
    }

    @Test
    fun testPart1RealInput() {
        assertThat(
            solvePart1(readInputOneString("day20/input")),
            `is`(747304011)
        )
    }

    @Test
    fun testPart2RealInput() {
        assertThat(
            solvePart2(readInputOneString("day20/input")),
            `is`(220366255099387)
        )
    }

    @Test
    fun testToString() {
        val input = readInputOneString("day20/input")
        val network = ModuleNetwork.parse(input)
        assertThat(
            network.toString().lines().sorted(),
            `is`(input.lines().sorted())
        )
    }
}