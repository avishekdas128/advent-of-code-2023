package day05

import println
import readInput
import kotlin.math.min

fun main() {
    data class AlmanacMap(val destinationStart: Long, val sourceStart: Long, val range: Long)

    fun parseInput(input: List<String>): List<List<AlmanacMap>> = buildList {
        val iterator = input.drop(3).iterator()
        while (iterator.hasNext()) {
            add(
                buildList {
                    while (iterator.hasNext()) {
                        val line = iterator.next()
                        if (line.isEmpty()) {
                            iterator.next()
                            break
                        }
                        val (destinationStart, sourceStart, length) = line.split(" ").map { it.toLong() }
                        add(AlmanacMap(destinationStart, sourceStart, length))
                    }
                }
            )
        }
    }

    fun part1(input: List<String>): Long {
        val seeds = input[0].split(": ")[1].split(" ").map { it.toLong() }
        val maps = parseInput(input)
        return seeds.minOf {
            maps.fold(it) { value, map ->
                map.find { almanacMap -> value in almanacMap.sourceStart..<(almanacMap.sourceStart + almanacMap.range) }
                    ?.let { almanacMap -> value - almanacMap.sourceStart + almanacMap.destinationStart } ?: value
            }
        }
    }

    fun part2(input: List<String>): Long {
        val seeds = input[0].split(": ")[1].split(" ").map { it.toLong() }
        val maps = parseInput(input)
        var small = Long.MAX_VALUE
        var index = 0
        while (index + 1 <= seeds.size) {
            for (seed in seeds[index] until seeds[index] + seeds[index + 1]) {
                small = min(small, maps.fold(seed) { value, map ->
                    map.find { almanacMap -> value in almanacMap.sourceStart..<(almanacMap.sourceStart + almanacMap.range) }
                        ?.let { almanacMap -> value - almanacMap.sourceStart + almanacMap.destinationStart } ?: value
                })
            }
            index += 2
            println(index)
        }
        return small
    }

    val input = readInput("day05/Day05")
    part1(input).println()
    part2(input).println()
}