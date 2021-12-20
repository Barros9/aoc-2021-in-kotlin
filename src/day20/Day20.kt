package day20

import Day
import readInput

class Day20 : Day {
    fun part1(input: List<String>): Int {
        val imageEnhancementAlgorithm = input.first()
        val inputImage = input.parseInput()
        val enhancedGrids = generateSequence(inputImage) { it.enhance(imageEnhancementAlgorithm) }
        return enhancedGrids.elementAt(2).countLights()
    }

    fun part2(input: List<String>): Int {
        val imageEnhancementAlgorithm = input.first()
        val inputImage = input.parseInput()
        val enhancedGrids = generateSequence(inputImage) { it.enhance(imageEnhancementAlgorithm) }
        return enhancedGrids.elementAt(50).countLights()
    }

    override fun play() {
        val testInput = readInput("day20/Day20_test")
        check(part1(testInput) == 35)
        check(part2(testInput) == 3351)

        val input = readInput("day20/Day20")
        println("Day 20")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}

private data class Point(val x: Int, val y: Int)

private data class InputImage(val points: Map<Point, Char>, val default: Char = '.') {
    val xMin = points.keys.minOf { it.x }
    val xMax = points.keys.maxOf { it.x }
    val yMin = points.keys.minOf { it.y }
    val yMax = points.keys.maxOf { it.y }

    fun enhance(imageEnhancementAlgorithm: String): InputImage {
        val newDefault = when {
            default == '.' && imageEnhancementAlgorithm.first() == '.' -> '.'
            default == '.' && imageEnhancementAlgorithm.first() == '#' -> '#'
            default == '#' && imageEnhancementAlgorithm.last() == '.' -> '.'
            default == '#' && imageEnhancementAlgorithm.last() == '#' -> '#'
            else -> error("Oops")
        }

        val newPoints = buildMap {
            for (x in (xMin - 2..xMax + 2)) {
                for (y in (yMin - 2..yMax + 2)) {
                    Point(x, y).also {
                        put(it, imageEnhancementAlgorithm[fromBinaryToDecimal(it)])
                    }
                }
            }
        }

        return InputImage(newPoints, newDefault)
    }

    fun valueAt(point: Point) = if ((points[point] ?: default) == '#') 1 else 0

    fun fromBinaryToDecimal(point: Point) = listOf(
        valueAt(Point(point.x - 1, point.y - 1)),
        valueAt(Point(point.x + 0, point.y - 1)),
        valueAt(Point(point.x + 1, point.y - 1)),
        valueAt(Point(point.x - 1, point.y + 0)),
        valueAt(Point(point.x + 0, point.y + 0)),
        valueAt(Point(point.x + 1, point.y + 0)),
        valueAt(Point(point.x - 1, point.y + 1)),
        valueAt(Point(point.x + 0, point.y + 1)),
        valueAt(Point(point.x + 1, point.y + 1))
    ).joinToString("").toInt(radix = 2)

    fun countLights() = points.values.count { it == '#' }
}

private fun List<String>.parseInput(): InputImage {
    return InputImage(drop(2).flatMapIndexed { y, line -> line.mapIndexed { x, char -> Point(x, y) to char } }.toMap())
}
