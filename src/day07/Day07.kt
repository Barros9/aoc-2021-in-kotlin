package day07

import readInput
import kotlin.math.abs
import kotlin.math.min

fun main() {
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

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day07/Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("day07/Day07")
    println(part1(input))
    println(part2(input))
}
