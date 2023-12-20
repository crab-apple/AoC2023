package day20.module

import day20.Message

class FlipFlopModule : Module {

    private var state = false
    override fun receive(message: Message): Boolean? {
        if (message.high) {
            return null
        }
        state = !state
        return state
    }
}