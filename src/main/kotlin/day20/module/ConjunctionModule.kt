package day20.module

import day20.Message

class ConjunctionModule : Module {

    private val recentInputs = mutableMapOf<String, Boolean>()

    fun registerInput(input: String) {
        recentInputs[input] = false
    }

    fun inputs(): Set<String> = recentInputs.keys

    override fun receive(message: Message): Boolean {
        recentInputs[message.origin] = message.high
        return !recentInputs.all { it.value }
    }
}