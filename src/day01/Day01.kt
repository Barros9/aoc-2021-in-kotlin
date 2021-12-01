package day01

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.toInt() }
            .windowed(2)
            .count { (previous, next) -> previous < next }
    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.toInt() }
            .windowed(3)
            .map { (a, b, c) -> a + b + c }
            .windowed(2)
            .count { (previous, next) -> previous < next }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01/Day01_test")
    check(part1(testInput) == 7)

    val input = readInput("day01/Day01")
    println(part1(input))
    println(part2(input))
}