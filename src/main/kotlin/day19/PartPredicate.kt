package day19

class PartPredicate(private val ranges: Map<Category, IntRange>) {

    fun test(part: Part): Boolean {
        return ranges.all { it.value.contains(part[it.key]) }
    }

    override fun toString(): String {
        return ranges.map { "${it.key}${it.value}" }.joinToString()
    }

    fun and(other: PartPredicate): PartPredicate {
        return PartPredicate(Category.entries.associateWith { category ->
            val thisRange = ranges[category]!!
            val otherRange = other.ranges[category]!!
            IntRange(maxOf(thisRange.first, otherRange.first), minOf(thisRange.last, otherRange.last))
        })
    }

    fun negated(): List<PartPredicate> {

        val acceptingAll = Category.entries.associateWith { IntRange(0, 4000) }

        val result = mutableListOf<PartPredicate>()
        for (category in Category.entries) {
            val range = ranges[category]!!
            for (inversePart in invertRange(range)) {
                if (!inversePart.isEmpty()) {
                    result.add(PartPredicate(acceptingAll.plus(Pair(category, inversePart))))
                }
            }
        }
        return result
    }

    private fun invertRange(range: IntRange): List<IntRange> {
        return listOf(IntRange(0, range.first - 1), IntRange(range.last + 1, 4000))
    }

    companion object {

        fun of(category: Category, threshold: Int, operation: Char): PartPredicate {

            return PartPredicate(Category.entries.associateWith { cat ->
                if (cat == category) {
                    if (operation == '<') {
                        IntRange(0, threshold - 1)
                    } else {
                        IntRange(threshold + 1, 4000)
                    }
                } else {
                    IntRange(0, 4000)
                }
            })
        }

        fun parse(input: String): PartPredicate {
            val category = input.split("[<>]".toRegex())[0].let { Category.parse(it) }
            val threshold = input.split("[<>]".toRegex())[1].toInt()
            return of(category, threshold, input.first { it == '<' || it == '>' })
        }
    }
}