package day3

import day3.Schematic.Symbol
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import kotlin.test.Test

class SchematicTest {

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
                Symbol('*', Pair(1, 3)),
                Symbol('#', Pair(3, 6)),
                Symbol('*', Pair(4, 3)),
                Symbol('+', Pair(5, 5)),
                Symbol('$', Pair(8, 3)),
                Symbol('*', Pair(8, 5)),
            )
        )
    }
}

