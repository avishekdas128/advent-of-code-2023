package day01

import println
import readInput

fun main() {
    fun replacePatterns(line: String): String {
        var newLine = line
        newLine = newLine.replace("one","o1ne")
        newLine = newLine.replace("two", "t2wo")
        newLine = newLine.replace("three", "th3ree")
        newLine = newLine.replace("four", "fo4ur")
        newLine = newLine.replace("five", "fi5ve")
        newLine = newLine.replace("six", "s6ix")
        newLine = newLine.replace("seven", "sev7en")
        newLine = newLine.replace("eight", "ei8ght")
        newLine = newLine.replace("nine", "ni9ne")
        return newLine
    }

    fun getNumber(line: String): Int {
        val firstNumber = line.find(Char::isDigit)
        val lastNumber = line.findLast(Char::isDigit)
        return "$firstNumber$lastNumber".toInt()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf(::getNumber)
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val replacedLine = replacePatterns(line)
            getNumber(replacedLine)
        }
    }

    val input = readInput("day01/Day01")
    part1(input).println()
    part2(input).println()
}
