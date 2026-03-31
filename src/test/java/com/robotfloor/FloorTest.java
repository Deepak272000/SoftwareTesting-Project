package com.robotfloor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class FloorTest {

    @Test
    void testConstructorInitializesGridWithRequestedSize() {
        // Statement Coverage
        // Arrange
        Floor floor = new Floor(4);

        // Act
        int[][] grid = floor.getGrid();

        // Assert
        assertEquals(4, floor.getSize());
        assertNotNull(grid);
        assertEquals(4, grid.length);
        assertEquals(4, grid[0].length);
    }

    @Test
    void testConstructorRejectsZeroAndNegativeSizes() {
        // Decision Coverage, Condition Coverage
        // Arrange / Act / Assert
        assertThrows(IllegalArgumentException.class, () -> new Floor(0));
        assertThrows(IllegalArgumentException.class, () -> new Floor(-1));
    }

    @Test
    void testIsValidPositionCoversIndependentConditionOutcomes() {
        // Condition Coverage, Multiple Condition Coverage
        // Arrange
        Floor floor = new Floor(3);

        // Act / Assert
        assertTrue(floor.isValidPosition(0, 0), "TTTT should be valid");
        assertFalse(floor.isValidPosition(-1, 0), "FTTT should be invalid because x >= 0 is false");
        assertFalse(floor.isValidPosition(3, 0), "TFTT should be invalid because x < size is false");
        assertFalse(floor.isValidPosition(0, -1), "TTFT should be invalid because y >= 0 is false");
        assertFalse(floor.isValidPosition(0, 3), "TTTF should be invalid because y < size is false");
        assertFalse(floor.isValidPosition(-1, -1), "FTFT should be invalid with both lower bounds false");
    }

    @Test
    void testMarkUpdatesOnlyValidPosition() {
        // Statement Coverage, Decision Coverage
        // Arrange
        Floor floor = new Floor(3);

        // Act
        floor.mark(1, 2);
        floor.mark(-1, 2);
        floor.mark(3, 2);

        // Assert
        assertEquals(1, floor.getValue(1, 2));
        assertEquals(0, floor.getValue(0, 2));
        assertEquals(0, floor.getValue(-1, 2));
        assertEquals(0, floor.getValue(3, 2));
    }

    @Test
    void testGetValueReturnsZeroForInvalidCoordinates() {
        // Decision Coverage, Boundary Coverage
        // Arrange
        Floor floor = new Floor(2);
        floor.mark(1, 1);

        // Act / Assert
        assertEquals(1, floor.getValue(1, 1));
        assertEquals(0, floor.getValue(-1, 0));
        assertEquals(0, floor.getValue(0, -1));
        assertEquals(0, floor.getValue(2, 0));
        assertEquals(0, floor.getValue(0, 2));
    }

    @Test
    void testClearResetsPreviouslyMarkedCells() {
        // Statement Coverage
        // Arrange
        Floor floor = new Floor(3);
        floor.mark(0, 0);
        floor.mark(2, 2);

        // Act
        floor.clear();

        // Assert
        for (int y = 0; y < floor.getSize(); y++) {
            for (int x = 0; x < floor.getSize(); x++) {
                assertEquals(0, floor.getValue(x, y));
            }
        }
    }

    @Test
    void testToStringShowsMarkedAndUnmarkedCells() {
        // Statement Coverage, Decision Coverage
        // Arrange
        Floor floor = new Floor(2);
        floor.mark(0, 0);
        floor.mark(1, 1);

        // Act
        String text = floor.toString();

        // Assert
        assertTrue(text.contains(" 1:"));
        assertTrue(text.contains(" 0:"));
        assertTrue(text.contains("*"));
    }

    @Test
    void testPrintWritesFormattedGridToConsole() {
        // Statement Coverage, Loop Decision Coverage
        // Arrange
        Floor floor = new Floor(2);
        floor.mark(1, 0);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            // Act
            floor.print();
        } finally {
            System.setOut(originalOut);
        }

        // Assert
        String printed = output.toString();
        assertTrue(printed.contains(" 0 "));
        assertTrue(printed.contains(" 1 "));
        assertTrue(printed.contains(" 0:"));
        assertTrue(printed.contains(" * "));
    }
}
