package day19

data class RangeSetPredicate(private val ranges: Map<Category, IntRange>) : PartPredicate {

    override fun test(part: Part): Boolean {
        return ranges.all { it.value.contains(part[it.key]) }
    }

    override fun toString(): String {
        return ranges.map { "${it.key}${it.value}" }.joinToString()
    }

    override fun and(other: PartPredicate): PartPredicate {
        if (other is RangeSetPredicate) {
            return RangeSetPredicate(Category.entries.associateWith { category ->
                val thisRange = ranges[category]!!
                val otherRange = other.ranges[category]!!
                IntRange(maxOf(thisRange.first, otherRange.first), minOf(thisRange.last, otherRange.last))
            })
        }
        if (other is OrPredicate) {
            return OrPredicate(other.predicates.map { and(it) })
        }
        throw RuntimeException("Cannot be")
    }

    override fun negate(): PartPredicate {

        val acceptingAll = Category.entries.associateWith { IntRange(1, 4000) }

        val result = mutableListOf<RangeSetPredicate>()
        for (category in Category.entries) {
            val range = ranges[category]!!
            for (inversePart in invertRange(range)) {
                result.add(RangeSetPredicate(acceptingAll.plus(Pair(category, inversePart))))
            }
        }
        return OrPredicate(result.filter { !it.isEmpty() })
    }

    override fun numAcceptedParts(): Long {
        return ranges.values.fold(1L) { product, range -> product * (range.last - range.first + 1) }
    }

    private fun invertRange(range: IntRange): List<IntRange> {
        return listOf(IntRange(1, range.first - 1), IntRange(range.last + 1, 4000)).filter { !it.isEmpty() }
    }

    override fun isEmpty() = ranges.values.any { it.isEmpty() }

    companion object {

        fun of(category: Category, threshold: Int, operation: Char): RangeSetPredicate {

            return RangeSetPredicate(Category.entries.associateWith { cat ->
                if (cat == category) {
                    if (operation == '<') {
                        IntRange(1, threshold - 1)
                    } else {
                        IntRange(threshold + 1, 4000)
                    }
                } else {
                    IntRange(1, 4000)
                }
            })
        }

        fun parse(input: String): RangeSetPredicate {
            val category = input.split("[<>]".toRegex())[0].let { Category.parse(it) }
            val threshold = input.split("[<>]".toRegex())[1].toInt()
            return of(category, threshold, input.first { it == '<' || it == '>' })
        }

        fun all() = RangeSetPredicate(Category.entries.associateWith { IntRange(1, 4000) })
    }
}