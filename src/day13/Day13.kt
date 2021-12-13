package day13

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val (coordinates, folds) = input.parseInput()
        return coordinates
            .fold(folds.first())
            .size
    }

    fun part2(input: List<String>) {
        val (coordinates, folds) = input.parseInput()
        folds.forEach { coordinates.fold(it) }
        coordinates.printCode()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day13/Day13_test")
    check(part1(testInput) == 17)

    val input = readInput("day13/Day13")
    println(part1(input))
    part2(input)
}

data class Coordinate(var x: Int, var y: Int)

fun List<String>.parseInput(): Pair<List<Coordinate>, List<Pair<String, Int>>> {
    val folds = this
        .filter { it.startsWith("f") }
        .map {
            val (axis, value) = it.removePrefix("fold along ").split("=")
            Pair(axis, value.toInt())
        }
    val coordinates = this
        .filterNot { it.startsWith("f") || it.isEmpty() }
        .map {
            val (x, y) = it.split(",")
            Coordinate(x.toInt(), y.toInt())
        }

    return Pair(coordinates, folds)
}

fun List<Coordinate>.fold(fold: Pair<String, Int>): List<Coordinate> {
    val width = this.maxOf { it.x }
    val height = this.maxOf { it.y }

    val newWidth = (width - 1) / 2
    val newHeight = (height - 1) / 2

    this.forEach {
        if (fold.first == "x") {
            if (it.x > newWidth)
                it.x = newWidth - (it.x - 2 - newWidth)
        } else {
            if (it.y > newHeight)
                it.y = newHeight - (it.y - 2 - newHeight)
        }
    }

    return this.distinct()
}

fun List<Coordinate>.printCode() {
    val width = this.maxOf { it.x }
    val height = this.maxOf { it.y }

    val output = mutableListOf("")
    for (y in 0..height) {
        var outputLine = ""
        for (x in 0..width) {
            outputLine += if (this.contains(Coordinate(x, y))) {
                "#"
            } else {
                " "
            }
        }
        output.add(outputLine)
    }
    print(output.joinToString("\n"))
}