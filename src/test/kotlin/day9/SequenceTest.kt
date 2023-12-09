package day9

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import kotlin.test.Test

class SequenceTest {

    @Test
    fun `finds differential sequence`() {
        val sequence = Sequence.of(10, 13, 16, 21, 30, 45)
        assertThat(
            sequence.differential(),
            `is`(Sequence.of(3, 3, 5, 9, 15))
        )
    }

    @Test
    fun `finds next value`() {
        val sequence = Sequence.of(10, 13, 16, 21, 30, 45)
        assertThat(sequence.nextValue(), `is`(68))
    }
}