package day14

import Day
import readInput

class Day14 : Day {
    fun part1(input: List<String>): Long {
        val (template, map) = input.parseInput()
        return map.processTemplate(template, 10)
    }

    fun part2(input: List<String>): Long {
        val (template, map) = input.parseInput()
        return map.processTemplate(template, 40)
    }

    override fun play() {
        val testInput = readInput("day14/Day14_test")
        check(part1(testInput) == 1588L)
        check(part2(testInput) == 2188189693529L)

        val input = readInput("day14/Day14")
        println("Day 14")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}

private fun List<String>.parseInput(): Pair<String, Map<String, List<String>>> {
    val template = this.first()
    val map = this.drop(2)
        .associate {
            val (key, value) = it.split(" -> ")
            key to listOf("${key[0]}$value", "$value${key[1]}")
        }
    return Pair(template, map)
}

private fun Map<String, List<String>>.processTemplate(template: String, times: Int): Long {
    var currentTemplate = template
        .windowed(2)
        .groupingBy { it }
        .eachCount()
        .mapValues { it.value.toLong() }

    repeat(times) {
        currentTemplate = buildMap {
            for ((pair, count) in currentTemplate) {
                for (char in this@processTemplate.getValue(pair)) {
                    put(char, getOrElse(char) { 0 } + count)
                }
            }
        }
    }

    val counts = buildMap<Char, Long> {
        put(template.first(), 1)
        put(template.last(), 1)
        for ((pair, count) in currentTemplate) {
            for (char in pair) {
                put(char, getOrElse(char) { 0 } + count)
            }
        }
    }

    return (counts.values.maxOrNull()!! - counts.values.minOrNull()!!) / 2
}
