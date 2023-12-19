package day19

import java.util.*

class Rule private constructor(
    private val name: String,
    private val branches: Map<String, Branch>
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

            val branches = parseBranches(contents.split(","), name)
                .associateBy { it.name }

            return Rule(name, branches)
        }

        private fun parseBranches(input: List<String>, firstBranchName: String): List<Branch> {

            if (input.size < 2) {
                throw IllegalArgumentException()
            }

            val firstConditional = input[0]
            val predicate = parsePredicate(firstConditional.split(":")[0])
            val resultIfTrue = firstConditional.split(":")[1]

            if (input.size == 2) {
                return listOf(
                    Branch(
                        firstBranchName,
                        predicate,
                        resultIfTrue,
                        input[1]
                    )
                )
            }

            val nextBranchName = UUID.randomUUID().toString()
            return parseBranches(input.drop(1), nextBranchName)
                .plus(
                    Branch(
                        firstBranchName,
                        predicate,
                        resultIfTrue,
                        nextBranchName
                    )
                )
        }

        private fun parsePredicate(input: String): (Part) -> Boolean {
            val category = input.split("[<>]".toRegex())[0].let { Category.parse(it) }
            val threshold = input.split("[<>]".toRegex())[1].toLong()
            return if (input.contains("<")) { it ->
                it[category] < threshold
            } else { it ->
                it[category] > threshold
            }
        }
    }
}