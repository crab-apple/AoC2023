package day2

import day2.Color.BLUE
import day2.Color.GREEN
import day2.Color.RED
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Part1Test {

    @Test
    fun example() {
        test(
            """
                Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
                """,
            8
        )
    }

    @Test
    fun `parses sample with one color`() {
        val sample = Cubes.parse("6 red")
        assertThat(sample.get(RED), equalTo(6));
    }

    @Test
    fun `parses sample with different color`() {
        val sample = Cubes.parse("7 blue")
        assertThat(sample.get(BLUE), equalTo(7));
    }

    @Test
    fun `parses sample with numbers having more than one digit`() {
        val sample = Cubes.parse("70 blue")
        assertThat(sample.get(BLUE), equalTo(70));
    }

    @Test
    fun `colors not in input have quantity 0`() {
        val sample = Cubes.parse("7 blue")
        assertThat(sample.get(RED), equalTo(0));
    }

    @Test
    fun `multiple colors`() {
        val sample = Cubes.parse("6 red, 1 blue, 3 green")
        assertThat(sample.get(RED), equalTo(6));
        assertThat(sample.get(BLUE), equalTo(1));
        assertThat(sample.get(GREEN), equalTo(3));
    }

    @Test
    fun `parses game ID`() {
        val game = Game.parse("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red")
        assertThat(game.id, equalTo(3));
    }

    @Test
    fun `possible game`() {
        val game = Game.parse("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red")
        assertTrue(game.bagIsPossible(Cubes(RED to 20, GREEN to 13, BLUE to 6)));
    }

    @Test
    fun `impossible game`() {
        val game = Game.parse("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red")
        assertFalse(game.bagIsPossible(Cubes(RED to 20, GREEN to 13, BLUE to 5)));
    }

    private fun test(input: String, output: Int) {
        assertEquals(
            output,
            Part1Solver.solve(input.trimIndent().lines())
        )
    }
}

