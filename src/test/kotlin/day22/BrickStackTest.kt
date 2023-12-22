package day22

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import kotlin.test.Test

class BrickStackTest {

    @Test
    fun testCompositionEmptyStack() {
        val stack = BrickStack()

        assertThat(
            stack.viewX(), `is`(
                """
                 x
                012
                ... 2
                ... 1 z
                --- 0
        """.trimIndent()
            )
        )

        assertThat(
            stack.viewY(), `is`(
                """
                 y
                012
                ... 2
                ... 1 z
                --- 0
        """.trimIndent()
            )
        )
    }

    @Test
    @Disabled
    fun testCompositionExampleInputStack() {

        val bricks = parseInput(
            """
                1,0,1~1,2,1
                0,0,2~2,0,2
                0,2,3~2,2,3
                0,0,4~0,2,4
                2,0,5~2,2,5
                0,1,6~2,1,6
                1,1,8~1,1,9 
                   """.trimIndent()
        )

        val stack = BrickStack(bricks)

        assertThat(
            stack.viewX(), `is`(
                """
                 x
                012
                .G. 6
                .G. 5
                FFF 4
                D.E 3 z
                ??? 2
                .A. 1
                --- 0
                """.trimIndent()
            )
        )

        assertThat(
            stack.viewY(), `is`(
                """
                 y
                012
                .G. 6
                .G. 5
                .F. 4
                ??? 3 z
                B.C 2
                AAA 1
                --- 0
        """.trimIndent()
            )
        )
    }
}