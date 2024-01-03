package day11

import println
import readInput
import kotlin.math.abs

fun main() {
    data class Pos(val x: Long, val y: Long)

    fun part1(input: List<String>): Long {
        val columnsToAdd = input[0].indices.filter { index -> input.map { it[index] }.all { it == '.' } }
        val rowsToAdd = input.mapIndexed { index, line -> index to line }.filter { (_, line) -> line.all { it == '.' } }
            .map { it.first }
        val galaxies = buildList {
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    if (c == '#') add(
                        Pos(
                            x + 1L * (columnsToAdd.count { it in 0..<x }),
                            y + 1L * (rowsToAdd.count { it in 0..<y })
                        )
                    )
                }
            }
        }
        val pairs =
            buildList { galaxies.forEachIndexed { i, a -> galaxies.forEachIndexed { j, b -> if (i < j) add(a to b) } } }
        return pairs.sumOf { (a, b) -> abs(a.x - b.x) + abs(a.y - b.y) }
    }

    fun part2(input: List<String>): Long {
        val columnsToAdd = input[0].indices.filter { index -> input.map { it[index] }.all { it == '.' } }
        val rowsToAdd = input.mapIndexed { index, line -> index to line }.filter { (_, line) -> line.all { it == '.' } }
            .map { it.first }
        val galaxies = buildList {
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, c ->
                    if (c == '#') add(
                        Pos(
                            x + 999999L * (columnsToAdd.count { it in 0..<x }),
                            y + 999999L * (rowsToAdd.count { it in 0..<y })
                        )
                    )
                }
            }
        }
        val pairs =
            buildList { galaxies.forEachIndexed { i, a -> galaxies.forEachIndexed { j, b -> if (i < j) add(a to b) } } }
        return pairs.sumOf { (a, b) -> abs(a.x - b.x) + abs(a.y - b.y) }
    }

    val input = readInput("day11/Day11")
    part1(input).println()
    part2(input).println()
}