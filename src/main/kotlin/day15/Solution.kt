package day15

import println
import readInput

fun main() {
    val input = readInput("day15/input")
    solvePart1(input[0]).println()
    solvePart2(input[0]).println()
}

fun solvePart1(input: String): Int {
    return input.split(",").sumOf { hash(it) }
}

fun solvePart2(input: String): Int {
    val boxes = (0..<256).map { LinkedHashMap<String, Int>() }
    for (operation in input.split(",")) {
        if (operation.contains('=')) {
            val label = operation.substringBefore('=')
            val focalLength = operation.substringAfter('=').toInt()
            val boxNumber = hash(label)
            boxes[boxNumber][label] = focalLength
        } else {
            val label = operation.substringBefore('-')
            val boxNumber = hash(label)
            boxes[boxNumber].remove(label)
        }
    }

    return boxes.mapIndexed { index, box ->
        (index + 1) * box.entries.mapIndexed { position, entry -> (position+1) * entry.value }.sum()
    }.sum()
}

fun hash(input: String): Int {
    return input.fold(0) { value, char -> ((value + char.code) * 17) % 256 }
}

