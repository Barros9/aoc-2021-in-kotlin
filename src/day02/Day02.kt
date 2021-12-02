package day02

import readInput

fun main() {
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

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02/Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("day02/Day02")
    println(part1(input))
    println(part2(input))
}
