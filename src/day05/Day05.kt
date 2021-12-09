package day05

import day05.Direction.*
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { Line(it.split(" -> ")) }
            .filter { it.direction == Horizontal || it.direction == Vertical }
            .flatMap { it.points() }
            .groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }
            .size
    }

    fun part2(input: List<String>): Int {
        return input
            .map { Line(it.split(" -> ")) }
            .flatMap { it.points() }
            .groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }
            .size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day05/Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("day05/Day05")
    println(part1(input))
    println(part2(input))
}

class Line(points: List<String>) {
    private val p1: Point
    private val p2: Point

    init {
        p1 = Point(points[0])
        p2 = Point(points[1])
    }

    val direction = when {
        p1.y == p2.y -> Horizontal
        p1.x == p2.x -> Vertical
        else -> Diagonal
    }

    fun points(): List<Point> {
        val points = mutableListOf<Point>()
        val goRight = p1.x < p2.x
        val goDown = p1.y < p2.y

        var temp = p1
        while (temp != p2) {
            points.add(temp)
            temp = temp.next(direction, goRight, goDown)
        }
        points.add(p2)
        return points
    }
}

data class Point(var x: Int, var y: Int) {
    constructor(string: String) : this(0, 0) {
        val (x, y) = string.split(",")
        this.x = x.toInt()
        this.y = y.toInt()
    }

    fun next(direction: Direction, goRight: Boolean, goDown: Boolean): Point {
        return when (direction) {
            Horizontal -> Point(if (goRight) x + 1 else x - 1, y)
            Vertical -> Point(x, if (goDown) y + 1 else y - 1)
            Diagonal -> Point(if (goRight) x + 1 else x - 1, if (goDown) y + 1 else y - 1)
        }
    }
}

enum class Direction { Horizontal, Vertical, Diagonal }