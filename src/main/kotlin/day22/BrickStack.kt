package day22

import java.util.LinkedList

class BrickStack(input: List<Brick> = listOf()) {

    private val bricks: MutableList<Brick> = mutableListOf()
    private val bricksByTopLevel: MutableMap<Int, MutableSet<Brick>> = mutableMapOf()
    private val supportedBy = mutableMapOf<Char, Set<Char>>()
    private val supports = mutableMapOf<Char, MutableSet<Char>>()

    init {
        for (floatingBrick in input.sortedBy { it.z.first }) {
            var restingLevel = floatingBrick.z.first
            while (true) {
                val levelBelow = restingLevel - 1
                if (levelBelow == 0) {
                    break
                }
                if (occupied(floatingBrick.hSection(), levelBelow)) {
                    break
                }
                restingLevel = levelBelow
            }
            val restingBrick = floatingBrick.atZ(restingLevel)
            bricks.add(restingBrick)
            bricksByTopLevel.computeIfAbsent(restingBrick.z.last) { mutableSetOf() }.add(restingBrick)

            bricks.forEach { brick ->
                val supporters = bricksAt(brick.hSection(), brick.z.first - 1)
                supportedBy[brick.name] = supporters.map { it.name }.toSet()
            }

            supportedBy.entries.forEach { (supported, supporters) ->
                supporters.forEach { supporter ->
                    supports.computeIfAbsent(supporter) { mutableSetOf() }.add(supported)
                }
            }
        }
    }

    private fun occupied(hSection: HSection, z: Int): Boolean {
        return bricksAt(hSection, z).isNotEmpty()
    }

    private fun bricksAt(hSection: HSection, z: Int): Set<Brick> {
        return hSection.points().mapNotNull { brickAt(it.first, it.second, z) }.toSet()
    }

    private fun brickAt(x: Int, y: Int, z: Int): Brick? {
        return (bricksByTopLevel[z] ?: setOf()).singleOrNull { it.contains(x, y, z) }
    }

    fun viewX(): String {
        return ViewDrawer().draw(HorizontalAxis.X)
    }

    fun viewY(): String {
        return ViewDrawer().draw(HorizontalAxis.Y)
    }

    fun numDisintegrable(): Long {
        return bricks.count { brick ->
            val supportedByMe = supports[brick.name] ?: setOf()
            supportedByMe.all { supportedBy[it]!!.size > 1 }
        }.toLong()
    }

    fun countFalls(): Int {
        return bricks.sumOf { howManyFallIfWeDisintegrate(it.name) }
    }

    fun howManyFallIfWeDisintegrate(brickName: Char): Int {
        val disappeared = mutableSetOf<Char>()
        val newlyDisappeared = LinkedList<Char>()

        disappeared.add(brickName)
        newlyDisappeared.add(brickName)

        while (newlyDisappeared.isNotEmpty()) {
            val current = newlyDisappeared.removeFirst()
            val supported = supports[current] ?: setOf()
            supported.forEach { brickThatLostSomeSupport ->
                if (supportedBy[brickThatLostSomeSupport]!!.minus(disappeared).isEmpty()) {
                    disappeared.add(brickThatLostSomeSupport)
                    newlyDisappeared.add(brickThatLostSomeSupport)
                }
            }
        }

        return disappeared.size - 1
    }

    inner class ViewDrawer {

        fun draw(hAxis: HorizontalAxis): String {
            val sb = StringBuilder()

            // Top axis
            drawHAxis(sb, hAxis)

            // Content
            val vAxisLegendPosition = vAxisLen() / 2
            (1..<vAxisLen()).reversed().forEach { vIndex ->
                (0..<hAxisLen()).forEach { hIndex ->
                    sb.append(viewSpotFromSide(hAxis, hIndex, vIndex))
                }
                sb.append(" $vIndex")
                if (vIndex == vAxisLegendPosition) {
                    sb.append(" z")
                }
                sb.appendLine()
            }

            // Floor
            sb.append("-".repeat(hAxisLen()))
            sb.append(" 0")

            return sb.toString()
        }

        private fun drawHAxis(sb: StringBuilder, axis: HorizontalAxis) {
            val hAxisLegendPosition = hAxisLen() / 2
            sb.append(" ".repeat(hAxisLegendPosition))
            sb.append(axis.name.lowercase())
            sb.appendLine()
            sb.appendLine((0..<hAxisLen()).joinToString(""))
        }

        private fun hAxisLen() = 3
        private fun vAxisLen(): Int {
            if (bricks.isEmpty()) {
                return 3
            }
            return (bricks.maxOf { it.z.last } + 1).coerceAtLeast(3)
        }

        private fun viewSpotFromSide(hAxis: HorizontalAxis, hIndex: Int, vIndex: Int): Char {
            val x: Int? = if (hAxis == HorizontalAxis.X) hIndex else null
            val y: Int? = if (hAxis == HorizontalAxis.X) null else hIndex
            val crossingBricks =
                bricks.filter { it.crosses(x, y, vIndex) }
            if (crossingBricks.size == 1) {
                return crossingBricks.single().name
            } else if (crossingBricks.isNotEmpty()) {
                return '?'
            }
            return '.'
        }
    }

    enum class HorizontalAxis { X, Y }
}