package day07

import println
import readInput

const val FIVE = 7
const val FOUR = 6
const val FULL_HOUSE = 5
const val THREE = 4
const val TWO_PAIR = 3
const val PAIR = 2
const val HIGH = 1

fun main() {

    data class Hand(val card: String, val bid: Int)

    fun calculateType(card: String): Int {
        val counts = card.groupingBy { it }.eachCount().values
        return when {
            5 in counts -> FIVE
            4 in counts -> FOUR
            3 in counts && 2 in counts -> FULL_HOUSE
            3 in counts -> THREE
            2 in counts && 2 in (counts - 2) -> TWO_PAIR
            2 in counts -> PAIR
            else -> HIGH
        }
    }

    fun calculateTypeWithJoker(card: String): Int {
        var baseRank = calculateType(card.filter { it != 'J' })
        val possibleRankUpgrade = card.count { it == 'J' }
        repeat(possibleRankUpgrade) {
            baseRank = when (baseRank) {
                FOUR -> FIVE
                THREE -> FOUR
                TWO_PAIR -> FULL_HOUSE
                PAIR -> THREE
                HIGH -> PAIR
                else -> baseRank
            }
        }
        return baseRank
    }

    fun part1(input: List<String>): Int {
        val hands = input.map { it.split(" ") }.map { (card, bid) -> Hand(card, bid.toInt()) }
        return hands.sortedWith(
            compareBy(
                { calculateType(it.card) },
                { it.card.map { c -> "0123456789abc"["23456789TJQKA".indexOf(c)] }.joinToString("") })
        ).mapIndexed { index, hand -> hand.bid * (index + 1) }.sum()
    }

    fun part2(input: List<String>): Int {
        val hands = input.map { it.split(" ") }.map { (card, bid) -> Hand(card, bid.toInt()) }
        return hands.sortedWith(
            compareBy(
                { calculateTypeWithJoker(it.card) },
                { it.card.map { c -> "0123456789abc"["J23456789TQKA".indexOf(c)] }.joinToString("") })
        ).mapIndexed { index, hand -> hand.bid * (index + 1) }.sum()
    }

    val input = readInput("day07/Day07")
    part1(input).println()
    part2(input).println()
}