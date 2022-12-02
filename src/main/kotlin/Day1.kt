import java.io.File

class Day1 {
    fun main() {
        val input = File("/Users/mtthman/Desktop/AdventOfCode/src/main/kotlin/Day1.txt").readText()
        val chunks = input.parseChunks()
        val caloriesList = chunks.map { it.parseCalories() }.sortedByDescending { it }

        val topElf = caloriesList.take(1).sum()
        val topThreeElves = caloriesList.take(3).sum()

        println("topElf: $topElf, topThreeElves: $topThreeElves")
    }

    private fun String.parseChunks(): List<String> = this.split("\n\n")

    private fun String.parseCalories(): Int = this.split("\n").sumOf { it.toInt() }
}