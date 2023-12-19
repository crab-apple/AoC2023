package day19

class RuleSet(ruleList: List<Rule>) {

    private val branches: Map<String, Branch>

    init {
        branches = ruleList.flatMap { it.branches.entries }.associate { Pair(it.key, it.value) }
    }

    fun accepted(part: Part): Boolean {
        return evaluateBranch(part, "in") == "A"
    }

    private fun evaluateBranch(part: Part, branchName: String): String {
        val branch = branches[branchName] ?: return branchName
        val result: String = if (branch.predicate.test(part)) branch.resultIfTrue else branch.resultIfFalse
        return evaluateBranch(part, result)
    }
}