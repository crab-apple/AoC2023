package day2

import day2.Color.BLUE
import day2.Color.GREEN
import day2.Color.RED
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import kotlin.test.Test

class CubesTest {

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
}

