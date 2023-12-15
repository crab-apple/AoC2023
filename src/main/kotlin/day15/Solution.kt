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
    val boxes = (0..<256).map { Box() }

    for (op in input.split(",").map { parseOperation(it) }) {
        op.apply(boxes[op.boxNumber()])
    }

    return boxes.mapIndexed { index, box ->
        (index + 1) * box.focusingPower()
    }.sum()
}

abstract class Operation(private val label: String) {

    fun boxNumber() = hash(label)
    abstract fun apply(box: Box)
}

class LensAddition(private val label: String, private val focalLength: Int) : Operation(label) {

    override fun apply(box: Box) {
        box.addLens(label, focalLength)
    }
}

class LensRemoval(private val label: String) : Operation(label) {

    override fun apply(box: Box) {
        box.removeLens(label)
    }
}

fun parseOperation(s: String): Operation {
    val groups = Regex("([a-z]+)(.)(\\d*)").matchEntire(s)!!.groups
    val label = groups[1]?.value ?: throw IllegalArgumentException()
    val operationType = groups[2]?.value?.get(0) ?: throw IllegalArgumentException()
    val operand = groups[3]?.value?.ifBlank { null }?.toInt()
    return when (operationType) {
        '=' -> LensAddition(label, operand!!)
        '-' -> LensRemoval(label)
        else -> throw IllegalArgumentException()
    }
}

class Box {

    private val lenses = LinkedHashMap<String, Int>()

    fun addLens(label: String, focalLength: Int) {
        lenses[label] = focalLength
    }

    fun removeLens(label: String) {
        lenses.remove(label)
    }

    fun focusingPower(): Int = lenses.entries.mapIndexed { position, entry -> (position + 1) * entry.value }.sum()
}

fun hash(input: String): Int {
    return input.fold(0) { value, char -> ((value + char.code) * 17) % 256 }
}

