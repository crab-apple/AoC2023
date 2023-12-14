package day14

import asInput
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
    fun testPart2() {
        assertEquals(64, solvePart2(exampleInput))
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

    @Test
    fun testCycle() {

        assertThat(
            cycle(exampleInput),
            `is`(
                """
        .....#....
        ....#...O#
        ...OO##...
        .OO#......
        .....OOO#.
        .O#...O#.#
        ....O#....
        ......OOOO
        #...O###..
        #..OO#....
        """.asInput()
            )
        )

        assertThat(
            cycle(cycle(exampleInput)),
            `is`(
                """
        .....#....
        ....#...O#
        .....##...
        ..O#......
        .....OOO#.
        .O#...O#.#
        ....O#...O
        .......OOO
        #..OO###..
        #.OOO#...O
        """.asInput()
            )
        )


        assertThat(
            cycle(cycle(cycle(exampleInput))),
            `is`(
                """
        .....#....
        ....#...O#
        .....##...
        ..O#......
        .....OOO#.
        .O#...O#.#
        ....O#...O
        .......OOO
        #...O###.O
        #.OOO#...O
        """.asInput()
            )
        )
    }
}

