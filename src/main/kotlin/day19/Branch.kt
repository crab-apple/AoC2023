package day19

class Branch(val predicate: RangeSetPredicate, val resultIfTrue: String, val resultIfFalse: String) {

    override fun toString(): String {
        return "if $predicate $resultIfTrue else $resultIfFalse"
    }
}