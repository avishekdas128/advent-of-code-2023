package day08

import println
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        val instruction = input[0]
        val map = buildMap {
            input.drop(2).map { it.split(" = ") }.map { (parent, nodes) ->
                val splitNodes = nodes.replace("(", "").replace(")", "").split(",")
                put(parent.trim(), Pair(splitNodes[0].trim(), splitNodes[1].trim()))
            }
        }
        var steps = 0
        var instructionIndex = 0
        var currentNode = "AAA"
        while (currentNode != "ZZZ") {
            if (instructionIndex == instruction.length) instructionIndex = 0
            val direction = instruction[instructionIndex++]
            val nodePair = map[currentNode]
            nodePair?.let {
                currentNode = if (direction == 'L') it.first else it.second
                steps++
            }
        }
        return steps
    }

    fun lcm(a: Long, b: Long): Long {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == 0L && lcm % b == 0L) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }

    fun part2(input: List<String>): Long {
        val instruction = input[0]
        val map = buildMap {
            input.drop(2).map { it.split(" = ") }.map { (parent, nodes) ->
                val splitNodes = nodes.replace("(", "").replace(")", "").split(",")
                put(parent.trim(), Pair(splitNodes[0].trim(), splitNodes[1].trim()))
            }
        }
        var steps = 0
        val stepsList = mutableListOf<Long>()
        var instructionIndex = 0
        var currentNode = map.keys.filter { it[2] == 'A' }
        while (currentNode.isNotEmpty()) {
            if (instructionIndex == instruction.length) instructionIndex = 0
            val direction = instruction[instructionIndex++]
            val updatedCurrentNode = mutableListOf<String>()
            steps++
            currentNode.forEach { node ->
                val nodePair = map[node]
                nodePair?.let {
                    val end = if (direction == 'L') it.first else it.second
                    if (!end.endsWith('Z'))
                        updatedCurrentNode.add(end)
                    else
                        stepsList.add(steps.toLong())
                }
            }
            currentNode = updatedCurrentNode
        }
        // big brain moment to find lcm of every node's steps to go till 'Z' node
        return stepsList.reduce { acc, i -> lcm(acc, i) }
    }

    val input = readInput("day08/Day08")
    part1(input).println()
    part2(input).println()
}