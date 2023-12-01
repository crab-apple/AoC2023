package day1

import kotlin.test.Test
import kotlin.test.assertEquals

class Part2Test {

    @Test
    fun example() {
        test(
            """
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen
                """,
            281
        )
    }

    @Test
    fun twoDigits() {
        test("12", 12)
    }

    @Test
    fun threeDigits() {
        test("123", 13)
    }

    @Test
    fun multipleLines() {
        test(
            """
            123
            124
        """,
            27
        )
    }

    @Test
    fun oneDigit() {
        test("4", 44)
    }

    @Test
    fun withNonDigits() {
        test("foo1bar2baz", 12)
    }

    @Test
    fun withNumbersAsWords() {
        test("onetwo", 12)
    }

    private fun test(input: String, output: Int) {
        assertEquals(
            output,
            Part2Solver.solve(input.trimIndent().lines())
        )
    }
}