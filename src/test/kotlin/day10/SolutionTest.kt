package day10

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Disabled
import kotlin.test.Test

class SolutionTest {

    @Test
    fun testPart1Example1() {

        assertThat(
            doTest(
                """
                -L|F7
                7S-7|
                L|7||
                -L-J|
                L|-JF
                   """
            ),
            `is`(4)
        )
    }

    @Test
    fun testPart1Example2() {

        assertThat(
            doTest(
                """
                ..F7.
                .FJ|.
                SJ.L7
                |F--J
                LJ...
                   """
            ),
            `is`(8)
        )
    }

    private fun doTest(input: String): Int {
        return solvePart1(
            input.trimIndent().lines()
        )
    }
}

