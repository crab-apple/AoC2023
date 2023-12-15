package day10

import day10.Direction.EAST
import day10.Direction.NORTH
import day10.Direction.SOUTH
import day10.Direction.WEST
import day10.Laterality.LEFT
import day10.Laterality.RIGHT
import utils.println
import utils.readInput

fun main() {
    val input = readInput("day10/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Long {
    val ground = Ground.parse(input)
    return ground.findFullPipe().size().toLong() / 2
}

fun solvePart2(input: List<String>): Long {
    val ground = Ground.parse(input)
    val loop = ground.findFullPipe().withLateralityRight()

    // Find some points that are inside the loop or part of the loop itself, but definitely not outside the loop
    val innerPointCandidates: List<Point> = loop.tiles.zip(loop.directions()).flatMap { pair ->
        val tile = pair.first
        val direction = pair.second
        val p = tile.point
        if (tile.char == '-') {
            if (direction == EAST) {
                listOf(p.south(), p.south().west(), p.south().east())
            } else {
                listOf(p.north(), p.north().west(), p.north().east())
            }
        } else if (tile.char == '|') {
            if (direction == NORTH) {
                listOf(p.east(), p.north().east(), p.south().east())
            } else {
                listOf(p.west(), p.north().west(), p.south().west())
            }
        } else if (tile.char == 'L') {
            if (direction == NORTH) {
                listOf(p.north().east())
            } else {
                listOf(p.north().west(), p.west(), p.south().west(), p.south(), p.south().east())
            }
        } else if (tile.char == 'J') {
            if (direction == NORTH) {
                listOf(p.north().east(), p.east(), p.south().east(), p.south(), p.south().west())
            } else {
                listOf(p.north().west())
            }
        } else if (tile.char == '7') {
            if (direction == SOUTH) {
                listOf(p.south().west())
            } else {
                listOf(p.south().east(), p.east(), p.north().east(), p.north(), p.north().west())
            }
        } else if (tile.char == 'F') {
            if (direction == SOUTH) {
                listOf(p.south().west(), p.west(), p.north().west(), p.north(), p.north().east())
            } else {
                listOf(p.south().east())
            }
        } else if (tile.char == 'S') {
            // Is it safe to ignore?
            listOf()
        } else {
            throw RuntimeException()
        }
    }

    // Remove points that are part of the loop itself
    val loopPoints = loop.tiles.map { it.point }.toSet()
    val innerPointSeeds = innerPointCandidates.toSet().minus(loopPoints)

    // Expand to include all non-loop points accessible from those
    val innerPoints = mutableSetOf<Point>()
    val queue = mutableListOf<Point>()
    queue.addAll(innerPointSeeds)
    while (queue.isNotEmpty()) {
        val next = queue.removeFirst()
        if (innerPoints.add(next)) {
            next.adjacentPoints().filter { !loopPoints.contains(it) }.forEach() { queue.add(it) }
        }
    }

    return innerPoints.size.toLong()
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

data class PipeSection(val tiles: List<Tile>) {

    fun plus(tile: Tile) = PipeSection(tiles.plus(tile))

    fun last() = tiles.last()
    fun secondToLast() = tiles[tiles.size - 2]

    fun size() = tiles.size

    /**
     * Indicates whether the loop is left-handed or right-handed
     */
    fun laterality(): Laterality {
        val directions: List<Direction> = directions()
        val turns: List<Laterality> = directions.mapIndexedNotNull { index, direction ->
            val nextDirection = directions[(index + 1) % directions.size]
            Laterality.entries.firstOrNull { direction.turn(it) == nextDirection }
        }

        val numRights = turns.count { it == RIGHT }
        val numLefts = turns.count { it == LEFT }

        if (numRights == numLefts + 4) {
            return RIGHT
        }
        if (numLefts == numRights + 4) {
            return LEFT
        }
        throw RuntimeException()
    }

    fun directions(): List<Direction> {
        val directions: List<Direction> = tiles.mapIndexed { index, tile ->
            val nextTile = tiles[(index + 1) % tiles.size]
            Direction.entries.first { tile.point.neighbour(it) == nextTile.point }
        }
        return directions
    }

    fun withLateralityRight(): PipeSection {
        if (laterality() == RIGHT) {
            return this
        }
        return PipeSection(listOf(tiles.first()).plus(tiles.drop(1).reversed()))
    }
}

data class Point(val x: Int, val y: Int) {

    fun plus(x: Int, y: Int) = Point(this.x + x, this.y + y)

    fun adjacentPoints() = setOf(
        north(),
        north().east(),
        east(),
        south().east(),
        south(),
        south().west(),
        west(),
        north().west()
    )

    fun east() = neighbour(EAST)
    fun west() = neighbour(WEST)
    fun north() = neighbour(NORTH)
    fun south() = neighbour(SOUTH)

    fun neighbour(direction: Direction): Point {
        return when (direction) {
            EAST -> plus(1, 0)
            WEST -> plus(-1, 0)
            NORTH -> plus(0, -1)
            SOUTH -> plus(0, 1)
        }
    }
}

enum class Laterality { LEFT, RIGHT }

enum class Direction() {

    NORTH, EAST, SOUTH, WEST;

    fun turn(laterality: Laterality): Direction {
        val delta = when (laterality) {
            RIGHT -> 1
            LEFT -> -1
        }
        return Direction.entries[(this.ordinal + delta + Direction.entries.size) % Direction.entries.size]
    }
}
