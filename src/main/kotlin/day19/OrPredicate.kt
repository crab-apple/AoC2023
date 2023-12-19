package day19

class OrPredicate(val predicates: List<PartPredicate>) : PartPredicate {

    override fun test(part: Part): Boolean {
        return predicates.any { it.test(part) }
    }

    override fun and(other: PartPredicate): PartPredicate {
        return OrPredicate(predicates.map { it.and(other) })
    }

    override fun negate(): PartPredicate {
        TODO("Not yet implemented")
    }

    override fun numAcceptedParts(): Long {

        var sum: Long = 0
        var alreadyConsidered: PartPredicate = NonePredicate()

        for (predicate in predicates) {
            sum += predicate.and(alreadyConsidered.negate()).numAcceptedParts()
            alreadyConsidered = alreadyConsidered.and(predicate)
        }

        return sum
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }
}