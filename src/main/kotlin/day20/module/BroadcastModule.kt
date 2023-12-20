package day20.module

import day20.Message

class BroadcastModule : Module {

    override fun receive(message: Message): Boolean = message.high
}