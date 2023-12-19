package day19

import java.util.function.Predicate

class RuleSet(ruleList: List<Rule>) {

    private val predicates: List<Predicate<Part>>

    init {
        val branches = ruleList.flatMap { it.branches.entries }.associate { Pair(it.key, it.value) }
        predicates = branchToPredicateList(branches["in"]!!, branches)
    }

    private fun branchToPredicateList(branch: Branch, branches: Map<String, Branch>): List<Predicate<Part>> {
        val result = mutableListOf<Predicate<Part>>()
        if (branch.resultIfTrue == "A") {
            result.add(branch.predicate)
        } else if (branch.resultIfTrue != "R") {
            result.addAll(
                branchToPredicateList(
                    branches[branch.resultIfTrue]!!,
                    branches
                ).map { it.and(branch.predicate) })
        }

        if (branch.resultIfFalse == "A") {
            result.add(branch.predicate.negate())
        } else if (branch.resultIfFalse != "R") {
            result.addAll(
                branchToPredicateList(
                    branches[branch.resultIfFalse]!!,
                    branches
                ).map { it.and(branch.predicate.negate()) })
        }
        return result
    }

    fun accepted(part: Part): Boolean {
        return predicates.any { it.test(part) }
    }

    override fun toString() = predicates.joinToString("\n")
}