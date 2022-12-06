import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day6Test {
    @Test
    fun `sliding window finds first start-of-message marker`() {
        val part1Size = 4
        assertEquals(7, "mjqjpqmgbljsphdztnvjfqwrcgsmlb".findFirstMarker(part1Size))
        assertEquals(5, "bvwbjplbgvbhsrlpgdmjqwftvncz".findFirstMarker(part1Size))
        assertEquals(6, "nppdvjthqldpwncqszvftbrmjlhg".findFirstMarker(part1Size))
        assertEquals(10, "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".findFirstMarker(part1Size))
        assertEquals(11, "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".findFirstMarker(part1Size))

        val part2Size = 14
        assertEquals(19, "mjqjpqmgbljsphdztnvjfqwrcgsmlb".findFirstMarker(part2Size))
        assertEquals(23, "bvwbjplbgvbhsrlpgdmjqwftvncz".findFirstMarker(part2Size))
        assertEquals(23, "nppdvjthqldpwncqszvftbrmjlhg".findFirstMarker(part2Size))
        assertEquals(29, "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".findFirstMarker(part2Size))
        assertEquals(26, "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".findFirstMarker(part2Size))
    }
}