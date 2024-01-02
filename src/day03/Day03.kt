package day03

import println
import readInput
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed { y, line ->
            var x = 0
            while (x < line.length) {
                var number = ""
                val numberStartX = x
                while (x < line.length && line[x].isDigit()) {
                    number += line[x]
                    x++
                }
                if (number.isNotEmpty()) {
                    val intNumber = number.toInt()
                    var surroundingCharacters = ""
                    val lineStartX = max(0, numberStartX - 1)
                    val lineEndX = min(x, line.length - 1)
                    if (y != 0) {
                        surroundingCharacters += input[y - 1].substring(lineStartX, lineEndX + 1)
                    }
                    if (y < line.length - 1) {
                        surroundingCharacters += input[y + 1].substring(lineStartX, lineEndX + 1)
                    }
                    if (numberStartX > 0) {
                        surroundingCharacters += line[numberStartX - 1]
                    }
                    if (x < line.length) {
                        surroundingCharacters += line[x]
                    }
                    if (surroundingCharacters.any { !it.isDigit() && it != '.' && !it.isWhitespace() }) {
                        sum += intNumber
                    }
                } else {
                    x++
                }
            }
        }
        return sum
    }

    fun checkLine(line: String, scanStartX: Int, scanEndX: Int): List<Int> {
        val numbers = mutableListOf<Int>()
        var scanX = scanStartX
        while (scanX <= scanEndX) {
            var numberX = scanX
            var number = ""
            while (numberX < line.length && line[numberX].isDigit()) {
                number += line[numberX]
                numberX++
            }
            if (number.isNotEmpty()) {
                val overflow = numberX - scanX
                numberX = scanX - 1
                while (numberX >= 0 && line[numberX].isDigit()) {
                    number = line[numberX] + number
                    numberX--
                }
                numbers += number.toInt()
                scanX += overflow
            } else {
                scanX++
            }
        }
        return numbers
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                if (char == '*') {
                    val numbers = mutableListOf<Int>()
                    val lineStartX = max(0, x - 1)
                    val lineEndX = min(x + 1, line.length - 1)
                    if (y != 0) {
                        numbers += checkLine(input[y - 1], lineStartX, lineEndX)
                    }
                    if (y < line.length - 1) {
                        numbers += checkLine(input[y + 1], lineStartX, lineEndX)
                    }
                    if (x > 0) {
                        numbers += checkLine(input[y], x - 1, x - 1)
                    }
                    if (x < line.length) {
                        numbers += checkLine(input[y], x + 1, x + 1)
                    }
                    if (numbers.size == 2) {
                        sum += numbers[0] * numbers[1]
                    }
                }
            }
        }
        return sum
    }

    val input = readInput("day03/Day03")
    part1(input).println()
    part2(input).println()
}
