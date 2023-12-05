package day5

data class AlmanacMap(private val ranges: Set<MappedRange>) {

    fun map(input: Long): Long {
        return ranges.firstNotNullOfOrNull { it.map(input) } ?: input
    }

    data class MappedRange(private val sourceStart: Long, private val destinationStart: Long, private val rangLen: Long) {

        fun map(input: Long): Long? {
            if ((sourceStart until sourceStart + rangLen).contains(input)) {
                return input - sourceStart + destinationStart
            }
            return null
        }
    }

    companion object {

        fun parse(input: List<String>): AlmanacMap {
            return input.drop(1)
                .map { line ->
                    val components = line.split(" ").map { it.toLong() }
                    MappedRange(components[1], components[0], components[2])
                }
                .let { AlmanacMap(it.toSet()) }
        }
    }
}
