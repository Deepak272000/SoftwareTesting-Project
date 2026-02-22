package com.robotfloor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for RobotSimulator command flow across Robot, Floor and CommandHistory.
 */
public class RobotSimulatorIntegrationTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUpOutput() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    public void testThreeCycleMovementWithMixedPenStatesPrintsExpectedShape() {
        RobotSimulator simulator = new RobotSimulator();

        simulator.executeCommand("I 6");

        simulator.executeCommand("D");
        simulator.executeCommand("M 3");
        simulator.executeCommand("R");
        simulator.executeCommand("M 2");

        simulator.executeCommand("U");
        simulator.executeCommand("R");
        simulator.executeCommand("M 2");

        simulator.executeCommand("D");
        simulator.executeCommand("L");
        simulator.executeCommand("M 2");

        simulator.executeCommand("P");
        simulator.executeCommand("C");

        String output = outputStream.toString();

        assertTrue(output.contains("System initialized with 6 x 6 floor"));
        assertTrue(output.contains("Position: 4, 1 - Pen: down - Facing: east"));

        assertTrue(output.contains(" 3:  *  *  *"), "Top path on row 3 should be marked");
        assertTrue(output.contains(" 2:  *"), "Only column 0 should be marked on row 2");
        assertTrue(output.contains(" 1:  *     *  *  *"), "Row 1 should contain vertical and horizontal marked cells");
        assertTrue(output.contains(" 0:  *"), "Origin path should be marked on row 0");
    }

    @Test
    public void testInvalidAndUninitializedCommandsAreReported() {
        RobotSimulator simulator = new RobotSimulator();

        simulator.executeCommand("M 2");
        simulator.executeCommand("P");
        simulator.executeCommand("I 0");
        simulator.executeCommand("I abc");
        simulator.executeCommand("I 3");
        simulator.executeCommand("M -1");
        simulator.executeCommand("M bad");
        simulator.executeCommand("Z");

        String output = outputStream.toString();

        assertTrue(output.contains("Error: System not initialized. Use 'I n' command first."));
        assertTrue(output.contains("Error: Floor size must be greater than zero"));
        assertTrue(output.contains("Error: Invalid floor size. Usage: I <number>"));
        assertTrue(output.contains("Error: Move distance must be non-negative"));
        assertTrue(output.contains("Error: Invalid move distance. Usage: M <number>"));
        assertTrue(output.contains("Unknown command: z"));
    }

    @Test
    public void testHistoryReplayAndQuitFlow() {
        RobotSimulator simulator = new RobotSimulator();

        simulator.executeCommand("I 4");
        simulator.executeCommand("D");
        simulator.executeCommand("M 1");
        simulator.executeCommand("H");
        simulator.executeCommand("Q");

        String output = outputStream.toString();

        assertTrue(output.contains("Replaying history..."));
        assertTrue(output.contains("> Enter command: D"));
        assertTrue(output.contains("> Enter command: M 1"));
        assertTrue(output.contains("History replay complete."));
        assertTrue(output.contains("Program ended."));
    }
}