package day2

import println
import readInput

fun main() {
    val input = readInput("day2/input")
    Part1Solver.solve(input).println()
}

object Part1Solver {
    fun solve(input: List<String>): Int = input.map { Game.parse(it) }
        .filter { it.bagIsPossible(Cubes(Color.RED to 12, Color.GREEN to 13, Color.BLUE to 14)) }
        .sumOf { it.id }
}

enum class Color { RED, GREEN, BLUE }

class Game(val id: Int, private val samples: Set<Cubes>) {
    fun bagIsPossible(cubes: Cubes): Boolean {
        return samples.all { cubes.subsumes(it) }
    }

    companion object {
        fun parse(s: String): Game {
            val id = s.split(": ").first().split(" ").last().toInt()
            val samples = s.split(": ").last().split("; ").map { Cubes.parse(it) }.toSet()
            return Game(id, samples)
        }
    }
}

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

