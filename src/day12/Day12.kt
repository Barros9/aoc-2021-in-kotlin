package day12

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        fun recurse(cave: Cave, path: List<Cave>): Int {
            return if (cave.isEnd) {
                1
            } else {
                cave.nextCaves
                    .filter { !it.isSmall || it !in path }
                    .sumOf { recurse(it, path + it) }
            }
        }

        val startCave = input.parseCaves().first { it.isStart }
        return recurse(startCave, emptyList())
    }

    fun part2(input: List<String>): Int {
        fun recurse(cave: Cave, path: List<Cave>, visitedSmallTwice: Boolean): Int {
            return if (cave.isEnd) {
                1
            } else {
                cave.nextCaves.sumOf {
                    when {
                        !it.isSmall || it !in path -> recurse(it, path + it, visitedSmallTwice)
                        !visitedSmallTwice -> recurse(it, path + it, true)
                        else -> 0
                    }
                }
            }
        }

        val startCave = input.parseCaves().first { it.isStart }
        return recurse(startCave, emptyList(), false)
    }

    // test if implementation meets criteria from the description, like:
    val testInput0 = readInput("day12/Day12_test_0")
    check(part1(testInput0) == 10)
    check(part2(testInput0) == 36)

    val testInput1 = readInput("day12/Day12_test_1")
    check(part1(testInput1) == 19)
    check(part2(testInput1) == 103)

    val testInput2 = readInput("day12/Day12_test_2")
    check(part1(testInput2) == 226)
    check(part2(testInput2) == 3509)

    val input = readInput("day12/Day12")
    println(part1(input))
    println(part2(input))
}

private data class Cave(val name: String) {
    val nextCaves = mutableListOf<Cave>()
    val isStart get() = name == "start"
    val isEnd get() = name == "end"
    val isSmall get() = name.all { c -> c.isLowerCase() }
}

private fun List<String>.parseCaves(): List<Cave> {
    val caves = mutableMapOf<String, Cave>()

    this.forEach {
        val (left, right) = it.split('-')
        val leftCave = caves.getOrPut(left) { Cave(left) }
        val rightCave = caves.getOrPut(right) { Cave(right) }

        if (!rightCave.isStart && !leftCave.isEnd) {
            leftCave.nextCaves += rightCave
        }

        if (!leftCave.isStart && !rightCave.isEnd) {
            rightCave.nextCaves += leftCave
        }
    }

    return caves.values.toList()
}
