package day02

import Day
import readInput

class Day02 : Day {
    fun part1(input: List<String>): Int {
        var (forward, up, down) = listOf(0, 0, 0)
        input.map { it.split(" ") }.forEach {
            when (it[0]) {
                "down" -> down += it[1].toInt()
                "up" -> up += it[1].toInt()
                "forward" -> forward += it[1].toInt()
            }
        }
        return forward * (down - up)
    }

    fun part2(input: List<String>): Int {
        var (forward, aim, depth) = listOf(0, 0, 0)
        input.map { it.split(" ") }.forEach {
            when (it[0]) {
                "down" -> aim += it[1].toInt()
                "up" -> aim -= it[1].toInt()
                "forward" -> {
                    forward += it[1].toInt()
                    depth += aim * it[1].toInt()
                }
            }
        }
        return forward * depth
    }

    override fun play() {
        val testInput = readInput("day02/Day02_test")
        check(part1(testInput) == 150)
        check(part2(testInput) == 900)

        val input = readInput("day02/Day02")
        println("Day 02")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}
