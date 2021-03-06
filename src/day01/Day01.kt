package day01

import Day
import readInput

class Day01 : Day {
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

    override fun play() {
        val testInput = readInput("day01/Day01_test")
        check(part1(testInput) == 7)
        check(part2(testInput) == 5)

        val input = readInput("day01/Day01")
        println("Day 01")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}
