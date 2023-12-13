package day13

import println
import readInput
import split
import transpose

fun main() {
    val input = readInput("day13/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Long {
    val patterns = input.split { it.isEmpty() }
    return patterns.sumOf { score(it) }.toLong()
}

fun solvePart2(input: List<String>): Long {
    return input.size.toLong()
}

fun score(pattern: List<String>): Int {
    return findVerticalSplit(pattern) ?: ((findHorizontalSplit(pattern)!!) * 100)
}

fun findVerticalSplit(pattern: List<String>): Int? {
    return findHorizontalSplit(transpose(pattern))
}

fun findHorizontalSplit(pattern: List<String>): Int? {
    return (1..<pattern.size).firstOrNull { mirrorsAt(pattern, it) }
}

fun mirrorsAt(pattern: List<String>, index: Int): Boolean {
    val firstHalf = pattern.subList(0, index).reversed()
    val secondHalf = pattern.subList(index, pattern.size)
    return firstHalf.zip(secondHalf).all { it.first == it.second }
}

