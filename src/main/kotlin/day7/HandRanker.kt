package day7

interface HandRanker {

    fun type(hand: Hand): Hand.Type

    fun comparator(): Comparator<Hand>
}