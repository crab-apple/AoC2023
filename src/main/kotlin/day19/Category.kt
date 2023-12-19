package day19

enum class Category {
    X, M, A, S;

    companion object Parser {

        fun parse(input: String) = Category.valueOf(input.uppercase())
    }
}