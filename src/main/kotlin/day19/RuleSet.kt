package day19

class RuleSet(ruleList: List<Rule>) {

    private val predicate: PartPredicate

    init {
        val branches = ruleList.flatMap { it.branches.entries }.associate { Pair(it.key, it.value) }
        predicate = branchToPredicate(branches["in"]!!, branches)
    }

    private fun branchToPredicate(branch: Branch, branches: Map<String, Branch>): PartPredicate {

        val result = mutableListOf<PartPredicate>()

        if (branch.resultIfTrue == "A") {
            result.add(branch.predicate)
        } else if (branch.resultIfTrue != "R") {
            result.add(
                branchToPredicate(
                    branches[branch.resultIfTrue]!!,
                    branches
                ).and(branch.predicate)
            )
        }

        if (branch.resultIfFalse == "A") {
            result.add(branch.predicate.negate())
        } else if (branch.resultIfFalse != "R") {
            result.add(
                branchToPredicate(
                    branches[branch.resultIfFalse]!!,
                    branches
                ).and(branch.predicate.negate())
            )
        }
        return OrPredicate(result)
    }

    fun accepted(part: Part): Boolean {
        return predicate.test(part)
    }

    override fun toString() = predicate.toString()
    fun numAcceptedParts(): Long {
        return predicate.numAcceptedParts()
    }
}