package day22

fun parseInput(input: String): List<Brick> {
    return input.lines().map { parseInputLine(it) }
}

private fun parseInputLine(it: String): Brick {
    val parts = it.split("[,~]".toRegex()).map { it.toInt() }
    return Brick(
        parts[0] to parts[3],
        parts[1] to parts[4],
        parts[2] to parts[5],
    )
}
