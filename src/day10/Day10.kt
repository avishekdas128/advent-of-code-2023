package day10

import println
import readInput

data class Pos(val x: Int, val y: Int) {
    operator fun plus(pos: Pos): Pos = Pos(x + pos.x, y + pos.y)
    operator fun minus(pos: Pos): Pos = Pos(x - pos.x, y - pos.y)
}

enum class Direction(val delta: Pos, opposite: () -> Direction) {
    NORTH(Pos(0, -1), { SOUTH }),
    EAST(Pos(1, 0), { WEST }),
    SOUTH(Pos(0, 1), { NORTH }),
    WEST(Pos(-1, 0), { EAST });

    val opposite by lazy(opposite)
}

sealed interface Tile {
    enum class Pipe(val directions: List<Direction>) : Tile {
        VERTICAL(listOf(Direction.NORTH, Direction.SOUTH)),
        HORIZONTAL(listOf(Direction.WEST, Direction.EAST)),
        NE(listOf(Direction.NORTH, Direction.EAST)),
        NW(listOf(Direction.NORTH, Direction.WEST)),
        SW(listOf(Direction.SOUTH, Direction.WEST)),
        SE(listOf(Direction.SOUTH, Direction.EAST)),
    }

    data object Ground : Tile
    data object Start : Tile
}

fun main() {
    fun findLoop(tileMap: List<List<Tile>>): List<Pos> {
        val yStart = tileMap.indexOfFirst { it.contains(Tile.Start) }
        var currentPos = Pos(tileMap[yStart].indexOf(Tile.Start), yStart)
        var direction = Direction.entries.first { direction ->
            val nextPos = currentPos + direction.delta
            val nextTile = tileMap.getOrNull(nextPos.y)?.getOrNull(nextPos.x)
            if (nextTile is Tile.Pipe) direction.opposite in nextTile.directions else false
        }
        var currentTile: Tile
        val loopPos = mutableListOf<Pos>()
        do {
            loopPos.add(currentPos)
            currentPos += direction.delta
            currentTile = tileMap.getOrNull(currentPos.y)?.getOrNull(currentPos.x)!!
            if (currentTile is Tile.Pipe) direction = currentTile.directions.first { it != direction.opposite }
        } while (currentTile != Tile.Start)
        return loopPos
    }

    fun part1(input: List<String>): Int {
        val parsedInput = input.map { line ->
            line.map {
                when (it) {
                    '|' -> Tile.Pipe.VERTICAL
                    '-' -> Tile.Pipe.HORIZONTAL
                    'L' -> Tile.Pipe.NE
                    'J' -> Tile.Pipe.NW
                    '7' -> Tile.Pipe.SW
                    'F' -> Tile.Pipe.SE
                    'S' -> Tile.Start
                    else -> Tile.Ground
                }
            }
        }
        return findLoop(parsedInput).size / 2
    }

    fun getStartPipe(loopPos: List<Pos>): Tile.Pipe {
        val startPos = loopPos[0]
        val directions = listOf(loopPos[1] - startPos, loopPos.last() - startPos)
        return Tile.Pipe.entries.first { pipe -> pipe.directions.all { it.delta in directions } }
    }

    fun part2(input: List<String>): Int {
        val parsedInput = input.map { line ->
            line.map {
                when (it) {
                    '|' -> Tile.Pipe.VERTICAL
                    '-' -> Tile.Pipe.HORIZONTAL
                    'L' -> Tile.Pipe.NE
                    'J' -> Tile.Pipe.NW
                    '7' -> Tile.Pipe.SW
                    'F' -> Tile.Pipe.SE
                    'S' -> Tile.Start
                    else -> Tile.Ground
                }
            }
        }
        val loopPos = findLoop(parsedInput)
        var isInsideLoop = false
        var area = 0
        parsedInput.forEachIndexed { y, line ->
            line.forEachIndexed { x, tile ->
                val pos = Pos(x, y)
                if (pos in loopPos) {
                    val pipe: Tile.Pipe? =
                        if (tile is Tile.Pipe) tile else if (tile is Tile.Start) getStartPipe(loopPos) else null
                    if (pipe?.directions?.contains(Direction.SOUTH) == true) isInsideLoop = !isInsideLoop
                } else if (isInsideLoop) area++
            }
        }
        return area
    }

    val input = readInput("day10/Day10")
    part1(input).println()
    part2(input).println()
}