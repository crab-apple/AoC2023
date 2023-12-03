package day3

import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import kotlin.test.Test
import kotlin.test.assertEquals

class Part1Test {

    val exampleInput = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598..
    """.trimIndent()

    @Test
    fun testPart1() {
        assertEquals(
            4361,
            Part1Solver.solve(exampleInput.lines())
        )
    }

    @Test
    fun `finds numbers in schematic`() {
        val schematic = Schematic(exampleInput.lines())
        val numbers = schematic.numbers()
        MatcherAssert.assertThat(
            numbers.map { it.value },
            Matchers.containsInAnyOrder(
                467,
                114,
                35,
                633,
                617,
                58,
                592,
                755,
                664,
                598,
            )
        )
    }

    @Test
    fun `finds row of numbers in schematic`() {
        val schematic = Schematic(exampleInput.lines())
        val thirtyFive = schematic.numbers().first { it.value == 35 }
        MatcherAssert.assertThat(thirtyFive.row, Matchers.equalTo(2))
    }

    @Test
    fun `finds starting position of numbers in schematic`() {
        val schematic = Schematic(exampleInput.lines())
        val thirtyFive = schematic.numbers().first { it.value == 35 }
        MatcherAssert.assertThat(thirtyFive.start, Matchers.equalTo(2))
    }

    @Test
    fun `finds ending position of numbers in schematic`() {
        val schematic = Schematic(exampleInput.lines())
        val thirtyFive = schematic.numbers().first { it.value == 35 }
        MatcherAssert.assertThat(thirtyFive.end, Matchers.equalTo(3))
    }

    @Test
    fun `finds position of symbols in schematic`() {
        val schematic = Schematic(exampleInput.lines())
        MatcherAssert.assertThat(
            schematic.symbols(), Matchers.containsInAnyOrder(
                Pair(1, 3),
                Pair(3, 6),
                Pair(4, 3),
                Pair(5, 5),
                Pair(8, 3),
                Pair(8, 5),
            )
        )
    }
}

