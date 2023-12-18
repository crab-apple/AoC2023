package day18

import utils.Direction

data class InputLine(val direction: Direction, val numSteps: Int) {
    companion object Parser {

        fun parseRegular(input: String): List<InputLine> {
            return input.lines().map { line ->
                InputLine(
                    parseDirection(line.split(" ")[0]),
                    line.split(" ")[1].toInt()
                )
            }
        }

        fun parseHexadecimal(input: String): List<InputLine> {
            return input.lines().map { line ->
                val hexDigits = line.takeLast(7).take(6)
                InputLine(
                    parseDirection(hexDigits.takeLast(1)),
                    hexDigits.take(5).toInt(16)
                )
            }
        }

        private fun parseDirection(input: String) = when (input) {
            "U" -> Direction.NORTH
            "D" -> Direction.SOUTH
            "R" -> Direction.EAST
            "L" -> Direction.WEST

            "0" -> Direction.EAST
            "1" -> Direction.SOUTH
            "2" -> Direction.WEST
            "3" -> Direction.NORTH
            else -> throw IllegalArgumentException("")
        }
    }
}