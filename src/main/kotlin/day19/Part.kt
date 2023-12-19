package day19

class Part private constructor(private val map: Map<Category, Long>) {

    operator fun get(category: Category): Long {
        return map[category]!!
    }

    fun addRatings(): Long {
        return map.values.sum()
    }

    companion object Parser {

        fun parse(input: String): Part {
            val map = input.removeSurrounding("{", "}")
                .split(",").associate { assignment ->
                    val category = assignment.split("=")[0].let { Category.parse(it) }
                    val value = assignment.split("=")[1].toLong()
                    category to value
                }
            return Part(map)
        }
    }
}