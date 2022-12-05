import java.io.File

class Day4 {
    fun main() {
        val input = File("/Users/mtthman/Desktop/AdventOfCode/src/main/kotlin/Day4.txt").readText()
        val lines = input.split("\n")

        // find whether one range contains the other range
        val part1 = lines.filter { it.toRangePair().contains() }.size
        println(part1)

        // find whether the ranges intersect
        val part2 = lines.filter { it.toRangePair().intersects() }.size
        println(part2)
    }
}

fun String.toRangePair(): Pair<IntRange, IntRange> {
    val ranges = split(",").map {
        val parts = it.split("-")
        val start = parts.first().toInt()
        val end = parts.last().toInt()
        IntRange(start, end)
    }
    return Pair(ranges.first(), ranges.last())
}

fun Pair<IntRange, IntRange>.contains(): Boolean = first.contains(second) || second.contains(first)

fun IntRange.contains(other: IntRange): Boolean = this.first <= other.first && this.last >= other.last

fun Pair<IntRange, IntRange>.intersects(): Boolean =
    first.contains(second.first) ||
            first.contains(second.last) ||
            second.contains(first.first) ||
            second.contains(first.last)