import java.io.File

class Day5 {
    fun main() {
        val input = File("/Users/mtthman/Desktop/AdventOfCode/src/main/kotlin/Day5.txt").readText()
        val (cratesString, moveString) = input.split("\n\n")

        // CrateMover 9000 moves crates one at a time
        val crates1 = cratesString.parseCrates()
        moveString.split("\n").forEach {
            val move = it.parseMoveCommand()
            crates1.executeMoveCommand(move)
        }
        val part1 = crates1.scanTop()
        println(part1)

        // CrateMover 9001 moves multiple crates at a time
        val crates2 = cratesString.parseCrates()
        moveString.split("\n").forEach {
            val move = it.parseMoveCommand()
            crates2.executeMoveCommand(move, true)
        }
        val part2 = crates2.scanTop()
        println(part2)
    }
}

data class MoveCommand(val amount: Int, val from: Int, val to: Int)

fun String.parseCrates(): MutableList<MutableList<String>> {
    val rows = split("\n").map { it.split("").toMutableList() }.toMutableList()
    val cols = transpose(rows)
    return cols
        .filter { isNumeric(it.last()) }
        .map { it.reversed().drop(1).filter { char -> char.isNotBlank() }.toMutableList() }.toMutableList()
}

fun String.parseMoveCommand(): MoveCommand {
    val (amount, from, to) = Regex("move (\\d+) from (\\d+) to (\\d+)").find(this)!!.destructured
    return MoveCommand(amount.toInt(), from.toInt(), to.toInt())
}

fun MutableList<MutableList<String>>.executeMoveCommand(move: MoveCommand, allAtOnce: Boolean = false) {
    val (amount, from, to) = move
    if (allAtOnce) {
        val (remain, transport) = this[from-1].withIndex().partition { it.index < this[from-1].size-amount }
        this[from-1] = remain.map { it.value }.toMutableList()
        this[to-1].addAll(transport.map{ it.value })
    } else {
        repeat(amount) {
            val item = this[from-1].removeLast()
            this[to-1].add(item)
        }
    }
}

fun MutableList<MutableList<String>>.scanTop() = joinToString("") { it.last() }