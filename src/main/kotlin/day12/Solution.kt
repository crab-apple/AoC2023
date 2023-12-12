package day12

import println
import readInput

fun main() {
    val input = readInput("day12/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: List<String>): Long {
    return input.sumOf { line ->
        val str = line.split(" ").first()
        val groups = line.split(" ").last().split(",").map { it.toInt() }
        countArrangements(str, groups)
    }
}

fun solvePart2(input: List<String>): Long {
    return input.sumOf { line ->
        val str = repeatFiveTimes(line.split(" ").first(), '?')
        val groups = repeatFiveTimes(line.split(" ").last(), ',').split(",").map { it.toInt() }
        countArrangements(str, groups)
    }
}

private fun repeatFiveTimes(input: String, delimiter: Char): String {
    return input.plus(delimiter).repeat(5).dropLast(1)
}

fun repeatString(input: String, times: Int): String {
    return repeat(input.toList(), times, '?').joinToString("")
}

fun <T> repeat(input: List<T>, times: Int, delimiter: T? = null): List<T> {
    val result = mutableListOf<T>()
    for (i in 1..times) {
        result.addAll(input)
        if (i < times && delimiter != null) {
            result.add(delimiter)
        }
    }
    return result
}

fun findPossibleStrings(s: String): List<String> {
    val accumulator = mutableListOf<String>()
    findPossibleStrings(s, accumulator)
    return accumulator
}

fun findPossibleStrings(s: String, accumulator: MutableList<String>) {
    if (!s.contains('?')) {
        accumulator.add(s)
        return
    }
    findPossibleStrings(s.replaceFirst('?', '.'), accumulator)
    findPossibleStrings(s.replaceFirst('?', '#'), accumulator)
}

fun countArrangements(input: String, groups: List<Int>): Long {
    return countArrangementsMemoized(input, groups, mutableMapOf())
}

fun countArrangementsMemoized(
    input: String,
    groups: List<Int>,
    cache: MutableMap<Pair<String, List<Int>>, Long>
): Long {
    val key = Pair(input, groups)
    if (cache.containsKey(key)) {
        return cache[key]!!
    }
    val result = countArrangements(input, groups, cache)
    cache[key] = result
    return result
}

fun countArrangements(input: String, groups: List<Int>, cache: MutableMap<Pair<String, List<Int>>, Long>): Long {

    if (input.count { it == '#' } > groups.sumOf { it }) {
        return 0
    }

    if (input.count { it == '#' || it == '?' } < groups.sumOf { it }) {
        return 0
    }

    if (groups.isNotEmpty() && groups[0] == 0) {
        return countArrangementsMemoized(input, groups.drop(1), cache)
    }

    if (input.startsWith(".")) {
        return countArrangementsMemoized(input.drop(1), groups, cache)
    }

    if (input.startsWith("#")) {

        if (groups.isEmpty()) {
            return 0
        }

        if (groups[0] > input.length) {
            return 0
        }

        if (input.take(groups[0]).contains(".")) {
            return 0
        }

        if (input.length > groups[0] && input[groups[0]] == '#') {
            return 0
        }

        if (input.take(groups[0] + 1).contains("?")) {
            return countArrangementsMemoized(
                input.mapIndexed { index, char ->
                    if (index < groups[0]) '#'
                    else if (index == groups[0]) '.'
                    else char
                }
                    .joinToString(""),
                groups,
                cache
            )
        }

        return countArrangementsMemoized(
            input.drop(1),
            groups.mapIndexed { index, group -> if (index == 0) group - 1 else group },
            cache
        )
    }

    if (input.contains("?")) {
        return countArrangementsMemoized(input.replaceFirst('?', '.'), groups, cache) +
                countArrangementsMemoized(input.replaceFirst('?', '#'), groups, cache)
    }

    if (groups.isNotEmpty()) {
        return 0
    }

    return 1
}

