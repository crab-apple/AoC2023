package day19

interface PartPredicate {

    fun test(part: Part): Boolean;

    fun and(other: PartPredicate): PartPredicate

    fun negate(): PartPredicate

    fun numAcceptedParts(): Long

    fun isEmpty(): Boolean
}