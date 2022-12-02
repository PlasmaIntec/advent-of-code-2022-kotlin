import java.io.File

class Day2 {
    fun main() {
        val input = File("/Users/mtthman/Desktop/AdventOfCode/src/main/kotlin/Day2.txt").readText()
        val lines = input.parseLines()

        // part 1: we have a direct mapping of move to score
        val part1Score = lines.sumOf {
            val (theirs, ours) = it.split(" ")
            ours.moveToScore() + ours.scoreAgainst(theirs)
        }
        println(part1Score)

        // part 2: we need to infer move
        val part2Score = lines.sumOf {
            val (theirs, directive) = it.split(" ")
            val ours = directive.inferMove(theirs)
            ours.moveToScore() + ours.scoreAgainst(theirs)
        }
        println(part2Score)
    }

    val oursList = listOf("X", "Y", "Z")
    val theirsList = listOf("A", "B", "C")

    private fun String.inferMove(theirs: String): String {
        val theirsPos = theirsList.indexOf(theirs)
        val oursPos = when (this) {
            "X" -> theirsPos.prev() // need to lose
            "Y" -> theirsPos // need to draw
            "Z" -> theirsPos.next() // need to win
            else -> throw Exception("not a move")
        }
        return oursList[oursPos]
    }

    private fun String.parseLines(): List<String> = this.split("\n")

    private fun String.moveToScore(): Int {
        return when (this) {
            "A", "X" -> 1
            "B", "Y" -> 2
            "C", "Z" -> 3
            else -> throw Exception("unexpected input")
        }
    }

    private fun String.scoreAgainst(theirs: String): Int {
        val oursPos = oursList.indexOf(this)
        val theirsPos = theirsList.indexOf(theirs)

        val score = when {
            oursPos == theirsPos -> 3 // draw
            oursPos.prev() == theirsPos -> 6 // win
            oursPos.next() == theirsPos -> 0 // lose
            else -> throw Exception("impossible ranking: $oursPos, $theirsPos")
        }
        return score
    }

    private fun Int.next() = when {
        this >= 2 -> 0
        else -> this + 1
    }

    private fun Int.prev() = when {
        this <= 0 -> 2
        else -> this - 1
    }
}