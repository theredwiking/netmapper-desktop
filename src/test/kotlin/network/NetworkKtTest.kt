package network

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NetworkKtTest {
    @Test
    fun testPing() {
        val expected: Ping = Ping("127.0.0.1", true);
        assertEquals(expected, ping("127.0.0.1"));
    }
}