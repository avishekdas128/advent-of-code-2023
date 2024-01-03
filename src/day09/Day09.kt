package day09

import println
import readInput

fun main() {

    fun part1(input: List<String>): Long {
        return input.map { it.split(" ").map { number -> number.trim().toLong() } }.sumOf { history ->
            val pyramid = mutableListOf<List<Long>>()
            pyramid.add(history)
            while (true) {
                val tempRow = mutableListOf<Long>()
                for (i in 1 until pyramid.last().size) tempRow.add(pyramid.last()[i] - pyramid.last()[i - 1])
                pyramid.add(tempRow)
                if (tempRow.all { it == 0L }) break
            }
            pyramid.sumOf { it.last() }
        }
    }

    fun part2(input: List<String>): Long {
        return input.map { it.split(" ").map { number -> number.trim().toLong() } }.map { it.reversed() }
            .sumOf { history ->
                val pyramid = mutableListOf<List<Long>>()
                pyramid.add(history)
                while (true) {
                    val tempRow = mutableListOf<Long>()
                    for (i in 1 until pyramid.last().size) tempRow.add(pyramid.last()[i] - pyramid.last()[i - 1])
                    pyramid.add(tempRow)
                    if (tempRow.all { it == 0L }) break
                }
                pyramid.sumOf { it.last() }
            }
    }

    val input = readInput("day09/Day09")
    part1(input).println()
    part2(input).println()
}