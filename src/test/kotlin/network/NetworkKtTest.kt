package network

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NetworkKtTest {
    @Test
    fun testPing() {
        val expected = Ping("45.33.32.156", true);
        assertEquals(expected, ping("45.33.32.156"));
    }
}