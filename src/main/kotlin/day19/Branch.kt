package day19

import java.util.function.Predicate

class Branch(val predicate: Predicate<Part>, val resultIfTrue: String, val resultIfFalse: String) {
}