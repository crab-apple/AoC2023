package day6

data class Race(val time: Long, val previousRecord: Long) {

    fun numWaysToBeatRecord(): Long {
        return (0..time).count {
            it * (time - it) > previousRecord
        }.toLong()
    }

    companion object {

        fun parseRaceTable(input: List<String>): List<Race> {
            val times = input[0].removePrefix("Time:")
                .split(" ")
                .filter { it.isNotBlank() }
                .map { it.toLong() }

            val distances = input[1].removePrefix("Distance:")
                .split(" ")
                .filter { it.isNotBlank() }
                .map { it.toLong() }

            return times.zip(distances).map { pair -> Race(pair.first, pair.second) }
        }
    }
}
