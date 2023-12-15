package utils

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import kotlin.test.Test

class CollectionUtilsTest {

    @Test
    fun testSplitList() {
        val input = listOf(
            "",
            "foo",
            "",
            "foo",
            "bar",
            "",
            "foobar",
            "foobarbaz"
        )

        assertThat(
            input.split { it.isBlank() }, equalTo(
                listOf(
                    listOf("foo"),
                    listOf("foo", "bar"),
                    listOf("foobar", "foobarbaz"),
                )
            )
        )
    }
}