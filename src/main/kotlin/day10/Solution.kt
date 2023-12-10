package day10

import println
import readInput

fun main() {
    val input = readInput("day10/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Int {
    val ground = Ground.parse(input)
    return ground.findFullPipe().size() / 2
}

fun solvePart2(input: List<String>): Int {
    return input.size
}

class Ground(private val contents: Map<Point, Tile>) {

    fun findFullPipe(): PipeSection {
        var section = PipeSection(listOf(startingPosition()))
        do {
            section = section.plus(findNextTile(section))
        } while (findNextTile(section).char != 'S')
        return section
    }

    private fun startingPosition(): Tile {
        return contents.values.first { it.char == 'S' }
    }

    private fun findNextTile(section: PipeSection): Tile {
        val lastTile = section.last()
        if (lastTile.char == 'S') {
            listOf(
                lastTile.point.east() to "J7-",
                lastTile.point.west() to "LF-",
                lastTile.point.north() to "F7|",
                lastTile.point.south() to "JL|",
            ).forEach {
                val candidatePoint = it.first
                val acceptableValues = it.second
                contents[candidatePoint]?.let { candidateTile ->
                    if (acceptableValues.contains(candidateTile.char)) {
                        return candidateTile
                    }
                }
            }
            throw RuntimeException("Shouldn't reach this point")
        }

        val connectedPositions = when (lastTile.char) {
            'J' -> setOf(lastTile.point.north(), lastTile.point.west())
            'L' -> setOf(lastTile.point.north(), lastTile.point.east())
            'F' -> setOf(lastTile.point.south(), lastTile.point.east())
            '7' -> setOf(lastTile.point.south(), lastTile.point.west())
            '-' -> setOf(lastTile.point.east(), lastTile.point.west())
            '|' -> setOf(lastTile.point.north(), lastTile.point.south())
            else -> throw RuntimeException()
        }

        return connectedPositions.map { contents[it] }.first { it != section.secondToLast() }!!
    }

    companion object {

        fun parse(input: List<String>): Ground {
            return input.flatMapIndexed { numRow, row ->
                row.mapIndexed { numCol, ch ->
                    val point = Point(numCol, numRow)
                    point to Tile(point, ch)
                }
            }
                .associate { it }
                .let { Ground(it) }
        }
    }
}

data class Tile(val point: Point, val char: Char) {
}

data class PipeSection(private val tiles: List<Tile>) {

    fun plus(tile: Tile) = PipeSection(tiles.plus(tile))

    fun last() = tiles.last()
    fun secondToLast() = tiles.dropLast(1).last()

    fun size() = tiles.size
}

data class Point(val x: Int, val y: Int) {

    fun plus(x: Int, y: Int) = Point(this.x + x, this.y + y)

    fun adjacentPoints() = setOf(
        plus(-1, 0),
        plus(0, -1),
        plus(0, 1),
        plus(1, 0),
    )

    fun east() = plus(1, 0)
    fun west() = plus(-1, 0)
    fun north() = plus(0, -1)
    fun south() = plus(0, 1)
}
