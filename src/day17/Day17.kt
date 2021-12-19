package day17

import Day
import readInput

class Day17 : Day {
    fun part1(input: List<String>): Int {
        return input
            .parseTargetArea()
            .hitsVelocityWithHigherPoint()
            .maxOf { it.second }
    }

    fun part2(input: List<String>): Int {
        return input
            .parseTargetArea()
            .hitsVelocityWithHigherPoint()
            .count()
    }

    override fun play() {
        val testInput = readInput("day17/Day17_test")
        check(part1(testInput) == 45)
        check(part2(testInput) == 112)

        val input = readInput("day17/Day17")
        println("Day 17")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}

private data class Velocity(var x: Int, var y: Int)
private data class Position(val x: Int, val y: Int)
private data class TargetArea(val list: List<Position>) {
    val maxX = list.maxOf { it.x }
    val minY = list.minOf { it.y }

    fun hitsTarget(velocity: Velocity): Int? {
        var position = Position(0, 0)
        var higherPosition = -Int.MAX_VALUE

        while (true) {
            position = velocity.next(position)

            if (position.y > higherPosition) {
                higherPosition = position.y
            }

            if (list.contains(position)) {
                return higherPosition
            } else if (position.x > list.last().x || position.y < list.last().y) {
                return null
            }
        }
    }
}

private fun List<String>.parseTargetArea(): TargetArea {
    val regex = Regex("target area: x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)")
    val (xMin, xMax, yMin, yMax) = checkNotNull(regex.find(this.first())).destructured

    val targetArea = mutableListOf<Position>()
    for (i in xMin.toInt()..xMax.toInt()) {
        for (j in yMax.toInt() downTo yMin.toInt()) {
            targetArea.add(Position(i, j))
        }
    }

    return TargetArea(targetArea)
}

private fun TargetArea.hitsVelocityWithHigherPoint(): List<Pair<Velocity, Int>> {
    val listOfVelocityWithHigherPoint = mutableListOf<Pair<Velocity, Int>>()
    for (i in 1..maxX) {
        for (j in minY..-minY) {
            val velocity = Velocity(i, j)
            hitsTarget(velocity)?.also { listOfVelocityWithHigherPoint.add(Pair(velocity, it)) }
        }
    }
    return listOfVelocityWithHigherPoint.toList()
}

private fun Velocity.next(position: Position): Position {
    val newPositionX = x + position.x
    val newPositionY = y + position.y
    x += when {
        x > 0 -> -1
        x < 0 -> +1
        else -> 0
    }
    y -= 1
    return Position(newPositionX, newPositionY)
}
