package day19

class Branch(val predicate: PartPredicate, val resultIfTrue: String, val resultIfFalse: String) {

    override fun toString(): String {
        return "if $predicate $resultIfTrue else $resultIfFalse"
    }
}