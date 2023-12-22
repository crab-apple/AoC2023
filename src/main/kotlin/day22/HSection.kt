package day22

data class HSection(val x: IntRange, val y: IntRange) {

    fun intersects(other: HSection) = x.intersects(other.x) && y.intersects(other.y)
}

private fun IntRange.intersects(other: IntRange): Boolean {
    if (last < other.first) {
        return false
    }
    if (other.last < first) {
        return false
    }
    return true
}

