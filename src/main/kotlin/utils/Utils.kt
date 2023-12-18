package utils

import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = readInputOneString(name).lines()

fun readInputOneString(name: String) = Path("src/main/kotlin/$name.txt").readText(StandardCharsets.UTF_8)

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun String.asInput() = trimIndent().lines()

fun transpose(lines: List<String>): List<String> {
    return lines[0].indices.map { index -> lines.map { it[index] } }.map { it.joinToString("") }
}

fun rotateClockwise(lines: List<String>): List<String> {
    return transpose(lines).map { it.reversed() }
}

fun rotateCounterClockwise(lines: List<String>): List<String> {
    return transpose(lines).reversed()
}

fun <T> List<T>.getCycling(index: Int): T {
    return this[(index + this.size) % this.size]
}

