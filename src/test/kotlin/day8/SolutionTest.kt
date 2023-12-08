package day8

import kotlin.test.Test
import kotlin.test.assertEquals

class SolutionTest {

    @Test
    fun testPart1Example1() {
        assertEquals(
            2,
            solvePart1(
                """
                RL

                AAA = (BBB, CCC)
                BBB = (DDD, EEE)
                CCC = (ZZZ, GGG)
                DDD = (DDD, DDD)
                EEE = (EEE, EEE)
                GGG = (GGG, GGG)
                ZZZ = (ZZZ, ZZZ)
           """.trimIndent().lines()
            )
        )
    }

    @Test
    fun testPart1Example2() {
        assertEquals(
            6,
            solvePart1(
                """
                LLR

                AAA = (BBB, BBB)
                BBB = (AAA, ZZZ)
                ZZZ = (ZZZ, ZZZ) 
           """.trimIndent().lines()
            )
        )
    }

    @Test
    fun testPart2() {
        assertEquals(
            6,
            solvePart2(
                """
                LR

                11A = (11B, XXX)
                11B = (XXX, 11Z)
                11Z = (11B, XXX)
                22A = (22B, XXX)
                22B = (22C, 22C)
                22C = (22Z, 22Z)
                22Z = (22B, 22B)
                XXX = (XXX, XXX)
           """.trimIndent().lines()
            )
        )
    }
}

