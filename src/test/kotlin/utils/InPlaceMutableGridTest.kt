package utils

class InPlaceMutableGridTest : MutableGridTest() {

    override fun mutableGrid(string: String): MutableGrid<Char> = InPlaceMutableGrid.of(string)
}
