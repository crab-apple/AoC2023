package day16

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import kotlin.test.Test

class SolutionTest {

    val exampleInput = """
                .|...\....
                |.-.\.....
                .....|-...
                ........|.
                ..........
                .........\
                ..../.\\..
                .-.-/..|..
                .|....-|.\
                ..//.|....
    """.trimIndent()

    @Test
    fun testPart1() {
        assertThat(
            solvePart1(exampleInput),
            `is`(46)
        )
    }

    @Test
    fun testPart2() {
        assertThat(
            solvePart2(exampleInput),
            `is`(51)
        )
    }

    @Test
    fun `energy map, no artifacts`() {
        testEnergyMap(
            """
                  ...
                  ...
                  ...
                  """,
            """
                  ###
                  ...
                  ...
                  """
        )
    }

    @Test
    fun `energy map, mirrors`() {
        testEnergyMap(
            """
                  .\.
                  ...
                  ./.
                  """,
            """
                  ##.
                  .#.
                  ##.
                  """
        )
    }

    @Test
    fun `energy map, splitters in line`() {
        testEnergyMap(
            """
                  .\.
                  .|.
                  ./.
                  """,
            """
                  ##.
                  .#.
                  ##.
                  """
        )
    }

    @Test
    fun `energy map, splitters flat`() {
        testEnergyMap(
            """
                  .\.
                  .-.
                  ./.
                  """,
            """
                  ##.
                  ###
                  ...
                  """
        )
    }

    @Test
    fun `energy map, cycles`() {
        testEnergyMap(
            """
                  ._\
                  .\/
                  ...
                  """,
            """
                  ###
                  .##
                  ...
                  """
        )
    }

    @Test
    fun `energy map, exampleInput`() {
        testEnergyMap(
            """
                .|...\....
                |.-.\.....
                .....|-...
                ........|.
                ..........
                .........\
                ..../.\\..
                .-.-/..|..
                .|....-|.\
                ..//.|....
                  """,
            """
                ######....
                .#...#....
                .#...#####
                .#...##...
                .#...##...
                .#...##...
                .#..####..
                ########..
                .#######..
                .#...#.#..
                 """
        )
    }

    private fun testEnergyMap(input: String, expected: String) {
        assertThat(
            makeEnergyMap(input.trimIndent()),
            `is`(expected.trimIndent())
        )
    }
}