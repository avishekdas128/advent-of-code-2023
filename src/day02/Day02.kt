package day02

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val splitString = it.split(":")
            val gameId = splitString[0].split(" ").last().toInt()
            val gameSets = splitString[1].split(";")
            var isGamePossible = true
            run breaking@{
                gameSets.forEach { sets ->
                    var redCount = 0
                    var blueCount = 0
                    var greenCount = 0
                    val games = sets.split(",")
                    games.forEach { game ->
                        val gameSplit = game.trimStart().trimEnd().split(" ")
                        val cubeCount = gameSplit[0].toInt()
                        when (gameSplit[1]) {
                            "red" -> redCount += cubeCount
                            "blue" -> blueCount += cubeCount
                            "green" -> greenCount += cubeCount
                        }
                    }
                    isGamePossible = redCount <= 12 && blueCount <= 14 && greenCount <= 13
                    if (isGamePossible.not()) return@breaking
                }
            }
            if (isGamePossible) sum += gameId
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val splitString = it.split(":")
            val gameSets = splitString[1].split(";")
            var redCount = 1
            var blueCount = 1
            var greenCount = 1
            gameSets.forEach { sets ->
                val games = sets.split(",")
                games.forEach { game ->
                    val gameSplit = game.trimStart().trimEnd().split(" ")
                    val cubeCount = gameSplit[0].toInt()
                    when (gameSplit[1]) {
                        "red" -> redCount = redCount.coerceAtLeast(cubeCount)
                        "blue" -> blueCount = blueCount.coerceAtLeast(cubeCount)
                        "green" -> greenCount = greenCount.coerceAtLeast(cubeCount)
                    }
                }
            }
            val totalPower = redCount * blueCount * greenCount
            sum += totalPower
        }
        return sum
    }

    val input = readInput("day02/Day02")
    part1(input).println()
    part2(input).println()
}
