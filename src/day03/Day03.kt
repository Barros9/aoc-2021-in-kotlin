package day03

import Day
import readInput

class Day03 : Day {
    fun part1(input: List<String>): Int {
        val columns = input.first().indices

        val gamma = columns.joinToString("") { position ->
            input.countBits(position).getMostCommon().toString()
        }.toInt(2)

        val epsilon = columns.joinToString("") { position ->
            input.countBits(position).getLeastCommon().toString()
        }.toInt(2)

        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        val columns = input.first().indices

        val oxygen = columns.runningFold(input) { remainingItems, position ->
            remainingItems.filter { it[position] == remainingItems.countBits(position).getMostCommon() }
        }.first { it.size == 1 }.first().toInt(2)

        val co2 = columns.runningFold(input) { remainingItems, position ->
            remainingItems.filter { it[position] == remainingItems.countBits(position).getLeastCommon() }
        }.first { it.size == 1 }.first().toInt(2)

        return oxygen * co2
    }

    override fun play() {
        val testInput = readInput("day03/Day03_test")
        check(part1(testInput) == 198)
        check(part2(testInput) == 230)

        val input = readInput("day03/Day03")
        println("Day 03")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}

private fun List<String>.countBits(position: Int): Pair<Int, Int> =
    this.map { it[position] }.run { count { it == '0' } to count { it == '1' } }

private fun Pair<Int, Int>.getMostCommon(): Char = if (this.first > this.second) '0' else '1'
private fun Pair<Int, Int>.getLeastCommon(): Char = if (this.first > this.second) '1' else '0'
