package day9

data class Sequence(private val values: List<Long>) {

    fun differential(): Sequence {
        return values.indices.drop(1)
            .map { values[it] - values[it - 1] }
            .let { Sequence(it) }
    }

    fun nextValue(): Long {
        if (values.all { it == 0L }) {
            return 0
        }
        return values.last() + differential().nextValue()
    }

    fun previousValue(): Long {
        if (values.all { it == 0L }) {
            return 0
        }
        return values.first() - differential().previousValue()
    }

    companion object {

        fun of(vararg values: Int) = Sequence(values.map { it.toLong() })
    }
}
