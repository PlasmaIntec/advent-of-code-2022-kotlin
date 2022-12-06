import java.io.File

class Day6 {
    fun main() {
        val input = File("/Users/mtthman/Desktop/AdventOfCode/src/main/kotlin/Day6.txt").readText()

        val part1 = input.findFirstMarker(4)
        println(part1)

        val part2 = input.findFirstMarker(14)
        println(part2)
    }
}

data class SlidingWindow<K>(val size: Int, val window: MutableMap<K, Int> = mutableMapOf())

fun <K> SlidingWindow<K>.slide(add: K, minus: K) {
    window[add] = window.getOrDefault(add, 0) + 1
    window[minus] = window.getOrDefault(minus, 0) - 1
}

fun <K> SlidingWindow<K>.checkNoRepeat(): Boolean =
    window.entries.sumOf { it.value } == size && window.entries.all { it.value <= 1 }

fun String.findFirstMarker(size: Int): Int {
    val window = SlidingWindow<Char>(size)

    repeat(size) {
        window.window[this[it]] = window.window.getOrDefault(this[it], 0) + 1
    }

    repeat(length - size) {
        if (window.checkNoRepeat()) {
            return it+size
        }
        window.slide(this[it+size], this[it])
    }

    throw Exception("unreachable")
}