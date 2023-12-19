package day19

import java.util.function.Predicate

class PartPredicate(private val ranges: Map<Category, IntRange>) : Predicate<Part> {

    override fun test(part: Part): Boolean {
        return ranges.all { it.value.contains(part[it.key]) }
    }

    override fun toString(): String {
        return ranges.map { "${it.key}${it.value}" }.joinToString()
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