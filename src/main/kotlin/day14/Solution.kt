package day14

import utils.Indexable
import utils.MutableGrid
import utils.println
import utils.readInput
import utils.rotateCounterClockwise
import utils.transpose

fun main() {
    val input = readInput("day14/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Long {
    return transpose(input).sumOf { bringRocksToStartAndScore(it) }.toLong()
}

fun solvePart2(input: List<String>): Long {

    // Find the cycle length
    val cycleLength: Int
    var cycleLengthCandidate = 1
    while (true) {
        println("Testing candidate $cycleLengthCandidate")
        var workingGrid = input
        val results = mutableListOf<Int>()
        for (i in 0..10) {
            workingGrid = cycle(workingGrid, cycleLengthCandidate)
            results.add(score(rotateCounterClockwise(workingGrid)))
        }
        if (results.takeLast(5).distinct().size == 1) {
            cycleLength = cycleLengthCandidate
            break
        }
        cycleLengthCandidate++
    }

    var workingGrid = input
    workingGrid = cycle(workingGrid, 1_000_000_000 % cycleLength)
    workingGrid = cycle(workingGrid, cycleLength * 10)
    return score(rotateCounterClockwise(workingGrid)).toLong()
}

fun cycle(grid: List<String>, times: Int): List<String> {

    val mutableGrid = MutableGrid.of(grid.joinToString("\n"))

    for (i in 1..times) {
        cycle(mutableGrid)
    }

    return mutableGrid.toString().split("\n")
}

fun cycle(grid: List<String>): List<String> {
    val mutableGrid = MutableGrid.of(grid.joinToString("\n"))

    cycle(mutableGrid)

    return mutableGrid.toString().split("\n")
}

private fun cycle(mutableGrid: MutableGrid<Char>) {
    mutableGrid.rotateCounterClockwise()

    bringRocksToStart(mutableGrid)

    mutableGrid.rotateClockwise()

    bringRocksToStart(mutableGrid)

    mutableGrid.rotateClockwise()

    bringRocksToStart(mutableGrid)

    mutableGrid.rotateClockwise()

    bringRocksToStart(mutableGrid)

    mutableGrid.rotateClockwise()
    mutableGrid.rotateClockwise()
}

fun bringRocksToStartAndScore(s: String): Int {
    return score(bringRocksToStart(s))
}

private fun score(grid: List<String>): Int = grid.sumOf { score(it) }

private fun score(s: String): Int {
    return s.mapIndexed { index, ch -> if (ch == 'O') s.length - index else 0 }.sum()
}

fun bringRocksToStart(s: String): String {

    val arr = Indexable.of(s.toCharArray().toTypedArray())

    bringRocksToStart(arr)

    return arr.joinToString("")
}

private fun bringRocksToStart(arr: Indexable<Char>) {
    while (true) {
        var movedSomething = false
        for (i in 0..<arr.size() - 1)
            if (arr[i] == '.' && arr[i + 1] == 'O') {
                swap(arr, i, i + 1)
                movedSomething = true
            }
        if (!movedSomething) break
    }
}

private fun <T> swap(arr: Indexable<T>, indexA: Int, indexB: Int) {
    val temp = arr[indexA]
    arr[indexA] = arr[indexB]
    arr[indexB] = temp
}

fun bringRocksToStart(grid: MutableGrid<Char>) {
    grid.rows().forEach {
        val copy = Indexable.of(it.toMutableList().toTypedArray())
        bringRocksToStart(copy)
        for (i in 0..<copy.size()) {
            it[i] = copy[i]
        }
    }
}




