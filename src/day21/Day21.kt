package day21

import Day
import readInput

class Day21 : Day {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    override fun play() {
        val testInput = readInput("day21/Day21_test")
        check(part1(testInput) == 1)

        val input = readInput("day21/Day21")
        println("Day 21")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}
