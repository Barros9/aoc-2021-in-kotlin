package day07

import Day
import readInput
import kotlin.math.abs
import kotlin.math.min

class Day07 : Day {
    fun part1(input: List<String>): Int {
        val crabs = input.first().split(',').map { it.toInt() }
        val minPosition = crabs.minOf { it }
        val maxPosition = crabs.maxOf { it }
        var minFuel = Int.MAX_VALUE

        for (i in minPosition..maxPosition) {
            minFuel = min(crabs.sumOf { abs(it - i) }, minFuel)
        }

        return minFuel
    }

    fun part2(input: List<String>): Int {
        val crabs = input.first().split(',').map { it.toInt() }
        val minPosition = crabs.minOf { it }
        val maxPosition = crabs.maxOf { it }
        var minFuel = Int.MAX_VALUE

        for (i in minPosition..maxPosition) {
            minFuel = min(crabs.sumOf { IntRange(1, abs(it - i)).sum() }, minFuel)
        }

        return minFuel
    }

    override fun play() {
        val testInput = readInput("day07/Day07_test")
        check(part1(testInput) == 37)
        check(part2(testInput) == 168)

        val input = readInput("day07/Day07")
        println("Day 07")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}
