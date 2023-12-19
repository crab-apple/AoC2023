package day19

import java.util.function.Predicate

data class PartPredicate(val category: Category, val threshold: Int, val operation: Char) : Predicate<Part> {

    override fun test(part: Part): Boolean {
        return if (operation == '<')
            part[category] < threshold
        else
            part[category] > threshold
    }

    override fun toString(): String {
        return "$category$operation$threshold"
    }

    companion object {

        fun of(category: Category, threshold: Int, operation: Char) = PartPredicate(category, threshold, operation)

        fun parse(input: String): PartPredicate {
            val category = input.split("[<>]".toRegex())[0].let { Category.parse(it) }
            val threshold = input.split("[<>]".toRegex())[1].toInt()
            return of(category, threshold, input.first { it == '<' || it == '>' })
        }
    }
}