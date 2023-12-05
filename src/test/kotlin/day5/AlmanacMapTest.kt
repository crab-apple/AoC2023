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

    @Test
    fun `test map range without match to existing maps`() {

        val almanacMap = AlmanacMap.parse(
            """
            seed-to-soil map:
            50 98 2
            52 50 48
        """.trimIndent().lines()
        )

        assertThat(almanacMap.mapRange(10L..13), equalTo(listOf(10L..13)))
    }

    @Test
    fun `test map range overlapping with existing maps, distinct outputs`() {

        val almanacMap = AlmanacMap.parse(
            """
            seed-to-soil map:
            12 10 10
        """.trimIndent().lines()
        )

        assertThat(
            almanacMap.mapRange(0L..15), equalTo(
                listOf(
                    0L..9,
                    12L..17,
                )
            )
        )
    }

    @Test
    fun `test map range with multiple maps, one of them matches`() {

        val almanacMap = AlmanacMap.parse(
            """
            seed-to-soil map:
            124 123 10
            20 10 100
        """.trimIndent().lines()
        )

        assertThat(almanacMap.mapRange(12L..13), equalTo(listOf(22L..23)))
    }
}