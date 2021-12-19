package day24

import Day
import readInput

class Day24 : Day {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    override fun play() {
        val testInput = readInput("day24/Day24_test")
        check(part1(testInput) == 1)

        val input = readInput("day24/Day24")
        println("Day 24")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}
