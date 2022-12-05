import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day5Test {
    @Test
    fun `parse stacks of crate`() {
        val input = """
    [D]
[N] [C]
[Z] [M] [P]
 1   2   3""".split("\n").drop(1).joinToString("\n")
        val stacks = input.parseCrates()

        assertEquals(3, stacks.size)
        assertEquals(listOf("Z", "N"), stacks[0])
        assertEquals(listOf("M", "C", "D"), stacks[1])
        assertEquals(listOf("P"), stacks[2])
    }

    @Test
    fun `parse move command`() {
        val input = "move 7 from 3 to 9"
        val (amount, from, to) = input.parseMoveCommand()

        assertEquals(7, amount)
        assertEquals(3, from)
        assertEquals(9, to)
    }

    @Test
    fun `execute move command`() {
        val stacks = mutableListOf(
            mutableListOf("Z", "N"),
            mutableListOf("M", "C", "D"),
            mutableListOf("P")
        )
        val move = MoveCommand(1, 2, 3)

        stacks.executeMoveCommand(move)

        assertEquals(3, stacks.size)
        assertEquals(listOf("Z", "N"), stacks[0])
        assertEquals(listOf("M", "C"), stacks[1])
        assertEquals(listOf("P", "D"), stacks[2])
    }

    @Test
    fun `execute move command all at once`() {
        val stacks = mutableListOf(
            mutableListOf("Z", "N"),
            mutableListOf("M", "C", "D"),
            mutableListOf("P")
        )
        val move = MoveCommand(3, 2, 3)

        stacks.executeMoveCommand(move, true)

        assertEquals(3, stacks.size)
        assertEquals(listOf("Z", "N"), stacks[0])
        assertEquals(listOf<String>(), stacks[1])
        assertEquals(listOf("P", "M", "C", "D"), stacks[2])
    }
}