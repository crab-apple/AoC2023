package day20.module

import day20.Message

interface Module {
    fun receive(message: Message): Boolean?
}