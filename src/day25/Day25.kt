package day25

import Day
import readInput

class Day25 : Day {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    override fun play() {
        val testInput = readInput("day25/Day25_test")
        check(part1(testInput) == 1)

        val input = readInput("day25/Day25")
        println("Day 25")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}
