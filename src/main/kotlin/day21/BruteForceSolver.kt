package day21

class BruteForceSolver : Solver {
    override fun solve(input: String, targetSteps: Long): Long {

        val reps = targetSteps.toInt() / input.lines().size + 1

        val expandedH = input.lines().map { line ->
            line.replace('S', '.').repeat(reps) + line + line.replace('S', '.').repeat(reps)
        }
        val expandedV = mutableListOf<String>()
        repeat(reps) { expandedV.addAll(expandedH.map { it.replace('S', '.') }) }
        expandedV.addAll(expandedH)
        repeat(reps) { expandedV.addAll(expandedH.map { it.replace('S', '.') }) }

        val expandedInput = expandedV.joinToString("\n")

        val plot = Plot(expandedInput)

        val startingPosition = plot.allPositions.single { plot.valAt(it) == 'S' }

        return plot.computeReachabilities(startingPosition).countReachable(targetSteps)
    }
}

