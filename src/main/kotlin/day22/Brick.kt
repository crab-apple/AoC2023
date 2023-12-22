package day22

data class Brick(val name: Char, val x: IntRange, val y: IntRange, val z: IntRange) {

    constructor(name: Char, x: Pair<Int, Int>, y: Pair<Int, Int>, z: Pair<Int, Int>) : this(
        name,
        x.toRange(),
        y.toRange(),
        z.toRange()
    )

    fun contains(x: Int, y: Int, z: Int) = crosses(x, y, z)

    fun crosses(hSection: HSection): Boolean {
        return hSection().intersects(hSection)
    }

    fun crosses(x: Int?, y: Int?, z: Int?): Boolean {
        return (x == null || this.x.contains(x)) &&
                (y == null || this.y.contains(y)) &&
                (z == null || this.z.contains(z))
    }

    fun hSection() = HSection(x, y)

    fun atZ(z: Int): Brick {
        return Brick(
            this.name,
            this.x,
            this.y,
            IntRange(z, z + this.z.last - this.z.first)
        )
    }
}

private fun Pair<Int, Int>.toRange() = IntRange(first, second)
