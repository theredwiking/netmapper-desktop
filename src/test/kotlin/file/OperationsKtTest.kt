package file

import network.Ping
import network.Vlan
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class OperationsKtTest {
    // Easy to reuse data across test functions
    private val active: List<Ping> = listOf(Ping("127.0.0.1", true));
    private val vlan: List<Vlan> = listOf(Vlan(active, emptyList()));
    private val data = SaveData(vlan, emptyList())

    @Test
    fun testWrite() {
        assertTrue(write("./test.nmf", data))
    }

    @Test
    fun testRead() {
        val expected: Boolean = true;
        var actualData: SaveData? = null;
        assertEquals(expected, read("./test.nmf") {actualData = it});
        // Check if returned data is expected data save from above function
        assertEquals(data, actualData);
    }

    @Test
    fun testFailedRead() {
        var actualData: SaveData? = null;
        assertFalse(read("./tester.nmf") { actualData = it});
        // Checks data function returns empty SaveData data class if function fails
        assertEquals(SaveData(emptyList(), emptyList()), actualData);
    }
}