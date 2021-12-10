package day10

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val scoreMapping = mapOf(")" to 3, "]" to 57, "}" to 1197, ">" to 25137)

        return input
            .map { it.getIncompleteLinesOrCorruptedChar() }
            .filter { it.length == 1 }
            .groupingBy { it }
            .eachCount()
            .map { scoreMapping[it.key]!! * it.value }
            .sumOf { it }
    }

    fun part2(input: List<String>): Long {
        val incompleteLines = input
            .map { it.getIncompleteLinesOrCorruptedChar() }
            .filter { it.length > 1 }

        return incompleteLines
            .map { it.getIncompleteLineScore() }
            .sortedDescending()[(incompleteLines.size - 1) / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day10/Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("day10/Day10")
    println(part1(input))
    println(part2(input))
}

fun String.getIncompleteLinesOrCorruptedChar(): String {
    val bracketMapping = mapOf(']' to '[', '>' to '<', ')' to '(', '}' to '{')

    fun getIncompleteLinesOrCorruptedCharRec(string: String): String {
        string.forEachIndexed { index, char ->
            if (char in listOf('>', ')', ']', '}')) {
                return if (string[index - 1] == bracketMapping[char]) {
                    getIncompleteLinesOrCorruptedCharRec(string.removeRange(index - 1, index + 1))
                } else {
                    char.toString()
                }
            }
        }
        return string
    }
    return getIncompleteLinesOrCorruptedCharRec(this)
}

fun String.getIncompleteLineScore(): Long {
    var totalScore = 0L
    val scoreMapping = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)

    this.reversed().forEach {
        totalScore *= 5
        totalScore += scoreMapping[it]!!
    }
    return totalScore
}
