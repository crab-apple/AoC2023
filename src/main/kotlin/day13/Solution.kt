package day13

import utils.println
import utils.readInput
import utils.split
import utils.transpose

fun main() {
    val input = readInput("day13/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Long {
    val patterns = input.split { it.isEmpty() }
    return patterns.sumOf { score(it)!! }.toLong()
}

fun solvePart2(input: List<String>): Long {
    val patterns = input.split { it.isEmpty() }
    return patterns.sumOf { scoreWithSmudge(it) }.toLong()
}

fun scoreWithSmudge(pattern: List<String>): Int {
    val regularScore = score(pattern)
    return (0..<pattern.sumOf { it.length }).flatMap { scores(smudge(pattern, it)) }.first { it != regularScore }
}

fun smudge(pattern: List<String>, index: Int): List<String> {
    val rowLength = pattern[0].length
    val rowNumToSmudge = index / rowLength
    val colNumToSmudge = index % rowLength
    return pattern.mapIndexed { rowNum, row ->
        row.mapIndexed { colNum, char ->
            if (rowNum == rowNumToSmudge && colNum == colNumToSmudge) smudge(char) else char
        }.joinToString("")
    }
}

fun smudge(char: Char) = if (char == '.') '#' else '.'

fun score(pattern: List<String>): Int? {
    return scores(pattern).firstOrNull()
}

fun scores(pattern: List<String>): Set<Int> {
    return findVerticalSplits(pattern).plus(findHorizontalSplits(pattern).map { it * 100 }).toSet()
}

fun findVerticalSplits(pattern: List<String>): Set<Int> {
    return findHorizontalSplits(transpose(pattern))
}

fun findHorizontalSplits(pattern: List<String>): Set<Int> {
    return (1..<pattern.size).filter { mirrorsAt(pattern, it) }.toSet()
}

fun mirrorsAt(pattern: List<String>, index: Int): Boolean {
    val firstHalf = pattern.subList(0, index).reversed()
    val secondHalf = pattern.subList(index, pattern.size)
    return firstHalf.zip(secondHalf).all { it.first == it.second }
}

