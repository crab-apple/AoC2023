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
}