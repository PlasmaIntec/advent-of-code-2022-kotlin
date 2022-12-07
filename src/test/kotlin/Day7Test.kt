import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day7Test {
    @Test
    fun `constructs filesystem`() {
        val input = """${'$'} cd /
${'$'} ls
dir a
14848514 b.txt
8504156 c.dat
dir d
${'$'} cd a
${'$'} ls
dir e
29116 f
2557 g
62596 h.lst
${'$'} cd e
${'$'} ls
584 i
${'$'} cd ..
${'$'} cd ..
${'$'} cd d
${'$'} ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k""".trimIndent()

        val fs = input.constructFilesystem()
        fs.computeSize()

        assertEquals(48381165, fs.size)
        assertEquals(95437, fs.dfs { it is Directory && it.size < 100000 }.sumOf { it.size })

        assertEquals(24933642, fs.dfs { it is Directory && it.size > 30000000 - 70000000 + fs.size }.minByOrNull { it.size }!!.size)
    }
}