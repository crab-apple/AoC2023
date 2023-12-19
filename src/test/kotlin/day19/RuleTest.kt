package day19

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import kotlin.test.Test

class RuleTest {

    private val part = Part.parse("{x=1,m=10,a=100,s=1000}")

    @Test
    fun `test name`() {
        assertThat(rule("foo{}").name(), `is`("foo"))
        assertThat(rule("bar{}").name(), `is`("bar"))
    }

    @Test
    fun `test single rule`() {
        assertThat(
            rule("foo{A}").evaluate(part),
            `is`("A")
        )
        assertThat(
            rule("foo{R}").evaluate(part),
            `is`("R")
        )
    }

    @Test
    fun `test threshold evaluation, matching`() {
        assertThat(
            rule("foo{a>99:A,R}").evaluate(part),
            `is`("A")
        )
    }

    @Test
    fun `test threshold evaluation, not matching`() {
        assertThat(
            rule("foo{a>101:A,R}").evaluate(part),
            `is`("R")
        )
        assertThat(
            rule("foo{a>100:A,R}").evaluate(part),
            `is`("R")
        )
    }

    private fun rule(s: String) = Rule.parse(s)
}