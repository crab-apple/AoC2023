package day14

import utils.Direction
import utils.Direction.EAST
import utils.Direction.NORTH
import utils.Direction.SOUTH
import utils.Direction.WEST
import utils.Indexable
import utils.Position
import utils.println
import utils.readInput

fun main() {
    val input = readInput("day14/input")
//    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Long {
    val grid = Grid(input.joinToString("\n"))
    grid.bringRocksToStart(NORTH)
    return grid.northLoad().toLong()
}

fun solvePart2(input: List<String>): Long {

    // Cycle until we find the same position twice
    val seen = mutableSetOf<String>()
    val grid_ = Grid(input.joinToString("\n"))
    while (true) {
        if (!seen.add(grid_.toString())) {
            break;
        }
        cycle(grid_)
    }
    println("Found repetition after seeing ${seen.size} positions")
    return 0

    // Find the cycle length
    val cycleLength: Int
    var cycleLengthCandidate = 1
    while (true) {
        println("Testing candidate $cycleLengthCandidate")
        val grid = Grid(input.joinToString("\n"))
        val results = mutableListOf<Int>()
        for (i in 0..10) {
            for (i in 1..cycleLengthCandidate) {
                cycle(grid)
            }
            results.add(grid.northLoad())
        }
        if (results.takeLast(5).distinct().size == 1) {
            cycleLength = cycleLengthCandidate
            break
        }
        cycleLengthCandidate++
    }

    val grid = Grid(input.joinToString("\n"))
    for (i in 1..1_000_000_000 % cycleLength) {
        cycle(grid)
    }
    for (i in 1..cycleLength * 10) {
        cycle(grid)
    }
    return grid.northLoad().toLong()
}

fun cycle(gridStr: List<String>): List<String> {
    val grid = Grid(gridStr.joinToString("\n"))
    cycle(grid)
    return grid.toString().split("\n")
}

private fun cycle(grid: Grid) {
    grid.bringRocksToStart(NORTH)
    grid.bringRocksToStart(WEST)
    grid.bringRocksToStart(SOUTH)
    grid.bringRocksToStart(EAST)
}

fun bringRocksToStart(s: String): String {

    val arr = Indexable.of(s.toCharArray().toTypedArray())

    val grid = Grid(arr)
    grid.bringRocksToStart(WEST)

    return grid.toString()
}

class Grid(str: String) {

    constructor(arr: Indexable<Char>) : this(arr.joinToString(""))

    private val numRows: Int
    private val numCols: Int
    private val stopPoints: Set<Position>
    private var currentRockPositions: Set<Position>

    init {
        val lines = str.lines()
        numRows = lines.size
        numCols = lines[0].length
        stopPoints =
            lines.flatMapIndexed { rowNum, line -> line.toList().indexesOf('#').map { Position(rowNum, it) } }
                .toSet()
        currentRockPositions =
            lines.flatMapIndexed { rowNum, line -> line.toList().indexesOf('O').map { Position(rowNum, it) } }
                .toSet()
    }

    fun bringRocksToStart(direction: Direction) {

        fun withinBounds(pos: Position): Boolean {
            return pos.col in 0..<numCols && pos.row in 0..<numRows
        }

        val newRockPositions = mutableSetOf<Position>()

        val sorting: (Position) -> Int = {
            when (direction) {
                WEST -> it.col
                EAST -> -it.col
                NORTH -> it.row
                SOUTH -> -it.row
            }
        }

        currentRockPositions.sortedBy(sorting).forEach { rock ->
            var current = rock
            var candidate = current.neighbour(direction)
            while (withinBounds(candidate) && !stopPoints.contains(candidate) && !newRockPositions.contains(candidate)) {
                current = candidate
                candidate = current.neighbour(direction)
            }
            newRockPositions.add(current)
        }

        currentRockPositions = newRockPositions.map { it }.toSet()
    }

    override fun toString(): String {
        val allPositions = (0..<numRows).map { numRow -> (0..<numCols).map { numCol -> Position(numRow, numCol) } }

        return allPositions.map { rowPositions ->
            rowPositions.map { elementAt(it) }.joinToString("")
        }.joinToString("\n")
    }

    private fun elementAt(position: Position): Char {
        return if (stopPoints.contains(position)) {
            '#'
        } else if (currentRockPositions.contains(position)) {
            'O'
        } else {
            '.'
        }
    }

    fun northLoad(): Int = currentRockPositions.sumOf { numRows - it.row }
}

private fun <T> Iterable<T>.indexesOf(c: T) = withIndex().filter { it.value == c }.map { it.index }




