package day19

import day19.Category.A
import day19.Category.M
import day19.Category.S
import day19.Category.X
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import kotlin.test.Test

class PartTest {

    @Test
    fun testParse() {
        val part = Part.parse("{x=787,m=2655,a=1222,s=2876}")
        assertThat(part[X], `is`(787))
        assertThat(part[M], `is`(2655))
        assertThat(part[A], `is`(1222))
        assertThat(part[S], `is`(2876))
    }

    @Test
    fun testAddRatings() {
        val part = Part.parse("{x=787,m=2655,a=1222,s=2876}")
        assertThat(part.addRatings(), `is`(7540))
    }
}