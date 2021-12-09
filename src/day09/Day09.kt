package day09

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val matrix =
            input.mapIndexed { row, line -> line.mapIndexed { column, elem -> Point(row, column, elem.digitToInt()) } }
        return matrix
            .flatten()
            .filter { matrix.isLowPoint(it) }
            .sumOf { it.value + 1 }
    }

    fun part2(input: List<String>): Int {
        val matrix =
            input.mapIndexed { row, line -> line.mapIndexed { column, elem -> Point(row, column, elem.digitToInt()) } }
        return matrix
            .flatten()
            .filter { matrix.isLowPoint(it) }
            .map { matrix.getBasinSize(it) }
            .sortedDescending()
            .subList(0, 3)
            .reduce { acc, i -> acc * i }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day09/Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("day09/Day09")
    println(part1(input))
    println(part2(input))
}

private fun Point.adjacent(): List<Point> = listOf(
    Point(row + 1, column),
    Point(row - 1, column),
    Point(row, column + 1),
    Point(row, column - 1)
)

private fun List<List<Point>>.isLowPoint(point: Point): Boolean =
    point.adjacent().all {
        this[point.row][point.column].value < (this.getOrNull(it.row)?.getOrNull(it.column)?.value ?: Int.MAX_VALUE)
    }

private fun List<List<Point>>.getBasinSize(lowPoint: Point): Int {
    fun getBasinSizeRec(point: Point, comparePoint: Point, visited: MutableSet<Point>): Int {
        val current = this.getOrNull(point.row)?.getOrNull(point.column) ?: return 0
        if (point in visited || current.value == 9) return 0
        if (current.value >= comparePoint.value) {
            visited.add(Point(point.row, point.column))
            return 1 + (point.adjacent().sumOf { getBasinSizeRec(it, comparePoint, visited) })
        }
        return 0
    }

    val current = this.getOrNull(lowPoint.row)?.getOrNull(lowPoint.column) ?: return 0
    return getBasinSizeRec(lowPoint, current, HashSet())
}

data class Point(val row: Int, val column: Int, val value: Int = 0)
