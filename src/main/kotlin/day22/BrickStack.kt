package day22

class BrickStack(input: List<Brick> = listOf()) {

    private val bricks: MutableList<Brick>

    init {
        bricks = mutableListOf()
        for (floatingBrick in input.sortedBy { it.z.first }) {
            var restingLevel = floatingBrick.z.first
            while (true) {
                var levelBelow = restingLevel - 1
                if (levelBelow == 0) {
                    break
                }
                if (occupied(floatingBrick.hSection(), levelBelow)) {
                    break
                }
                restingLevel = levelBelow
            }
            bricks.add(floatingBrick.atZ(restingLevel))
        }
    }

    private fun occupied(hSection: HSection, z: Int): Boolean {
        return bricksAt(hSection, z).isNotEmpty()
    }

    private fun bricksAt(hSection: HSection, z: Int): Set<Int> {
        return hSection.points().mapNotNull { brickAt(it.first, it.second, z) }.toSet()
    }

    private fun brickAt(x: Int, y: Int, z: Int): Int? {
        return bricks.indexOfFirst { it.contains(x, y, z) }.let { if (it == -1) null else it }
    }

    fun viewX(): String {
        return ViewDrawer().draw(HorizontalAxis.X)
    }

    fun viewY(): String {
        return ViewDrawer().draw(HorizontalAxis.Y)
    }

    fun numDisintegrable(): Long {
        val supportedBy = mutableMapOf<Int, Set<Int>>()
        bricks.forEachIndexed { index, brick ->
            val supporters = bricksAt(brick.hSection(), brick.z.first - 1)
            supportedBy[index] = supporters
        }

        val supports = mutableMapOf<Int, MutableSet<Int>>()
        supportedBy.entries.forEach { (supported, supporters) ->
            supporters.forEach { supporter ->
                supports.computeIfAbsent(supporter) { mutableSetOf() }.add(supported)
            }
        }

        return bricks.indices.count { index ->
            val supportedByMe = supports[index] ?: setOf()
            supportedByMe.all { supportedBy[it]!!.size > 1 }
        }.toLong()
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
            val crossingIndices =
                bricks.mapIndexedNotNull { index, brick -> if (brick.crosses(x, y, vIndex)) index else null }
            if (crossingIndices.size == 1) {
                return 'A'.plus(crossingIndices.single())
            } else if (crossingIndices.isNotEmpty()) {
                return '?'
            }
            return '.'
        }
    }

    enum class HorizontalAxis { X, Y }
}