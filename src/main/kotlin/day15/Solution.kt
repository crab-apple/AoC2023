package day15

import println
import readInput

fun main() {
    val input = readInput("day15/input")
    solvePart1(input[0]).println()
    solvePart2(input).println()
}

fun solvePart1(input: String): Int {
    return input.split(",").sumOf { hash(it) }
}

fun solvePart2(input: List<String>): Long {
    return input.size.toLong()
}

fun hash(input: String): Int {
    return input.fold(0) { value, char -> ((value + char.code) * 17) % 256 }
}

