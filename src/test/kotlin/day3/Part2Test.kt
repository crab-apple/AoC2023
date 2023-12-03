package day3

import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals

class Part2Test {

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
    fun testPart2() {
        assertEquals(
            467835,
            Part2Solver.solve(exampleInput.lines())
        )
    }
}

