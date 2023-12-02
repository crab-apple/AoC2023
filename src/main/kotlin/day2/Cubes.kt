package day2

class Cubes(private val data: Map<Color, Int>) {
    constructor(vararg pairs: Pair<Color, Int>) : this(mapOf(*pairs))

    fun get(color: Color): Int {
        return data[color] ?: 0
    }

    fun subsumes(other: Cubes): Boolean {
        return Color.entries.all { this.get(it) >= other.get(it) }
    }

    companion object {

        fun parse(s: String): Cubes {
            val data = s.split(", ").associate { it ->
                val quantity = it.split(" ").first().toInt()
                val color = it.split(" ").last().uppercase().let(Color::valueOf)
                color to quantity
            }
            return Cubes(data)
        }
    }
}
