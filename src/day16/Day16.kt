package day16

import Day
import readInput

class Day16 : Day {
    fun part1(input: List<String>): Long {
        return Binary(input)
            .parsePacket()
            .versions()
    }

    fun part2(input: List<String>): Long {
        return Binary(input)
            .parsePacket()
            .calculateExpression()
    }

    override fun play() {
        check(part1(listOf("8A004A801A8002F478")) == 16L)
        check(part1(listOf("620080001611562C8802118E34")) == 12L)
        check(part1(listOf("C0015000016115A2E0802F182340")) == 23L)
        check(part1(listOf("A0016C880162017C3686B18A3D4780")) == 31L)

        check(part2(listOf("C200B40A82")) == 3L)
        check(part2(listOf("04005AC33890")) == 54L)
        check(part2(listOf("880086C3E88112")) == 7L)
        check(part2(listOf("CE00C43D881120")) == 9L)
        check(part2(listOf("D8005AC2A8F0")) == 1L)
        check(part2(listOf("F600BC2D8F")) == 0L)
        check(part2(listOf("9C005AC2F8F0")) == 0L)
        check(part2(listOf("9C0141080250320F1802104A08")) == 1L)

        val input = readInput("day16/Day16")
        println("Day 16")
        println("- Part 1 result: ${part1(input)}")
        println("- Part 2 result: ${part2(input)}")
    }
}

private sealed class Packet(val version: Int, val type: Int)
private class Literal(version: Int, type: Int, val data: Long) : Packet(version, type)
private class Operator(version: Int, type: Int, val data: List<Packet>) : Packet(version, type)

private class Binary(input: List<String>) {
    val binary: String = "F${input.first()}".toBigInteger(16).toString(2).drop(4)
    var index = 0

    fun readBits(i: Int) = binary.substring(index, index + i).toInt(2).also { index += i }
}

private fun Binary.parsePacket(): Packet {
    val version = readBits(3)
    return when (val type = readBits(3)) {
        4 -> Literal(version, type, parseLiteral())
        else -> Operator(version, type, parseOperator())
    }
}

private fun Binary.parseLiteral(): Long {
    var result = 0L
    do {
        val next = readBits(1) == 1
        result *= 16
        result += readBits(4)
    } while (next)
    return result
}

private fun Binary.parseOperator(): List<Packet> {
    return if (readBits(1) == 0) {
        val lastBit = readBits(15) + index
        buildList {
            while (index < lastBit) {
                add(parsePacket())
            }
        }
    } else {
        val packetCount = readBits(11)
        List(packetCount) { parsePacket() }
    }
}

private fun Packet.versions(): Long = when (this) {
    is Literal -> version.toLong()
    is Operator -> version + data.sumOf { it.versions() }
}

private fun Packet.calculateExpression(): Long = when (this) {
    is Literal -> data
    is Operator -> when (type) {
        0 -> data.sumOf { it.calculateExpression() }
        1 -> data.map { it.calculateExpression() }.reduce(Long::times)
        2 -> data.minOf { it.calculateExpression() }
        3 -> data.maxOf { it.calculateExpression() }
        5 -> data.map { it.calculateExpression() }.let { if (it[0] > it[1]) 1L else 0L }
        6 -> data.map { it.calculateExpression() }.let { if (it[0] < it[1]) 1L else 0L }
        7 -> data.map { it.calculateExpression() }.let { if (it[0] == it[1]) 1L else 0L }
        else -> throw IllegalStateException()
    }
}