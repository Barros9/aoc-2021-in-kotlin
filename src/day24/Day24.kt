package day24

import Day
import day24.Instruction.*
import day24.Register.*
import readInput

class Day24 : Day {
    fun part1(input: List<String>): String {
        return input
            .parseInput()
            .solve(9L downTo 1L)
    }

    fun part2(input: List<String>): String {
        return input
            .parseInput()
            .solve(1L..9L)
    }

    override fun play() {
        val input = readInput("day24/Day24")
        val solutionPart1 = part1(input)
        val solutionPart2 = part2(input)

        check(solutionPart1 == "65984919997939")
        check(solutionPart2 == "11211619541713")

        println("Day 24")
        println("- Part 1 result: $solutionPart1")
        println("- Part 2 result: $solutionPart2")
    }
}

private enum class Register { W, X, Y, Z }
private enum class Instruction { Inp, Add, Mul, Div, Mod, Eql }
private data class Operation(val instruction: Instruction, val register: Register, val value: Any?) {
    operator fun invoke(registers: MutableMap<Register, Long>, input: Iterator<Long>): Boolean {
        when (instruction) {
            Inp -> {
                if (!input.hasNext()) return false
                registers[register] = input.next()
            }
            else -> {
                val value = value.getValue(registers)
                registers[register] = when (instruction) {
                    Add -> registers[register]?.plus(value)!!
                    Mul -> registers[register]?.times(value)!!
                    Div -> registers[register]?.div(value)!!
                    Mod -> registers[register]?.mod(value)!!
                    Eql -> if (registers[register] == value) 1L else 0L
                    else -> error("Operation not found")
                }
            }
        }
        return true
    }
}

private fun List<String>.parseInput(): List<Operation> {
    return map { line ->
        val elem = line.split(" ")
        val instruction = elem[0].mapInstruction()
        val register = elem[1].mapRegister()
        val value = elem.getOrNull(2)?.mapValue()
        Operation(instruction, register, value)
    }
}

private fun String.mapInstruction() = when (this) {
    "inp" -> Inp
    "add" -> Add
    "mul" -> Mul
    "div" -> Div
    "mod" -> Mod
    "eql" -> Eql
    else -> error("Unknown instruction")
}

private fun String.mapRegister() = when (this) {
    "w" -> W
    "x" -> X
    "y" -> Y
    "z" -> Z
    else -> error("Unknown register")
}

private fun String?.mapValue() = when (this) {
    "w", "x", "y", "z" -> this.mapRegister()
    else -> this?.toLong()
}

private fun Any?.getValue(register: MutableMap<Register, Long>): Long = when (this) {
    is Register -> register[this]!!
    is Long -> this
    else -> error("Value not found")
}

private fun List<Operation>.solve(progression: LongProgression): String {
    val limitRange = ((11L..99L)).map { listOf(it / 10, it % 10) }.filterNot { l -> l.any { it == 0L } }
    fun validPrefix(prefix: List<Long>): Boolean {
        val data = prefix.toMutableList()
        while (data.size < 14) {
            data.add(limitRange.minByOrNull { d -> verifyMonad(data + d) }?.first() ?: error("Nothing in range"))
        }
        return verifyMonad(data) == 0L
    }
    return (0 until 14).fold(listOf<Long>()) { l, _ -> l + progression.first { validPrefix(l + it) } }.joinToString("")
}

private fun List<Operation>.verifyMonad(monad: List<Long>): Long {
    val registers = mutableMapOf(W to 0L, X to 0L, Y to 0L, Z to 0L)
    val input = monad.iterator()
    forEach { instruction ->
        if (!instruction(registers, input)) {
            return registers[Z]!!
        }
    }
    return registers[Z]!!
}
