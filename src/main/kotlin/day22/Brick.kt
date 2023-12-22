package day22

data class Brick(val x: IntRange, val y: IntRange, val z: IntRange) {

    constructor(x: Pair<Int, Int>, y: Pair<Int, Int>, z: Pair<Int, Int>) : this(x.toRange(), y.toRange(), z.toRange())
}

private fun Pair<Int, Int>.toRange() = IntRange(first, second)
