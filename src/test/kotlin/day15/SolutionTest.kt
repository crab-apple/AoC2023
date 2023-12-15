package day15

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class SolutionTest {

    val exampleInput = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"

    @Test
    fun testPart1() {
        assertEquals(1320, solvePart1(exampleInput))
    }

    @Test
    fun testHash() {
        assertThat(hash("HASH"), `is`(52))
    }
}