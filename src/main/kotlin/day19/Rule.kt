package day19

import java.util.function.Predicate

class Rule private constructor(
    private val name: String,
    private val conditionals: List<Conditional>
) {

    fun name(): String {
        return name
    }

    fun evaluate(part: Part): String {
        for (conditional in conditionals) {
            if (conditional.predicate.test(part)) {
                return conditional.resultIfTrue
            }
        }
        throw RuntimeException("Cannot be")
    }

    companion object Parser {

        fun parse(input: String): Rule {
            val name = input.substringBefore("{")
            val contents = input.removePrefix(name).removeSurrounding("{", "}")
            val conditionals = contents.split(",").map { Conditional.parse(it) }

            return Rule(name, conditionals)
        }
    }

    private class Conditional(val predicate: Predicate<Part>, val resultIfTrue: String) {
        companion object Parser {

            fun parse(input: String): Conditional {
                return if (input.contains(":")) {
                    val predicate = input.split(":")[0].let { parsePredicate(it) }
                    val result = input.split(":")[1]
                    Conditional(predicate, result)
                } else {
                    Conditional({ true }, input)
                }
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
}