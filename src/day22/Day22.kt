package day22

import Day
import readInput
import kotlin.math.max
import kotlin.math.min

class Day22 : Day {
    fun part1(input: List<String>): Long {
        return input
            .parseInput()
            .map { it.limit(-50..50) }
            .calculate()
    }

    fun part2(input: List<String>): Long {
        return input
            .parseInput()
            .calculate()
    }

    override fun play() {
        val testInput1 = readInput("day22/Day22_test_1")
        val testInput2 = readInput("day22/Day22_test_2")
        check(part1(testInput1) == 590784L)
        check(part2(testInput2) == 2758514936282235L)

        val input = readInput("day22/Day22")
        println("Day 22")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}

private data class Position(val x: Int, val y: Int, val z: Int)
private data class Instruction(val area: Area, val isOn: Boolean)
private data class Area(val from: Position, val to: Position) {
    val count = (from.x..to.x).count().toLong() * (from.y..to.y).count() * (from.z..to.z).count()
}

private fun List<String>.parseInput() = map {
    val regex = Regex("(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)")
    val (state, x1, x2, y1, y2, z1, z2) = checkNotNull(regex.find(it)).destructured
    Instruction(
        Area(
            Position(min(x1.toInt(), x2.toInt()), min(y1.toInt(), y2.toInt()), min(z1.toInt(), z2.toInt())),
            Position(max(x1.toInt(), x2.toInt()), max(y1.toInt(), y2.toInt()), max(z1.toInt(), z2.toInt()))
        ),
        state == "on"
    )
}

private fun Instruction.limit(range: IntRange) = Instruction(
    Area(
        Position(
            max(area.from.x, range.first),
            max(area.from.y, range.first),
            max(area.from.z, range.first)
        ),
        Position(
            min(area.to.x, range.last),
            min(area.to.y, range.last),
            min(area.to.z, range.last)
        )
    ),
    isOn
)

private fun Area.getOverlap(area: Area): Area? {
    val x1 = max(area.from.x, from.x)
    val y1 = max(area.from.y, from.y)
    val z1 = max(area.from.z, from.z)

    val x2 = min(area.to.x, to.x)
    val y2 = min(area.to.y, to.y)
    val z2 = min(area.to.z, to.z)

    return if (x2 >= x1 && y2 >= y1 && z2 >= z1) {
        Area(Position(x1, y1, z1), Position(x2, y2, z2))
    } else null
}

private fun List<Instruction>.calculate(): Long {
    var count = 0L
    forEachIndexed { index, instruction ->
        val cubeCount = instruction.area.count - getOverlap(this.subList(0, index), instruction.area, instruction.isOn)
        count += if (instruction.isOn) cubeCount else -cubeCount
    }
    return count
}

private fun getOverlap(instructions: List<Instruction>, area: Area, isOn: Boolean): Long {
    var overlap = if (isOn) 0L else area.count
    instructions.forEachIndexed { index, instruction ->
        instruction.area.getOverlap(area)?.also { area ->
            (area.count - getOverlap(instructions.subList(0, index), area, instruction.isOn)).also {
                overlap += if (instruction.isOn != isOn) -it else it
            }
        }
    }
    return overlap
}
