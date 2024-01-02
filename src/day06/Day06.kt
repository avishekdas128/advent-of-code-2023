package day06

import println
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        val time = input[0].split(":")[1]
            .trim()
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
        val distance = input[1].split(":")[1]
            .trim()
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.toInt() }

        val answer = mutableListOf<Int>()
        time.forEachIndexed { index, value ->
            var count = 0
            for (i in 0..value) {
                val distanceValue = distance[index]
                val distanceCovered = i * (value - i)
                if (distanceCovered > distanceValue) count++
            }
            answer.add(count)
        }
        return answer.reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        val time = input[0].split(":")[1].trim().filter { !it.isWhitespace() }.toLong()
        val distance = input[1].split(":")[1].trim().filter { !it.isWhitespace() }.toLong()

        var count = 0
        for (i in 0..time) {
            val distanceCovered = i * (time - i)
            if (distanceCovered > distance) count++
        }
        return count
    }

    val input = readInput("day06/Day06")
    part1(input).println()
    part2(input).println()
}