package day21

import utils.Position

class Reachabilities(map: Map<Position, Int>) {

    private val evens = map.values.filter { it % 2 == 0 }
    private val odds = map.values.filter { it % 2 == 1 }
    private val maxEvens: Int = evens.max()
    private val maxOdds: Int = odds.max()
    val max: Int = maxEvens.coerceAtLeast(maxOdds)

    private val cachedCounts = mutableMapOf<Long, Long>()

    fun countReachable(targetSteps: Long): Long {

        if (targetSteps % 2 == 0L) {
            if (targetSteps >= maxEvens) {
                return evens.count().toLong()
            }
        } else {
            if (targetSteps >= maxOdds) {
                return odds.count().toLong()
            }
        }
        return cachedCounts.computeIfAbsent(targetSteps) { countReachableUncached(targetSteps) }
    }

    private fun countReachableUncached(targetSteps: Long): Long {
        return if (targetSteps % 2 == 0L) {
            if (targetSteps >= maxEvens) {
                evens.count().toLong()
            } else {
                evens.count { it <= targetSteps }.toLong()
            }
        } else {
            if (targetSteps >= maxOdds) {
                odds.count().toLong()
            } else {
                odds.count { it <= targetSteps }.toLong()
            }
        }
    }
}