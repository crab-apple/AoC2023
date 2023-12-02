package day2

import day2.Color.BLUE
import day2.Color.GREEN
import day2.Color.RED
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameTest {

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
}

