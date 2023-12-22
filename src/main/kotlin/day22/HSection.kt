package day22

data class HSection(val x: IntRange, val y: IntRange){


    fun points(): List<Pair<Int, Int>> {
        return x.flatMap { eachX -> y.map { Pair(eachX, it) } }
    }
}

