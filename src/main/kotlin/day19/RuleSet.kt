package day19

class RuleSet(ruleList: List<Rule>) {

    private val predicates: List<PartPredicate>

    init {
        val branches = ruleList.flatMap { it.branches.entries }.associate { Pair(it.key, it.value) }
        predicates = branchToPredicateList(branches["in"]!!, branches)
    }

    private fun branchToPredicateList(branch: Branch, branches: Map<String, Branch>): List<PartPredicate> {
        val result = mutableListOf<PartPredicate>()
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
            result.addAll(branch.predicate.negated())
        } else if (branch.resultIfFalse != "R") {
            result.addAll(
                branchToPredicateList(
                    branches[branch.resultIfFalse]!!,
                    branches
                ).flatMap { a -> branch.predicate.negated().map { a.and(it) } })
        }
        return result
    }

    fun accepted(part: Part): Boolean {
        return predicates.any { it.test(part) }
    }

    override fun toString() = predicates.joinToString("\n")
}