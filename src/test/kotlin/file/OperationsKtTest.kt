package file

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class OperationsKtTest {
    @Test
    fun testWrite() {
        val expected: Boolean = true;
        assertEquals(expected, write("./test.nmf", SaveData(emptyList(), emptyList())))
    }

    @Test
    fun testRead() {
        val expected: Boolean = true;
        val expectedData = SaveData(emptyList(), emptyList());
        var actuallyData: SaveData? = null;
        assertEquals(expected, read("./test.nmf") {actuallyData = it});
        assertEquals(expectedData, actuallyData);
    }
}