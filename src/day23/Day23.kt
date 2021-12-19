package day23

import Day
import readInput

class Day23 : Day {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    override fun play() {
        val testInput = readInput("day23/Day23_test")
        check(part1(testInput) == 1)

        val input = readInput("day23/Day23")
        println("Day 23")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}
