import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.io.File

class Day1 {
    fun main() {
        measureDuration("sequential") {
            solveWith { chunks ->
                chunks.map { it.parseCalories() }.sortedByDescending { it }
            }
        }
        measureDuration("concurrent") {
            solveWith { chunks ->
                runBlocking {
                    chunks.map {
                        async { it.parseCalories() }
                    }.awaitAll().sortedByDescending { it }
                }
            }
        }
    }

    private fun solveWith(block: (List<String>) -> List<Int>) {
        val input = File("/Users/mtthman/Desktop/AdventOfCode/src/main/kotlin/Day1.txt").readText()
        val chunks = input.parseChunks()
        val caloriesList = block(chunks)

        val topElf = caloriesList.take(1).sum()
        val topThreeElves = caloriesList.take(3).sum()

        println("topElf: $topElf, topThreeElves: $topThreeElves")
    }

    private fun String.parseChunks(): List<String> = this.split("\n\n")

    private fun String.parseCalories(): Int = this.split("\n").sumOf { it.toInt() }
}