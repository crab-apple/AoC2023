package day17

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import kotlin.random.Random
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
    fun testPart2() {
        assertThat(
            solvePart2(exampleInput),
            `is`(94)
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

    @Test
    fun ultraCrucibleExample2() {
        assertThat(
            solvePart2(
                """
                111111111111
                999999999991
                999999999991
                999999999991
                999999999991
                """.trimIndent()
            ),
            `is`(71)
        )
    }

    @Test
    fun performanceTest() {
        solvePart1(makeInputOfSide(2))
        solvePart1(makeInputOfSide(10))
        solvePart1(makeInputOfSide(20))
        solvePart1(makeInputOfSide(50))
        solvePart1(makeInputOfSide(75))
        solvePart1(makeInputOfSide(100))
        solvePart1(makeInputOfSide(150))
    }

    private fun makeInputOfSide(i: Int): String {
        val random = Random(123)
        return (0..<i).map {
            (0..<i).map {
                random.nextInt(10)
            }.joinToString("")
        }.joinToString("\n")
    }
}