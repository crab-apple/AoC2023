package day22

data class Brick(val x: IntRange, val y: IntRange, val z: IntRange) {

    constructor(x: Pair<Int, Int>, y: Pair<Int, Int>, z: Pair<Int, Int>) : this(x.toRange(), y.toRange(), z.toRange())

    fun contains(x: Int, y: Int, z: Int) = crosses(x, y, z)

    fun crosses(x: Int?, y: Int?, z: Int?): Boolean {
        return (x == null || this.x.contains(x)) &&
                (y == null || this.y.contains(y)) &&
                (z == null || this.z.contains(z))
    }

    fun hSection(): List<Pair<Int, Int>> {
        return x.flatMap { eachX -> y.map { Pair(eachX, it) } }
    }

    fun atZ(z: Int): Brick {
        return Brick(
            this.x,
            this.y,
            IntRange(z, z + this.z.last - this.z.first)
        )
    }
}

private fun Pair<Int, Int>.toRange() = IntRange(first, second)