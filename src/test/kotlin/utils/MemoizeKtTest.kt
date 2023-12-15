package utils

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

class MemoizedTest {

    @Test
    fun testMemoize() {

        var invocationCount = 0

        fun sum(a: Int, b: Int): Int {
            invocationCount++
            return a + b
        }

        val memoizedSum = { p: Pair<Int, Int> -> sum(p.first, p.second) }.memoize()

        assertThat(memoizedSum(Pair(2, 3)), `is`(5))
        assertThat(invocationCount, `is`(1))

        assertThat(memoizedSum(Pair(3, 3)), `is`(6))
        assertThat(invocationCount, `is`(2))

        assertThat(memoizedSum(Pair(2, 3)), `is`(5))
        assertThat(invocationCount, `is`(2))
    }

    @Test
    fun testClearCache() {

        var invocationCount = 0

        fun sum(a: Int, b: Int): Int {
            invocationCount++
            return a + b
        }

        val memoizedSum = { p: Pair<Int, Int> -> sum(p.first, p.second) }.memoize()

        assertThat(memoizedSum(Pair(2, 3)), `is`(5))
        assertThat(memoizedSum(Pair(2, 3)), `is`(5))
        assertThat(memoizedSum(Pair(2, 3)), `is`(5))
        assertThat(invocationCount, `is`(1))

        memoizedSum.clearCache()

        assertThat(memoizedSum(Pair(2, 3)), `is`(5))
        assertThat(memoizedSum(Pair(2, 3)), `is`(5))
        assertThat(memoizedSum(Pair(2, 3)), `is`(5))
        assertThat(invocationCount, `is`(2))
    }
}