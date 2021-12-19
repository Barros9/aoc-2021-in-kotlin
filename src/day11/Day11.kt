package day11

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val matrix = input.mapIndexed { row, line ->
            line.mapIndexed { column, elem ->
                Octopus(
                    row,
                    column,
                    elem.digitToInt()
                )
            }
        }
        return (0 until 100).sumOf { matrix.countFlashes() }
    }

    fun part2(input: List<String>): Int {
        val matrix = input.mapIndexed { row, line ->
            line.mapIndexed { column, elem ->
                Octopus(
                    row,
                    column,
                    elem.digitToInt()
                )
            }
        }

        return generateSequence { matrix.countFlashes().takeIf { it != matrix.flatten().size } }.count() + 1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day11/Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("day11/Day11")
    println(part1(input))
    println(part2(input))
}

private data class Octopus(val row: Int, val column: Int, var energy: Int, var isFlashed: Boolean = false)

private fun List<List<Octopus>>.countFlashes(): Int {
    var flashes = 0

    this.flatten().forEach { it.energy++ }

    do {
        val toFlash = this.flatten().filter { it.energy > 9 && !it.isFlashed }
        toFlash.forEach { octopus ->
            flashes += 1
            octopus.energy = 0
            octopus.isFlashed = true
            this.adjacent(octopus).filter { !it.isFlashed }.forEach { it.energy++ }
        }
    } while (this.flatten().any { it.energy > 9 })

    this.flatten().forEach { it.isFlashed = false }

    return flashes
}

private fun List<List<Octopus>>.adjacent(octopus: Octopus): List<Octopus> {
    return listOfNotNull(
        this.getOctopus(octopus.row - 1, octopus.column - 1),
        this.getOctopus(octopus.row - 1, octopus.column + 0),
        this.getOctopus(octopus.row - 1, octopus.column + 1),
        this.getOctopus(octopus.row + 0, octopus.column - 1),
        this.getOctopus(octopus.row + 0, octopus.column + 1),
        this.getOctopus(octopus.row + 1, octopus.column - 1),
        this.getOctopus(octopus.row + 1, octopus.column + 0),
        this.getOctopus(octopus.row + 1, octopus.column + 1),
    )
}

private fun List<List<Octopus>>.getOctopus(row: Int, column: Int): Octopus? {
    return this.getOrNull(row)?.getOrNull(column)
}