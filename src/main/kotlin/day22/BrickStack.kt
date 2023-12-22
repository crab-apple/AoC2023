package day22

class BrickStack(bricks: List<Brick> = listOf()) {

    fun viewX(): String {
        return ViewDrawer().draw(ViewDrawer.HorizontalAxis.X)
    }

    fun viewY(): String {
        return ViewDrawer().draw(ViewDrawer.HorizontalAxis.Y)
    }

    internal class ViewDrawer {

        fun draw(hAxis: HorizontalAxis): String {
            val sb = StringBuilder()

            // Top axis
            drawHAxis(sb, hAxis)

            // Content
            val vAxisLegendPosition = hAxisLen() / 2
            (1..<vAxisLen()).reversed().forEach {
                sb.append(".".repeat(hAxisLen()))
                sb.append(" $it")
                if (it == vAxisLegendPosition) {
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
        private fun vAxisLen() = 3

        enum class HorizontalAxis { X, Y }
    }
}