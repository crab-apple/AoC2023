package day20

import day20.module.BroadcastModule
import day20.module.ConjunctionModule
import day20.module.FlipFlopModule
import day20.module.Module
import java.util.*

class ModuleNetwork(
    val modules: MutableMap<String, Module>,
    val connections: MutableMap<String, List<String>>
) {

    var lowCount = 0L
    var highCount = 0L

    private val messageQueue: Deque<Message> = LinkedList()

    init {
        initConjunctionModules()
    }

    private fun initConjunctionModules() {
        modules.forEach { (name, module) ->
            if (module is ConjunctionModule) {
                val inputs = connections.filter { it.value.contains(name) }.map { it.key }
                inputs.forEach { module.registerInput(it) }
            }
        }
    }

    fun pushButton(): List<Message> {

        val sentMessages = mutableListOf<Message>()

        messageQueue.add(Message(false, "button", "broadcaster"))
        while (messageQueue.isNotEmpty()) {

            val message = messageQueue.removeFirst()

            if (message.high) highCount++ else lowCount++

            val destinationModule = modules[message.destination]
            if (destinationModule != null) {
                val result = destinationModule.receive(message)
                if (result != null) {
                    for (furtherDestinationName in connections[message.destination]!!) {
                        messageQueue.add(Message(result, message.destination, furtherDestinationName))
                    }
                }
            }

            sentMessages.add(message)
        }

        return sentMessages
    }

    override fun toString(): String {
        return connections.entries.joinToString("\n") {
            val prefix = when (modules[it.key]) {
                is FlipFlopModule -> "%"
                is ConjunctionModule -> "&"
                else -> ""
            }
            "${prefix}${it.key} -> ${it.value.joinToString(", ")}"
        }
    }

    companion object Parser {

        fun parse(input: String): ModuleNetwork {
            val modules = mutableMapOf<String, Module>()
            val connections = mutableMapOf<String, List<String>>()

            input.lines().forEach { line ->
                val moduleString = line.substringBefore(" -> ")
                val destinations = line.substringAfter(" -> ").split(", ")
                val module: Module
                val moduleName: String
                if (moduleString == "broadcaster") {
                    moduleName = "broadcaster"
                    module = BroadcastModule()
                } else {
                    moduleName = moduleString.drop(1)
                    module = when (moduleString[0]) {
                        '%' -> FlipFlopModule()
                        '&' -> ConjunctionModule()
                        else -> throw IllegalArgumentException()
                    }
                }
                modules[moduleName] = module
                connections[moduleName] = destinations
            }

            return ModuleNetwork(modules, connections)
        }
    }
}