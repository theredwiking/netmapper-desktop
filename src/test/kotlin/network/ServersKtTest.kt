package network

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ServersKtTest {
    @Test
    fun testIsServer() {
        val ports: List<Int> = listOf(22, 80);
        val expected = ServerInfo("45.33.32.156", ports);
        assertEquals(expected, isServer("45.33.32.156"));
    }
}