package day17

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import kotlin.test.Test

class SolutionTest {

    val exampleInput = """
        2413432311323
        3215453535623
        3255245654254
        3446585845452
        4546657867536
        1438598798454
        4457876987766
        3637877979653
        4654967986887
        4564679986453
        1224686865563
        2546548887735
        4322674655533
    """.trimIndent()

    @Test
    fun testPart1() {
        assertThat(
            solvePart1(exampleInput),
            `is`(102)
        )
    }

    @Test
    fun testStraightRight() {
        assertThat(
            solvePart1("""123"""),
            `is`(5)
        )
    }

    @Test
    fun testStraightDown() {
        assertThat(
            solvePart1(
                """
                1
                2
                3
                """.trimIndent()
            ),
            `is`(5)
        )
    }

    @Test
    fun testAngles() {
        assertThat(
            solvePart1(
                """
                112
                111
                121
                """.trimIndent()
            ),
            `is`(4)
        )
    }
}