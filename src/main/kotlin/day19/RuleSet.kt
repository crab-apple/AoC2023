package day19

class RuleSet(ruleList: List<Rule>) {

    private val rules: Map<String, Rule>

    init {
        rules = ruleList.associateBy { it.name() }
    }

    fun accepted(part: Part): Boolean {
        return evaluate(part, "in")
    }

    private fun evaluate(part: Part, ruleName: String): Boolean {
        val rule = rules[ruleName]!!
        return when (val result = rule.evaluate(part)) {
            "A" -> true
            "R" -> false
            else -> evaluate(part, result)
        }
    }
}