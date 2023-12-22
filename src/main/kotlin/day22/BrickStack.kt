package day22

class BrickStack(private val bricks: List<Brick> = listOf()) {

    fun viewX(): String {
        return ViewDrawer().draw(HorizontalAxis.X)
    }

    fun viewY(): String {
        return ViewDrawer().draw(HorizontalAxis.Y)
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