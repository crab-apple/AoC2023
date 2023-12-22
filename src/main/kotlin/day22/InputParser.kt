package day22

fun parseInput(input: String): List<Brick> {
    return input.lines().mapIndexed { index, line -> parseInputLine(line, 'A'.plus(index)) }
}

private fun parseInputLine(line: String, brickName: Char): Brick {
    val parts = line.split("[,~]".toRegex()).map { it.toInt() }
    return Brick(
        brickName,
        parts[0] to parts[3],
        parts[1] to parts[4],
        parts[2] to parts[5],
    )
}
