package day3

class Schematic(private val lines: List<String>) {

    fun numbers(): Set<Number> {
        return lines.flatMapIndexed { lineNumber, line ->
            "\\d+".toRegex().findAll(line)
                .map { Number(it.value.toInt(), lineNumber, it.range.first, it.range.last) }
        }.toSet()
    }

    fun symbols(): Set<Symbol> {
        return lines.flatMapIndexed { lineNumber, line ->
            "[^\\d.]".toRegex().findAll(line)
                .map { Symbol(it.value.first(), Pair(lineNumber, it.range.first)) }
        }.toSet()
    }

    data class Number(val value: Int, val row: Int, val start: Int, val end: Int) {

        fun positionIsAdjacent(position: Pair<Int, Int>): Boolean {
            val positionRow = position.first
            val positionColumn = position.second
            if (positionRow < row - 1) {
                return false
            }
            if (positionRow > row + 1) {
                return false
            }
            if (positionColumn < start - 1) {
                return false
            }
            if (positionColumn > end + 1) {
                return false
            }
            return true
        }
    }

    data class Symbol(val value: Char, val position: Pair<Int, Int>)
}
