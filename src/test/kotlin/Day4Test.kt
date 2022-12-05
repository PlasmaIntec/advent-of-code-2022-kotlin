import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day4Test {
    @Test
    fun `contains checks that either range contains the other`() {
        assertFalse("1-4,5-9".toRangePair().contains())
        assertFalse("1-5,5-9".toRangePair().contains())
        assertTrue("1-5,0-9".toRangePair().contains())
    }

    @Test
    fun `intersects checks that either range intersects the other`() {
        assertFalse("1-4,5-9".toRangePair().intersects())
        assertTrue("1-5,5-9".toRangePair().intersects())
        assertTrue("1-5,0-9".toRangePair().intersects())
    }
}