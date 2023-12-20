package day20

import day20.module.ConjunctionModule
import utils.println
import utils.readInputOneString

fun main() {
    val input = readInputOneString("day20/input")
    solvePart1(input).println()
    solvePart2(input).println()
}

fun solvePart1(input: String): Long {
    val network = ModuleNetwork.parse(input)
    repeat(1000) { network.pushButton() }
    return network.lowCount * network.highCount
}

fun solvePart2(input: String): Long {
    val network = ModuleNetwork.parse(input)

    // For my input, it looks like the initial broadcaster module sends signals to 4 different other modules,
    // and then these 4 modules are the starts of 4 independent circuits that only converge at the end into one
    // conjunction module, the output of which then goes to 'rx'. So 'rx' will receive a low signal iff these four
    // networks all result in a high signal at the same time. That is, assuming that each of the networks sends exactly
    // one signal to the conjunction module. Let's see...

    val finalConnection = network.connections.entries.single { it.value.contains("rx") }

    if (finalConnection.value != listOf("rx")) {
        throw RuntimeException("Assumption doesn't hold: the final connection has onl 'rx' as an output")
    }

    if (network.connections.any { it.key == "rx" }) {
        throw RuntimeException("Assumption doesn't hold: 'rx' has no outputs")
    }

    val finalConjunctionModuleName = finalConnection.key
    val finalConjunctionModule = network.modules[finalConjunctionModuleName]
    if (finalConjunctionModule !is ConjunctionModule) {
        throw RuntimeException("Assumption doesn't hold: the module right before 'rx' is a conjunction module")
    }

    // Now let's run the thing many times, and record when there are high signals from each of the inputs into the
    // final conjunction module. Hopefully we'll find some cycles?
    val finalConjunctionInputs = finalConjunctionModule.inputs().associateWith { mutableListOf<Int>() }
    (1..100000).forEach { cycleNumber ->
        val sentMessages = network.pushButton()
        finalConjunctionInputs.keys.forEach { finalConjunctionInput ->
            if (sentMessages.any { it.origin == finalConjunctionInput && it.destination == finalConjunctionModuleName && it.high }) {
                finalConjunctionInputs[finalConjunctionInput]!!.add(cycleNumber)
            }
        }
    }

    val cycleLengths = finalConjunctionInputs.map { (_, highs) ->
        val cycleLength = highs.zipWithNext().map { (a, b) -> b - a }
            .distinct()
            .single()
        val prefix = highs[0]
        if (cycleLength != prefix) {
            throw RuntimeException("Assumption doesn't hold: there is no prefix to the cycles")
        }
        cycleLength
    }

    return cycleLengths.map { it.toLong() }.reduceRight { a, b -> a * b }
}

