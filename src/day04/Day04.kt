package day04

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val splitString = it.split(": ")[1].split(" | ")
            val winningNumbers =
                splitString[0].split(" ")
                    .filter { number -> number.isNotEmpty() }
                    .map { number -> number.toInt() }
            val currentNumbers =
                splitString[1].split(" ")
                    .filter { number -> number.isNotEmpty() }
                    .map { number -> number.toInt() }
            var points = 0
            currentNumbers.forEach { number ->
                if (winningNumbers.contains(number)) {
                    if (points == 0) points += 1 else points *= 2
                }
            }
            sum += points
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val cardCounts = input.map { 1 }.toTypedArray()
        input.forEachIndexed { index, card ->
            val splitString = card.split(": ")[1].split(" | ")
            val winningNumbers =
                splitString[0].split(" ")
                    .filter { number -> number.isNotEmpty() }
                    .map { number -> number.toInt() }
            val currentNumbers =
                splitString[1].split(" ")
                    .filter { number -> number.isNotEmpty() }
                    .map { number -> number.toInt() }
            val count = currentNumbers.count { it in winningNumbers }
            for (j in index.rangeUntil(index + count)) {
                cardCounts[j + 1] += cardCounts[index]
            }
        }
        return cardCounts.sum()
    }

    val input = readInput("day04/Day04")
    part1(input).println()
    part2(input).println()
}