import java.io.File

class Day3 {
    fun main() {
        solve()
    }

    private fun solve() {
        val input = File("/Users/mtthman/Desktop/AdventOfCode/src/main/kotlin/Day3.txt").readText()
        val chunks = input.split("\n")

        // split each line in half
        val part1 = chunks.sumOf {
            getPriority(
                listOf(
                    it.subSequence(0, it.length/2).toString(),
                    it.subSequence(it.length/2, it.length).toString()
                )
            )
        }
        println(part1)

        // chunks of three lines
        val part2 = chunks.chunked(3).sumOf { getPriority(it) }
        println(part2)
    }

    private fun getPriority(rucksacks: List<String>): Int {
        val sharedItem = rucksacks.fold(rucksacks.first().toSet()) { acc, cur -> acc.intersect(cur.toSet()) }
        return sharedItem.first().toPriority()
    }

    private fun Char.toPriority(): Int =
        if (isUpperCase()) {
            code-64+26
        } else {
            code-96
        }
}