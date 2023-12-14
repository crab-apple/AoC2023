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
            `is`("O.......##")
        )
    }

    @Test
    fun testMoveFourRocksWithoutStops() {
        assertThat(
            bringRocksToStart("OO.O.O..##"),
            `is`("OOOO....##")
        )
    }

    @Test
    fun testMoveSixRocksWithoutStops() {
        assertThat(
            bringRocksToStart("OO.O.O..OO"),
            `is`("OOOOOO....")
        )
    }

    @Test
    fun testMoveOneRockWithStop() {
        assertThat(
            bringRocksToStart("..#..O..##"),
            `is`("..#O....##")
        )
    }

    @Test
    fun testMoveTwoRocksWithStop() {
        assertThat(
            bringRocksToStart("..#..O.O##"),
            `is`("..#OO...##")
        )
    }

    @Test
    fun testMoveRocksWithMultipleStops() {
        assertThat(
            bringRocksToStart(".O#..O.O##"),
            `is`("O.#OO...##")
        )
    }

    @Test
    fun testMoveRocksNoRocks() {
        assertThat(
            bringRocksToStart("..#.....##"),
            `is`("..#.....##")
        )
    }
}

