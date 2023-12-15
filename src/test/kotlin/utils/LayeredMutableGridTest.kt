package utils

class LayeredMutableGridTest : MutableGridTest() {

    override fun mutableGrid(string: String): MutableGrid<Char> = LayeredMutableGrid.of(string)
}
