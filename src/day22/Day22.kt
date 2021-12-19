package day22

import Day
import readInput

class Day22 : Day {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    override fun play() {
        val testInput = readInput("day22/Day22_test")
        check(part1(testInput) == 1)

        val input = readInput("day22/Day22")
        println("Day 22")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}
