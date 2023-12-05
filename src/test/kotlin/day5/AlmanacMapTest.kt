package day5

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import kotlin.test.Test

class AlmanacMapTest {

    @Test
    fun testMap() {

        val almanacMap = AlmanacMap.parse(
            """
            seed-to-soil map:
            50 98 2
            52 50 48
        """.trimIndent().lines()
        )

        assertThat(almanacMap.map(79), equalTo(81))
        assertThat(almanacMap.map(14), equalTo(14))
        assertThat(almanacMap.map(55), equalTo(57))
        assertThat(almanacMap.map(13), equalTo(13))
    }
}