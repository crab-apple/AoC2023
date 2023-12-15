package utils

fun <E> List<E>.split(predicate: (E) -> Boolean): List<List<E>> {

    val result = mutableListOf<List<E>>()
    val remaining = this.toMutableList()


    while (remaining.isNotEmpty()) {
        while (remaining.isNotEmpty() && predicate(remaining[0])) {
            remaining.removeAt(0)
        }
        var current: MutableList<E>? = null
        while (remaining.isNotEmpty() && !predicate(remaining[0])) {
            if (current == null) {
                current = mutableListOf()
                result.add(current)
            }

            current.add(remaining[0])
            remaining.removeAt(0)
        }
    }

    return result
}
