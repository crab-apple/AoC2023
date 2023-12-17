package utils

enum class Direction { NORTH, EAST, SOUTH, WEST;

    fun turn(laterality: Laterality): Direction {
        val delta = when (laterality) {
            Laterality.RIGHT -> 1
            Laterality.LEFT -> -1
        }
        return Direction.entries[(this.ordinal + delta + Direction.entries.size) % Direction.entries.size]
    }
}