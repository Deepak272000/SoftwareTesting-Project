package com.robotfloor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for CommandHistory class
 */
public class CommandHistoryTest {

    private CommandHistory history;

    @BeforeEach
    public void setUp() {
        history = new CommandHistory();
    }

    @Test
    public void testHistoryInitialization() {
        assertEquals(0, history.size(), "History should be empty on initialization");
    }

    @Test
    public void testAddSingleCommand() {
        history.addCommand("I 10");
        assertEquals(1, history.size(), "History size should be 1");
    }

    @Test
    public void testAddMultipleCommands() {
        history.addCommand("I 10");
        history.addCommand("D");
        history.addCommand("M 5");
        history.addCommand("P");
        assertEquals(4, history.size(), "History size should be 4");
    }

    @Test
    public void testGetCommands() {
        history.addCommand("U");
        history.addCommand("D");
        List<String> commands = history.getCommands();
        assertEquals(2, commands.size());
        assertEquals("U", commands.get(0));
        assertEquals("D", commands.get(1));
    }

    @Test
    public void testGetCommand() {
        history.addCommand("I 10");
        history.addCommand("D");
        history.addCommand("M 5");
        assertEquals("I 10", history.getCommand(0));
        assertEquals("D", history.getCommand(1));
        assertEquals("M 5", history.getCommand(2));
    }

    @Test
    public void testClear() {
        history.addCommand("U");
        history.addCommand("D");
        history.addCommand("M 3");
        assertEquals(3, history.size());

        history.clear();
        assertEquals(0, history.size(), "History should be empty after clear");
    }

    @Test
    public void testGetCommandsReturnsNewList() {
        history.addCommand("U");
        List<String> commands1 = history.getCommands();
        history.addCommand("D");
        List<String> commands2 = history.getCommands();

        assertEquals(1, commands1.size(), "First list should have 1 element");
        assertEquals(2, commands2.size(), "Second list should have 2 elements");
    }

    @Test
    public void testAddCommandWithSpaces() {
        history.addCommand("M 10");
        history.addCommand("I 20");
        assertEquals("M 10", history.getCommand(0));
        assertEquals("I 20", history.getCommand(1));
    }

    @Test
    public void testAddCommandsInOrderOfExecution() {
        String[] commands = {"I 5", "D", "M 3", "R", "M 2", "P", "C", "Q"};
        for (String cmd : commands) {
            history.addCommand(cmd);
        }

        assertEquals(8, history.size());
        for (int i = 0; i < commands.length; i++) {
            assertEquals(commands[i], history.getCommand(i), "Commands should be in order of execution");
        }
    }

    @Test
    public void testToString() {
        history.addCommand("U");
        history.addCommand("D");
        String output = history.toString();
        assertNotNull(output);
        assertTrue(output.contains("CommandHistory"));
    }
}
