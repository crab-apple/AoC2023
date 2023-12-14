package day14

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import kotlin.test.Test
import kotlin.test.assertEquals

class SolutionTest {

    val exampleInput = """
                O....#....
                O.OO#....#
                .....##...
                OO.#O....O
                .O.....O#.
                O.#..O.#.#
                ..O..#O..O
                .......O..
                #....###..
                #OO..#.... 
                """.trimIndent().lines()

    @Test
    fun testPart1() {
        assertEquals(136, solvePart1(exampleInput))
    }

    @Test
    fun testMoveOneRockWithoutStops() {
        assertThat(
            bringRocksToStart(".....O..##"),
            `is`(10)
        )
    }

    @Test
    fun testMoveFourRocksWithoutStops() {
        assertThat(
            bringRocksToStart("OO.O.O..##"),
            `is`(34)
        )
    }

    @Test
    fun testMoveSixRocksWithoutStops() {
        assertThat(
            bringRocksToStart("OO.O.O..OO"),
            `is`(45)
        )
    }

    @Test
    fun testMoveOneRockWithStop() {
        assertThat(
            bringRocksToStart("..#..O..##"),
            `is`(7)
        )
    }

    @Test
    fun testMoveTwoRocksWithStop() {
        assertThat(
            bringRocksToStart("..#..O.O##"),
            `is`(13)
        )
    }

    @Test
    fun testMoveRocksWithMultipleStops() {
        assertThat(
            bringRocksToStart(".O#..O.O##"),
            `is`(23)
        )
    }

    @Test
    fun testNoRocks() {
        assertThat(
            bringRocksToStart("..#.....##"),
            `is`(0)
        )
    }
}

