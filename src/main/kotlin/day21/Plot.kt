package day21

import utils.Position
import java.util.*

class Plot(input: String) {

    private val lines = input.lines().map { it.toList() }
    val allPositions = lines.indices.flatMap { row -> lines[0].indices.map { Position(row, it) } }.toSet()
    private val rocks = allPositions.filter { valAt(it) == '#' }.toSet()
    fun valAt(pos: Position) = lines[pos.row.toInt()][pos.col.toInt()]

    fun computeReachabilities(startingPosition: Position): Reachabilities {
        val reachabilities = mutableMapOf<Position, Int>()
        val pendingToVisit = LinkedList<Pair<Position, Int>>()

        pendingToVisit.add(Pair(startingPosition, 0))

        while (pendingToVisit.isNotEmpty()) {
            val current = pendingToVisit.remove()
            val pos = current.first
            val steps = current.second
            if (reachabilities.putIfAbsent(pos, steps) != null) {
                continue
            }
            pos.allNeighbours()
                .filter { allPositions.contains(it) }
                .filter { !rocks.contains(it) }
                .forEach {
                    pendingToVisit.add(Pair(it, steps + 1))
                }
        }
        return Reachabilities(reachabilities)
    }
}