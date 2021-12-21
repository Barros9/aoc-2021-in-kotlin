package day21

import Day
import readInput

class Day21 : Day {
    fun part1(input: List<String>): Int {
        return input
            .parseInput()
            .playWithDeterministDice()
    }

    fun part2(input: List<String>): Long {
        return input
            .parseInput()
            .playWithDiracDice()
    }

    override fun play() {
        val testInput = readInput("day21/Day21_test")
        check(part1(testInput) == 739785)
        check(part2(testInput) == 444356092776315)

        val input = readInput("day21/Day21")
        println("Day 21")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}

private fun List<String>.parseInput(): Board {
    val player1 = Player(this[0].removePrefix("Player 1 starting position: ").toInt() - 1)
    val player2 = Player(this[1].removePrefix("Player 2 starting position: ").toInt() - 1)
    return Board(player1, player2)
}

private data class Player(var position: Int, var score: Int = 0)

private data class Universe(var player1: Player, val player2: Player)

private class Board(var player1: Player, val player2: Player) {
    var isMovingPlayer1 = true

    // region Determinist Dice
    private var deterministicDice = 1
    private var deterministicDiceCounter = 0

    private fun throwDeterministicDice() = deterministicDice.also {
        deterministicDiceCounter++
        if (++deterministicDice > 100) deterministicDice = 1
    }

    fun playWithDeterministDice(): Int {
        while (player1.score < 1000 && player2.score < 1000) {
            val player = if (isMovingPlayer1) player1 else player2
            player.position += throwDeterministicDice() + throwDeterministicDice() + throwDeterministicDice()
            player.position %= 10
            player.score += player.position + 1
            isMovingPlayer1 = !isMovingPlayer1
        }

        return minOf(player1.score, player2.score) * deterministicDiceCounter
    }
    // endregion

    // region Dirac Dice
    private var universes = mapOf(Universe(player1, player2) to 1L)
    private var player1Wins = 0L
    private var player2Wins = 0L

    fun playWithDiracDice(): Long {
        while (universes.isNotEmpty()) {
            universes = universes.throwDiracDice().throwDiracDice().throwDiracDice().score()
            player1Wins += universes.filterKeys { it.player1.score >= 21 }.values.sum()
            player2Wins += universes.filterKeys { it.player2.score >= 21 }.values.sum()
            universes = universes.filterKeys { it.player1.score < 21 && it.player2.score < 21 }
            isMovingPlayer1 = !isMovingPlayer1
        }

        return maxOf(player1Wins, player2Wins)
    }

    private fun Map<Universe, Long>.throwDiracDice() = asSequence()
        .flatMap { (old, count) ->
            (1..3).map { roll ->
                if (isMovingPlayer1) {
                    old.copy(
                        player1 = Player((old.player1.position + roll) % 10, old.player1.score)
                    ) to count
                } else {
                    old.copy(
                        player2 = Player((old.player2.position + roll) % 10, old.player2.score)
                    ) to count
                }
            }
        }
        .groupBy({ it.first }, { it.second })
        .mapValues { it.value.sum() }

    private fun Map<Universe, Long>.score() = mapKeys { (old) ->
        if (isMovingPlayer1) {
            old.copy(
                player1 = Player(old.player1.position, old.player1.score + old.player1.position + 1)
            )
        } else {
            old.copy(
                player2 = Player(old.player2.position, old.player2.score + old.player2.position + 1)
            )
        }
    }

    // endregion
}
