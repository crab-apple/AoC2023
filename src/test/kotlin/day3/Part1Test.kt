package day3

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
}

