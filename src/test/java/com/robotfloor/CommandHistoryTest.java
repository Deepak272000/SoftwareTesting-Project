package com.robotfloor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class CommandHistoryTest {

    @Test
    void testAddCommandAndGetCommandPreserveExecutionOrder() {
        // Statement Coverage
        // Arrange
        CommandHistory history = new CommandHistory();

        // Act
        history.addCommand("I 5");
        history.addCommand("D");
        history.addCommand("M 2");

        // Assert
        assertEquals(3, history.size());
        assertEquals("I 5", history.getCommand(0));
        assertEquals("D", history.getCommand(1));
        assertEquals("M 2", history.getCommand(2));
    }

    @Test
    void testGetCommandsReturnsDefensiveCopy() {
        // Decision Coverage
        // Arrange
        CommandHistory history = new CommandHistory();
        history.addCommand("I 4");

        // Act
        List<String> snapshot = history.getCommands();
        snapshot.add("Q");

        // Assert
        assertEquals(1, history.size());
        assertEquals("I 4", history.getCommand(0));
        assertEquals(2, snapshot.size());
    }

    @Test
    void testClearRemovesAllRecordedCommands() {
        // Statement Coverage
        // Arrange
        CommandHistory history = new CommandHistory();
        history.addCommand("U");
        history.addCommand("D");

        // Act
        history.clear();

        // Assert
        assertEquals(0, history.size());
        assertTrue(history.getCommands().isEmpty());
    }

    @Test
    void testGetCommandRejectsInvalidIndex() {
        // Decision Coverage, Boundary Coverage
        // Arrange
        CommandHistory history = new CommandHistory();

        // Act / Assert
        assertThrows(IndexOutOfBoundsException.class, () -> history.getCommand(0));
    }

    @Test
    void testToStringIncludesRecordedCommands() {
        // Statement Coverage
        // Arrange
        CommandHistory history = new CommandHistory();
        history.addCommand("P");

        // Act
        String text = history.toString();

        // Assert
        assertTrue(text.contains("CommandHistory"));
        assertTrue(text.contains("P"));
        assertNotEquals("", text);
    }
}
