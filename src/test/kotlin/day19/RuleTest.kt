package day19

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import kotlin.test.Test

class RuleTest {


    @Test
    fun `test name`() {
        assertThat(rule("foo{a>99:A,R}").name(), `is`("foo"))
        assertThat(rule("bar{a>99:A,R}").name(), `is`("bar"))
    }

    @Test
    fun `test threshold evaluation, matching`() {
        val part = Part.parse("{x=1,m=10,a=100,s=1000}")
        assertThat(
            rule("foo{a>99:A,R}").evaluate(part),
            `is`("A")
        )
    }

    @Test
    fun `test threshold evaluation, not matching`() {
        val part = Part.parse("{x=1,m=10,a=100,s=1000}")
        assertThat(
            rule("foo{a>101:A,R}").evaluate(part),
            `is`("R")
        )
        assertThat(
            rule("foo{a>100:A,R}").evaluate(part),
            `is`("R")
        )
    }

    @Test
    fun `test multiple threshold evaluation `() {

        val rule = rule("foo{a<100:abc,a<200:def,R}")

        assertThat(
            rule.evaluate(Part.parse("{x=0,m=0,a=0,s=0}")),
            `is`("abc")
        )

        assertThat(
            rule.evaluate(Part.parse("{x=0,m=0,a=150,s=0}")),
            `is`("def")
        )

        assertThat(
            rule.evaluate(Part.parse("{x=0,m=0,a=250,s=0}")),
            `is`("R")
        )
    }

    private fun rule(s: String) = Rule.parse(s)
}