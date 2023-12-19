package day19

import java.util.*

class Rule private constructor(
    private val name: String,
    val branches: Map<String, Branch>
) {

    fun name(): String {
        return name
    }

    fun evaluate(part: Part): String {
        return evaluateBranch(part, name)
    }

    private fun evaluateBranch(part: Part, branchName: String): String {
        val branch = branches[branchName] ?: return branchName
        val result: String = if (branch.predicate.test(part)) branch.resultIfTrue else branch.resultIfFalse
        return evaluateBranch(part, result)
    }

    companion object Parser {

        fun parse(input: String): Rule {
            val name = input.substringBefore("{")
            val contents = input.removePrefix(name).removeSurrounding("{", "}")

            val branches = mutableMapOf<String, Branch>()
            parseBranches(contents.split(","), name, branches)
            return Rule(name, branches)
        }

        private fun parseBranches(
            input: List<String>,
            firstBranchName: String,
            accumulator: MutableMap<String, Branch>
        ) {

            if (input.size < 2) {
                throw IllegalArgumentException()
            }

            val firstConditional = input[0]
            val predicate = parsePredicate(firstConditional.split(":")[0])
            val resultIfTrue = firstConditional.split(":")[1]

            val nextBranchName: String
            if (input.size == 2) {
                nextBranchName = input[1]
            } else {
                nextBranchName = UUID.randomUUID().toString()
                parseBranches(input.drop(1), nextBranchName, accumulator)
            }

            accumulator[firstBranchName] = Branch(
                predicate,
                resultIfTrue,
                nextBranchName
            )
        }

        private fun parsePredicate(input: String): PartPredicate {
            val category = input.split("[<>]".toRegex())[0].let { Category.parse(it) }
            val threshold = input.split("[<>]".toRegex())[1].toInt()
            return PartPredicate(category, threshold, input.first { it == '<' || it == '>' })
        }
    }
}