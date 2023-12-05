package day5

data class AlmanacMap(private val ranges: Set<MappedRange>) {

    fun map(input: Long): Long {
        return ranges.firstNotNullOfOrNull { it.map(input) } ?: input
    }

    fun mapRange(input: LongRange): List<LongRange> {

        var allMappedDests = RangeSet()
        var allMappedSources = RangeSet()
        var allNonMappedSources = RangeSet()

        ranges.forEach {
            val mappedDests = it.mapRange(input).mappedDests
            if (!mappedDests.isEmpty()) {
                allMappedDests = allMappedDests.plus(mappedDests)
            }
            val mappedSources = it.mapRange(input).mappedSources
            if (!mappedSources.isEmpty()) {
                allMappedSources = allMappedSources.plus(mappedSources)
            }
            allNonMappedSources = allNonMappedSources.plus(it.mapRange(input).notMappedSources)
        }

        val allNonMappedClean = allNonMappedSources.minus(allMappedSources)

        return allMappedDests.ranges.plus(allNonMappedClean.ranges).distinct().sortedBy { it.first }
    }

    data class RangeSet(val ranges: Set<LongRange>) {

        fun plus(range: LongRange) = RangeSet(ranges.plusElement(range))

        fun plus(ranges: Collection<LongRange>) = RangeSet(this.ranges.plus(ranges))

        fun minus(ranges: RangeSet): RangeSet {
            return ranges.ranges.fold(this) { rs, range -> rs.minus(range) }
        }

        fun minus(range: LongRange): RangeSet = RangeSet(ranges.flatMap { it.remove(range) }.toSet())

        constructor() : this(emptySet())
    }

    data class MappedRange(
        private val sourceStart: Long,
        private val destinationStart: Long,
        private val rangLen: Long
    ) {

        fun map(input: Long): Long? {
            if ((sourceStart until sourceStart + rangLen).contains(input)) {
                return input + offset()
            }
            return null
        }

        private fun offset() = destinationStart - sourceStart

        fun mapRange(input: LongRange): MapRangeResult {
            val acceptingRange = sourceStart until sourceStart + rangLen
            val match = input.intersectRange(acceptingRange)
            val mappedRange = match.first + offset()..match.last + offset()

            val notMapped = mutableListOf<LongRange>()
            if (input.first < match.first) {
                val element = input.first..minOf(input.last, match.first - 1)
                if (!element.isEmpty()) {
                    notMapped.add(element)
                }
            }
            if (input.last > match.last) {
                val element = maxOf(input.first, match.last + 1)..input.last
                if (!element.isEmpty()) {
                    notMapped.add(element)
                }
            }

            return MapRangeResult(mappedRange, match, notMapped)
        }

        data class MapRangeResult(
            val mappedDests: LongRange,
            val mappedSources: LongRange,
            val notMappedSources: List<LongRange>
        )
    }

    companion object {

        fun parse(input: List<String>): AlmanacMap {
            return input.drop(1)
                .map { line ->
                    val components = line.split(" ").map { it.toLong() }
                    MappedRange(components[1], components[0], components[2])
                }
                .let { AlmanacMap(it.toSet()) }
        }
    }
}

private fun LongRange.remove(other: LongRange): List<LongRange> {

    val before = this.first..minOf(this.last, other.first - 1)
    val after = maxOf(other.last + 1, this.first)..this.last
    val result = mutableListOf<LongRange>()
    if (!before.isEmpty()) {
        result.add(before)
    }
    if (!after.isEmpty()) {
        result.add(after)
    }
    return result;
}

private fun LongRange.intersectRange(other: LongRange): LongRange {
    return maxOf(this.first, other.first)..minOf(this.last, other.last)
}
