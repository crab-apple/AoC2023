package day2

class Game(val id: Int, private val samples: Set<Cubes>) {

    fun bagIsPossible(cubes: Cubes): Boolean {
        return samples.all { cubes.subsumes(it) }
    }

    fun minimumPossibleBag(): Cubes {
        return Cubes(Color.entries.associateWith { color -> this.samples.maxOf { it.get(color) } })
    }

    companion object {

        fun parse(s: String): Game {
            val id = s.split(": ").first().split(" ").last().toInt()
            val samples = s.split(": ").last().split("; ").map { Cubes.parse(it) }.toSet()
            return Game(id, samples)
        }
    }
}
