package day06

import Day
import readInput

class Day06 : Day {
    fun part1(input: List<String>): Long {
        var fishes = parseFishes(input)
        repeat(80) { fishes = fishes.nextDay() }
        return fishes.values.sum()
    }

    fun part2(input: List<String>): Long {
        var fishes = parseFishes(input)
        repeat(256) { fishes = fishes.nextDay() }
        return fishes.values.sum()
    }

    override fun play() {
        val testInput = readInput("day06/Day06_test")
        check(part1(testInput) == 5934L)
        check(part2(testInput) == 26984457539L)

        val input = readInput("day06/Day06")
        println("Day 06")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}

private fun parseFishes(input: List<String>): MutableMap<Int, Long> =
    input
        .first()
        .split(',')
        .map { it.toInt() }
        .groupingBy { it }
        .eachCount()
        .mapValues { (_, value) -> value.toLong() }
        .toMutableMap()

private fun MutableMap<Int, Long>.nextDay(): MutableMap<Int, Long> =
    mapKeysTo(mutableMapOf()) { (key) -> key - 1 }.apply {
        this.remove(-1)?.also {
            this[6] = it + (this[6] ?: 0L)
            this[8] = it
        }
    }