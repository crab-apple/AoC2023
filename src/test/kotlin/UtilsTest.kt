import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import kotlin.test.Test

class UtilsTest {

    @Test
    fun testTranspose() {
        assertThat(
            transpose(
                """
            AB
            CD
            EF
        """.asInput()
            ),
            `is`(
                """
                ACE
                BDF
            """.asInput()
            )
        )
    }

    @Test
    fun testRotateClockwise() {
        assertThat(
            rotateClockwise(
                """
            AB
            CD
            EF
        """.asInput()
            ),
            `is`(
                """
                ECA
                FDB
            """.asInput()
            )
        )
    }

    @Test
    fun testRotateCounterclockwise() {
        assertThat(
            rotateCounterClockwise(
                """
            AB
            CD
            EF
        """.asInput()
            ),
            `is`(
                """
                BDF
                ACE
            """.asInput()
            )
        )
    }
}