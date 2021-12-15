package day15

import readInput
import kotlin.Int.Companion.MAX_VALUE

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { line -> line.map { it.digitToInt() } }
            .applyDijkstra()
    }

    fun part2(input: List<String>): Int {
        return input
            .map { line -> line.map { it.digitToInt() } }
            .repeatMap()
            .applyDijkstra()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day15/Day15_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInput("day15/Day15")
    println(part1(input))
    println(part2(input))
}

data class Point(val x: Int, val y: Int)

private fun List<List<Int>>.applyDijkstra(): Int {
    val startPoint = Point(0, 0)
    val endPoint = Point(this[this.lastIndex].lastIndex, this.lastIndex)

    val distances = mutableMapOf<Point, Int>()
    val queue = mutableMapOf<Point, Int>()

    distances[startPoint] = 0
    queue[startPoint] = distances[startPoint]!!

    while (queue.isNotEmpty()) {
        val analyzePoint = queue.minByOrNull { it.value }!!.key

        if (analyzePoint == endPoint) {
            break
        }

        queue.remove(analyzePoint)
        val value = distances[analyzePoint]!!

        analyzePoint.adjacent().forEach { adjacent ->
            if (adjacent.y < 0 ||
                adjacent.y > this.lastIndex ||
                adjacent.x < 0 ||
                adjacent.x > this[adjacent.y].lastIndex
            ) {
                return@forEach
            }
            val newValue = value + this[adjacent.y][adjacent.x]
            if (newValue < distances.getOrDefault(adjacent, MAX_VALUE)) {
                distances[adjacent] = newValue
                queue[adjacent] = newValue
            }
        }
    }
    return distances[endPoint]!!
}

private fun Point.adjacent(): List<Point> = listOf(
    Point(x + 1, y),
    Point(x - 1, y),
    Point(x, y + 1),
    Point(x, y - 1)
)

private fun List<List<Int>>.repeatMap() = this.repeatLines().map { it.repeatLine() }

private fun List<List<Int>>.repeatLines(): List<List<Int>> {
    val originalMap = this.toMutableList()
    var repeatedMap = this
    repeat(4) {
        repeatedMap = repeatedMap.map { line ->
            line.map { elem ->
                if (elem == 9) {
                    1
                } else {
                    elem + 1
                }
            }
        }
        originalMap.addAll(repeatedMap)
    }
    return originalMap
}

private fun List<Int>.repeatLine(): List<Int> {
    val originalLine = this.toMutableList()
    var repeatedLine = this

    repeat(4) {
        repeatedLine = repeatedLine.map { elem ->
            if (elem == 9) {
                1
            } else {
                elem + 1
            }
        }
        originalLine.addAll(repeatedLine)
    }
    return originalLine
}