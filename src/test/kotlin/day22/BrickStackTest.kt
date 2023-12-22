package day22

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Disabled
import kotlin.test.Test

class BrickStackTest {

    @Test
    fun testCompositionEmptyStack() {
        val stack = BrickStack()

        assertViews(
            stack,
            """
                 x         y
                012       012
                ... 2     ... 2
                ... 1 z   ... 1 z
                --- 0     --- 0
        """
        )
    }

    @Test
    fun testCompositionOneBrickAlreadyFallen() {
        val stack = BrickStack(
            listOf(
                Brick(1 to 1, 1 to 2, 1 to 1)
            )
        )

        assertViews(
            stack,
            """
                 x         y
                012       012
                ... 2     ... 2
                .A. 1 z   .AA 1 z
                --- 0     --- 0
        """
        )
    }

    @Test
    fun testCompositionTwoBricksAlreadyFallen() {
        val stack = BrickStack(
            listOf(
                Brick(1 to 1, 1 to 2, 1 to 1),
                Brick(0 to 2, 2 to 2, 2 to 5),
            )
        )

        assertViews(
            stack,
            """
                 x         y
                012       012
                BBB 5     ..B 5
                BBB 4     ..B 4
                BBB 3 z   ..B 3 z
                BBB 2     ..B 2
                .A. 1     .AA 1
                --- 0     --- 0
        """
        )
    }

    @Test
    fun testCompositionTwoBricksAlreadyFallenOverlapping() {
        val stack = BrickStack(
            listOf(
                Brick(1 to 1, 0 to 1, 1 to 1),
                Brick(0 to 2, 2 to 2, 1 to 4),
            )
        )

        assertViews(
            stack,
            """
                 x         y
                012       012
                BBB 4     ..B 4
                BBB 3     ..B 3
                BBB 2 z   ..B 2 z
                B?B 1     AAB 1
                --- 0     --- 0
        """
        )
    }

    @Test
    fun testFallingBrick() {
        val stack = BrickStack(
            listOf(
                Brick(1 to 1, 1 to 1, 2 to 2),
            )
        )

        assertViews(
            stack,
            """
                 x         y
                012       012
                ... 2     ... 2
                .A. 1 z   .A. 1 z
                --- 0     --- 0
        """
        )
    }

    @Test
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

    private fun assertViews(stack: BrickStack, views: String) {
        val clean = views.trimIndent().lines().map { it.trimEnd() }
        val split = clean.map { it.indexOf('z') }.single { it > 0 } + 1
        val xView = clean.joinToString("\n") { it.substring(0, split).trimEnd() }
        val yView = clean.joinToString("\n") { it.substring(split).trimEnd() }.trimIndent()
        assertThat(stack.viewX(), `is`(xView))
        assertThat(stack.viewY(), `is`(yView))
    }
}