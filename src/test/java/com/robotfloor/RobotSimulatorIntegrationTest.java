package com.robotfloor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RobotSimulatorIntegrationTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testIntegratedCommandSequenceProducesExpectedTraceAndStatus() {
        // Statement Coverage, Decision Coverage
        // Arrange
        RobotSimulator simulator = new RobotSimulator();

        // Act
        simulator.executeCommand("I 5");
        simulator.executeCommand("D");
        simulator.executeCommand("M 2");
        simulator.executeCommand("R");
        simulator.executeCommand("M 1");
        simulator.executeCommand("P");
        simulator.executeCommand("C");

        // Assert
        String console = output.toString();
        assertTrue(console.contains("System initialized with 5 x 5 floor"));
        assertTrue(console.contains(" 2:"));
        assertTrue(console.contains(" 1:"));
        assertTrue(console.contains(" 0:"));
        assertTrue(console.contains(" * "));
        assertTrue(console.contains("Position: 1, 2 - Pen: down - Facing: east"));
    }

    @Test
    void testInvalidCommandFlowReportsUserFacingErrors() {
        // Decision Coverage, Condition Coverage
        // Arrange
        RobotSimulator simulator = new RobotSimulator();

        // Act
        simulator.executeCommand("M");
        simulator.executeCommand("P");
        simulator.executeCommand("I abc");
        simulator.executeCommand("I 0");
        simulator.executeCommand("I 2");
        simulator.executeCommand("M -1");
        simulator.executeCommand("Z");

        // Assert
        String console = output.toString();
        assertTrue(console.contains("Error: System not initialized. Use 'I n' command first."));
        assertTrue(console.contains("Error: Invalid floor size. Usage: I <number>"));
        assertTrue(console.contains("Error: Floor size must be greater than zero"));
        assertTrue(console.contains("Error: Move distance must be non-negative"));
        assertTrue(console.contains("Unknown command: z"));
    }
}
