package day04

import Day
import readInput

class Day04 : Day {
    fun part1(input: List<String>): Int {
        val draws = input.first().split(",").map { it.toInt() }
        val cards = input.drop(2).windowed(size = 5, step = 6).map { parseCard(it) }

        draws.forEach { draw ->
            cards.forEach { card ->
                card.mark(draw)
                if (card.isBingo()) {
                    return draw * card.sumUnmarked()
                }
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val draws = input.first().split(",").map { it.toInt() }
        val cards = input.drop(2).windowed(size = 5, step = 6).map { parseCard(it) }
        lateinit var lastWinnerCard: Pair<Int, Card>

        draws.forEach { draw ->
            cards.forEach { card ->
                if (!card.isAWinnerCard) {
                    card.mark(draw)
                    if (card.isBingo()) {
                        lastWinnerCard = Pair(draw, card)
                    }
                }
            }
        }
        return lastWinnerCard.first * lastWinnerCard.second.sumUnmarked()
    }

    override fun play() {
        val testInput = readInput("day04/Day04_test")
        check(part1(testInput) == 4512)
        check(part2(testInput) == 1924)

        val input = readInput("day04/Day04")
        println("Day 04")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }

}

private data class Box(val number: Int, var isMarked: Boolean = false)

private class Card(private val boxes: List<Box>) {
    var isAWinnerCard = false

    fun mark(number: Int) {
        boxes.forEach { if (it.number == number) it.isMarked = true }
    }

    fun isBingo(): Boolean {
        for (i in 0 until 5) {
            if (rowBingo(i) || columnBingo(i)) {
                isAWinnerCard = true
                return true
            }
        }
        return false
    }

    fun sumUnmarked() = boxes.filter { !it.isMarked }.sumOf { it.number }

    private fun rowBingo(index: Int) = listOf(
        boxes[index * 5 + 0],
        boxes[index * 5 + 1],
        boxes[index * 5 + 2],
        boxes[index * 5 + 3],
        boxes[index * 5 + 4]
    ).all { it.isMarked }

    private fun columnBingo(index: Int) = listOf(
        boxes[index + 5 * 0],
        boxes[index + 5 * 1],
        boxes[index + 5 * 2],
        boxes[index + 5 * 3],
        boxes[index + 5 * 4]
    ).all { it.isMarked }
}

private fun parseCard(input: List<String>) =
    Card(input.flatMap { row -> row.trim().replace("\\s+".toRegex(), ",").split(",").map { Box(it.toInt()) } })