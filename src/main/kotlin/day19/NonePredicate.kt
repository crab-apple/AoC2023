package day19

class NonePredicate : PartPredicate {

    override fun test(part: Part): Boolean {
        return false
    }

    override fun and(other: PartPredicate): PartPredicate {
        return this
    }

    override fun negate(): PartPredicate {
        return RangeSetPredicate.all()
    }

    override fun numAcceptedParts(): Long {
        return 0
    }

    override fun isEmpty(): Boolean {
        return true
    }
}