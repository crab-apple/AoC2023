package day21

import utils.Position
import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.max

class HopefullyEfficientSolver : Solver {

    data class SolutionDetails(
        var reps: Long = 0,
        var maxIntraPlotDistance: Long = 0,

        var cornerMin: Long = 0,
        var cornerMax: Long = 0,

        var numReachablePlots: Long = 0,
        var numReachablePlots_subsumed: Long = 0,
        var numReachablePlots_origin: Long = 0,
        var numReachablePlots_straights: Long = 0,
        var numReachablePlots_corners: Long = 0,

        var numEvaluatedPlots: Long = 0,
        var numEvaluatedPlots_subsumed: Long = 0,
        var numEvaluatedPlots_origin: Long = 0,
        var numEvaluatedPlots_straights: Long = 0,
        var numEvaluatedPlots_corners: Long = 0,

        var subsumedEvenPerQuadrant: Long = 0,
        var subsumedOddPerQuadrant: Long = 0,

        var answer: Long = 0,
        var answer_subsumed: Long = 0,
        var answer_origin: Long = 0,
        var answer_straights: Long = 0,
        var answer_corners: Long = 0,

        )

    override fun solve(input: String, targetSteps: Long): Long {
        return solveDetailed(input, targetSteps).answer
    }

    fun solveDetailed(input: String, targetSteps: Long): SolutionDetails {

        val result = SolutionDetails()

        val sideLen = input.lines().size
        val reps = ceil((targetSteps - sideLen / 2).coerceAtLeast(0).toDouble() / sideLen).toLong()

        // If there were no rocks, the positions reachable within N steps would be all those positions at a Manhattan
        // distance of N or fewer. And the positions reachable at exactly N steps would be about half of those - half
        // of them would be reachable and half wouldn't, depending on whether N is even or odd.

        // If N is very large, then for the biggest portion of the map, all you care about is whether the Manhattan distance
        // to that point is even or odd. That is assuming that all the empty positions are reachable - if some aren't because
        // they are surrounded by rocks, we can just replace them with rocks beforehand.

        // So the difficult part seems to be at the edges.

        // Because the edges of the plot are empty, all the repetitions that are not in a direct line with the plot
        // can be reached walking along the edges and entering the repetition at a corner. That leaves us with only 4
        // repetitions to compute separately, I believe

        // Perhaps the input is crafted in such a way that, even for repetitions that are in a direct line with the initial
        // plot, the shortest path to any destination point can also pass through the corners. We'll have to test that.

        val plot = Plot(input)
        val startingPosition = plot.allPositions.single { plot.valAt(it) == 'S' }

        // It turns out that there's only one point in either direction that is the fastest to reach! Also, in the real
        // input, this point is always in a straight line from the start, which makes it easier.

        val reachabilitiesFromStart = plot.computeReachabilities(startingPosition)
        val reachabilitiesFromN = plot.computeReachabilities(Position(0, sideLen / 2))
        val reachabilitiesFromS = plot.computeReachabilities(Position(sideLen - 1, sideLen / 2))
        val reachabilitiesFromE = plot.computeReachabilities(Position(sideLen / 2, sideLen - 1))
        val reachabilitiesFromW = plot.computeReachabilities(Position(sideLen / 2, 0))
        val reachabilitiesFromNW = plot.computeReachabilities(Position(0, 0))
        val reachabilitiesFromNE = plot.computeReachabilities(Position(0, sideLen - 1))
        val reachabilitiesFromSW = plot.computeReachabilities(Position(sideLen - 1, 0))
        val reachabilitiesFromSE = plot.computeReachabilities(Position(sideLen - 1, sideLen - 1))

        val allReachableEvenStart = plot.computeReachabilities(Position(0, 0)).countReachable(1_000_000_000)
        val allReachableOddStart = plot.computeReachabilities(Position(0, 1)).countReachable(1_000_000_000)
        val allReachableEvenPlot: Long
        val allReachableOddPlot: Long
        if (targetSteps % 2 == 0L) {
            allReachableEvenPlot = allReachableEvenStart
            allReachableOddPlot = allReachableOddStart
        } else {
            allReachableEvenPlot = allReachableOddStart
            allReachableOddPlot = allReachableEvenStart
        }

        val maxIntraPlotDistance = setOf(
            reachabilitiesFromE.max,
            reachabilitiesFromN.max,
            reachabilitiesFromS.max,
            reachabilitiesFromW.max,
            reachabilitiesFromNE.max,
            reachabilitiesFromNW.max,
            reachabilitiesFromSE.max,
            reachabilitiesFromSW.max,
        ).max().toLong()

        if (maxIntraPlotDistance > ((sideLen - 1) * 2)) {
            throw RuntimeException(
                "Assumption failed: shortest path from an entry point to any square in the plot is" +
                        "no greater than the distance from one corner to the other"
            )
        }

        // What's the side (in number of plots) of the square within which we can be sure that we always reach all spots?
        result.cornerMin =
            ((targetSteps - (sideLen - 1)) / (sideLen)).coerceAtLeast(0)
        result.cornerMax =
            ceil((targetSteps - sideLen).toDouble() / (sideLen)).toLong().coerceAtLeast(0)

        result.subsumedEvenPerQuadrant = (result.cornerMin / 2) * (result.cornerMin / 2)
        result.subsumedOddPerQuadrant =
            (result.cornerMin * (result.cornerMin - 1) / 2) - result.subsumedEvenPerQuadrant
        val countEven = result.subsumedEvenPerQuadrant * 4
        val countOdd = result.subsumedOddPerQuadrant * 4
        result.numEvaluatedPlots_subsumed += countEven + countOdd
        result.numReachablePlots_subsumed += countEven + countOdd
        result.answer_subsumed += allReachableEvenPlot * countEven
        result.answer_subsumed += allReachableOddPlot * countOdd

        fun doCornerPlot(greaterRow: Long, greaterCol: Long): Long {

            val distanceInNumPlots = greaterRow.absoluteValue + greaterCol.absoluteValue
            val distance: Long = sideLen - 1 + ((distanceInNumPlots - 2) * sideLen) + 2
            val reachabilities: Reachabilities = if (greaterRow < 0 && greaterCol < 0) {
                reachabilitiesFromSE
            } else if (greaterRow < 0) {
                reachabilitiesFromSW
            } else if (greaterCol < 0) {
                reachabilitiesFromNE
            } else {
                reachabilitiesFromNW
            }
            val localTargetSteps = (targetSteps - distance)

            return reachabilities.countReachable(localTargetSteps)
        }

        for (greaterRow in 0 - reps..0 + reps) {
            if (greaterRow == 0L) {
                continue
            }
            val min = (result.cornerMin - greaterRow.absoluteValue + 1).coerceAtLeast(1)
            val max = (result.cornerMax - greaterRow.absoluteValue + 1)
            (min..max).forEach { greaterCol ->
                val count = doCornerPlot(greaterRow, greaterCol)
                result.answer_corners += count
                if (count > 0) {
                    result.numReachablePlots_corners++
                }
                result.numEvaluatedPlots_corners++
            }
            (-max..-min).forEach { greaterCol ->
                val count = doCornerPlot(greaterRow, greaterCol)
                result.answer_corners += count
                if (count > 0) {
                    result.numReachablePlots_corners++
                }
                result.numEvaluatedPlots_corners++
            }
        }

        // Origin plot
        result.answer_origin += reachabilitiesFromStart.countReachable(targetSteps)
        result.numReachablePlots_origin++
        result.numEvaluatedPlots_origin++

        // Reachable in a straight line
        val straightPlots = mutableSetOf<Position>()
        (0 - reps..0 + reps).forEach {
            if (it != 0L) {
                straightPlots.add(Position(it, 0))
                straightPlots.add(Position(0, it))
            }
        }

        straightPlots.forEach { greaterPos ->
            val greaterRow = greaterPos.row.toInt()
            val greaterCol = greaterPos.col.toInt()

            val distanceInNumPlots: Int = max(greaterRow.absoluteValue, greaterCol.absoluteValue)
            val distance: Long = sideLen / 2 + ((distanceInNumPlots - 1).toLong() * sideLen) + 1
            val reachabilities: Reachabilities = if (greaterRow == 0 && greaterCol > 0) {
                reachabilitiesFromW
            } else if (greaterRow == 0) {
                reachabilitiesFromE
            } else if (greaterRow > 0) {
                reachabilitiesFromN
            } else {
                reachabilitiesFromS
            }
            val localTargetSteps = (targetSteps - distance)

            val count = reachabilities.countReachable(localTargetSteps)
            result.answer_straights += count
            if (count > 0) {
                result.numReachablePlots_straights++
            }
            result.numEvaluatedPlots_straights++
        }

        result.reps = reps
        result.maxIntraPlotDistance = maxIntraPlotDistance

        result.answer = result.answer_origin + result.answer_corners + result.answer_straights + result.answer_subsumed
        result.numEvaluatedPlots =
            result.numEvaluatedPlots_origin + result.numEvaluatedPlots_corners + result.numEvaluatedPlots_straights + result.numEvaluatedPlots_subsumed
        result.numReachablePlots =
            result.numReachablePlots_origin + result.numReachablePlots_corners + result.numReachablePlots_straights + result.numReachablePlots_subsumed

        return result;
    }
}
