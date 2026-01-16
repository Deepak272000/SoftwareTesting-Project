package com.robotfloor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Floor class
 */
public class FloorTest {

    private Floor floor;

    @BeforeEach
    public void setUp() {
        floor = new Floor(10);
    }

    @Test
    public void testFloorInitialization() {
        assertEquals(10, floor.getSize(), "Floor size should be 10");
        assertNotNull(floor.getGrid(), "Grid should not be null");
    }

    @Test
    public void testFloorGridAllZeros() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals(0, floor.getValue(i, j), "All cells should be initialized to 0");
            }
        }
    }

    @Test
    public void testInvalidFloorSize() {
        assertThrows(IllegalArgumentException.class, () -> new Floor(0));
        assertThrows(IllegalArgumentException.class, () -> new Floor(-5));
    }

    @Test
    public void testMarkPosition() {
        floor.mark(0, 0);
        assertEquals(1, floor.getValue(0, 0), "Marked position should have value 1");
    }

    @Test
    public void testMarkMultiplePositions() {
        floor.mark(0, 0);
        floor.mark(1, 1);
        floor.mark(5, 5);

        assertEquals(1, floor.getValue(0, 0));
        assertEquals(1, floor.getValue(1, 1));
        assertEquals(1, floor.getValue(5, 5));
    }

    @Test
    public void testMarkSamePositionMultipleTimes() {
        floor.mark(3, 3);
        floor.mark(3, 3);
        floor.mark(3, 3);
        assertEquals(1, floor.getValue(3, 3), "Marking same position multiple times should keep value as 1");
    }

    @Test
    public void testIsValidPosition() {
        assertTrue(floor.isValidPosition(0, 0), "0, 0 should be valid");
        assertTrue(floor.isValidPosition(9, 9), "9, 9 should be valid");
        assertTrue(floor.isValidPosition(5, 5), "5, 5 should be valid");

        assertFalse(floor.isValidPosition(-1, 0), "-1, 0 should be invalid");
        assertFalse(floor.isValidPosition(0, -1), "0, -1 should be invalid");
        assertFalse(floor.isValidPosition(10, 0), "10, 0 should be invalid");
        assertFalse(floor.isValidPosition(0, 10), "0, 10 should be invalid");
    }

    @Test
    public void testMarkOutOfBounds() {
        // Should not throw exception, just ignore
        floor.mark(-1, 0);
        floor.mark(0, -1);
        floor.mark(10, 0);
        floor.mark(0, 10);

        // All positions should still be 0
        assertEquals(0, floor.getValue(-1, 0));
        assertEquals(0, floor.getValue(0, -1));
        assertEquals(0, floor.getValue(10, 0));
        assertEquals(0, floor.getValue(0, 10));
    }

    @Test
    public void testGetValueOutOfBounds() {
        assertEquals(0, floor.getValue(-1, 0), "Out of bounds should return 0");
        assertEquals(0, floor.getValue(0, -1), "Out of bounds should return 0");
        assertEquals(0, floor.getValue(10, 0), "Out of bounds should return 0");
    }

    @Test
    public void testClear() {
        floor.mark(0, 0);
        floor.mark(5, 5);
        floor.mark(9, 9);

        floor.clear();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals(0, floor.getValue(i, j), "All values should be 0 after clear");
            }
        }
    }

    @Test
    public void testSmallFloor() {
        Floor smallFloor = new Floor(1);
        assertEquals(1, smallFloor.getSize());
        smallFloor.mark(0, 0);
        assertEquals(1, smallFloor.getValue(0, 0));
    }

    @Test
    public void testLargeFloor() {
        Floor largeFloor = new Floor(100);
        assertEquals(100, largeFloor.getSize());
        largeFloor.mark(0, 0);
        largeFloor.mark(99, 99);
        assertEquals(1, largeFloor.getValue(0, 0));
        assertEquals(1, largeFloor.getValue(99, 99));
    }

    @Test
    public void testToString() {
        String output = floor.toString();
        assertNotNull(output);
        assertTrue(output.length() > 0);
    }

    @Test
    public void testGridIndependence() {
        Floor floor1 = new Floor(5);
        Floor floor2 = new Floor(5);

        floor1.mark(0, 0);
        assertEquals(1, floor1.getValue(0, 0));
        assertEquals(0, floor2.getValue(0, 0), "Marking floor1 should not affect floor2");
    }
}
