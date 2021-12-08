package day08

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            line.split(" | ")[1]
                .split(" ")
                .map { it.length }
                .count { it in listOf(2, 3, 4, 7) }
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (patternLine, outputLine) = line.split(" | ")
            val pattern = patternLine.split(" ").map { it.toCharArray().sorted().joinToString("") }
            val output = outputLine.split(" ").map { it.toCharArray().sorted().joinToString("") }

            val knownNumber = Array(10) { "" }
            knownNumber[1] = pattern.first { it.length == 2 }
            knownNumber[7] = pattern.first { it.length == 3 }
            knownNumber[4] = pattern.first { it.length == 4 }
            knownNumber[8] = pattern.first { it.length == 7 }

            val possible069 = pattern.filter { it.length == 6 }
            val possible235 = pattern.filter { it.length == 5 }

            possible069.forEach { p ->
                when {
                    !knownNumber[1].all { it in p } -> knownNumber[6] = p
                    knownNumber[4].all { it in p } -> knownNumber[9] = p
                    else -> knownNumber[0] = p
                }
            }

            possible235.forEach { p ->
                when {
                    knownNumber[1].all { it in p } -> knownNumber[3] = p
                    p.all { it in knownNumber[9] } -> knownNumber[5] = p
                    else -> knownNumber[2] = p
                }
            }

            output.joinToString("") { knownNumber.indexOf(it).toString() }.toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day08/Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("day08/Day08")
    println(part1(input))
    println(part2(input))
}

