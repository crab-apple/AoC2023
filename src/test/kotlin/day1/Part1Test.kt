package day1

import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals

class Part1Test {

    @Test
    fun example() {
        test(
            """
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet
                """,
            142
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
    fun withNonDigits(){
        test("foo1bar2baz", 12)
    }

    private fun test(input: String, output: Int) {
        assertEquals(
            output,
            solve(input.trimIndent().lines())
        )
    }
}