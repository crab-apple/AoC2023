package day22

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import utils.readInputOneString
import kotlin.test.Test

class SolutionTest {

    val exampleInput = """
                1,0,1~1,2,1
                0,0,2~2,0,2
                0,2,3~2,2,3
                0,0,4~0,2,4
                2,0,5~2,2,5
                0,1,6~2,1,6
                1,1,8~1,1,9 
                   """.trimIndent()

    @Test
    fun testPart1() {
        assertThat(
            solvePart1(exampleInput),
            `is`(0)
        )
    }

    @Test
    fun testPart1RealInput() {
        assertThat(
            solvePart1(readInputOneString("day22/input")),
            `is`(0)
        )
    }

    @Test
    fun testPart2() {
        assertThat(
            solvePart2(exampleInput),
            `is`(0)
        )
    }

    @Test
    fun testPart2RealInput() {
        assertThat(
            solvePart2(readInputOneString("day22/input")),
            `is`(0)
        )
    }
}