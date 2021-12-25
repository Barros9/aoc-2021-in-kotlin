package day25

import Day
import day25.Status.*
import readInput

class Day25 : Day {
    fun part1(input: List<String>): Int {
        val grid = input.parseInput()
        return generateSequence { grid.move() }.takeWhile { it }.count() + 1
    }

    override fun play() {
        val testInput = readInput("day25/Day25_test")
        check(part1(testInput) == 58)

        val input = readInput("day25/Day25")
        println("Day 25")
        println("- Part 1 result: ${part1(input)}")
    }
}

private data class Grid(val positions: List<List<Position>>) {
    fun move(): Boolean {
        fun moving(direction: Status): Boolean {
            var moved = false
            positions.flatten().forEach { position ->
                if (position.status == direction && !position.alreadyMoved) {
                    val next = positions.next(position, direction)
                    if (next.status == Empty && !next.alreadyMoved) {
                        moved = true
                        positions[next.row][next.column].apply {
                            status = direction
                            alreadyMoved = true
                        }
                        positions[position.row][position.column].apply {
                            status = Empty
                            alreadyMoved = true
                        }
                    }
                }
            }
            positions.flatten().forEach { it.alreadyMoved = false }
            return moved
        }

        val movedRight = moving(Right)
        val movedDown = moving(Down)

        return movedRight || movedDown
    }
}

private enum class Status { Right, Down, Empty }
private data class Position(var row: Int, var column: Int, var status: Status, var alreadyMoved: Boolean = false)

private fun List<String>.parseInput(): Grid {
    return Grid(mapIndexed { row, line -> line.mapIndexed { column, elem -> Position(row, column, elem.parseElem()) } })
}

private fun Char.parseElem(): Status {
    return when (this) {
        '.' -> Empty
        '>' -> Right
        'v' -> Down
        else -> error("Error parsing")
    }
}

private fun List<List<Position>>.next(position: Position, status: Status): Position {
    return when (status) {
        Right -> {
            this.getOrNull(position.row)?.getOrNull(position.column + 1) ?: this.getOrNull(position.row)?.getOrNull(0)!!
        }
        Down -> {
            this.getOrNull(position.row + 1)?.getOrNull(position.column) ?: this.getOrNull(0)
                ?.getOrNull(position.column)!!
        }
        else -> error("")
    }
}
