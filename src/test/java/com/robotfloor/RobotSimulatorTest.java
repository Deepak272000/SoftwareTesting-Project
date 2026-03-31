package com.robotfloor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RobotSimulatorTest {

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
    void testInitializeCreatesFloorResetsRobotAndClearsHistory() {
        // Statement Coverage, Decision Coverage
        // Arrange
        RobotSimulator simulator = new RobotSimulator();
        simulator.executeCommand("D");
        simulator.executeCommand("R");
        simulator.executeCommand("I 2");
        simulator.executeCommand("M 1");

        // Act
        simulator.initialize(4);

        // Assert
        assertNotNull(getFloor(simulator));
        assertEquals(4, getFloor(simulator).getSize());
        assertEquals(0, getRobot(simulator).getX());
        assertEquals(0, getRobot(simulator).getY());
        assertFalse(getRobot(simulator).isPenDown());
        assertEquals(Robot.Direction.NORTH, getRobot(simulator).getFacing());
        assertEquals(0, getHistory(simulator).size());
        assertTrue(output.toString().contains("System initialized with 4 x 4 floor"));
    }

    @Test
    void testInitializeRejectsNonPositiveSizeWithoutCreatingFloor() {
        // Decision Coverage, Condition Coverage
        // Arrange
        RobotSimulator simulator = new RobotSimulator();

        // Act
        simulator.initialize(0);

        // Assert
        assertNull(getFloor(simulator));
        assertTrue(output.toString().contains("Error: Floor size must be greater than zero"));
    }

    @Test
    void testExecuteCommandIgnoresNullBlankAndWhitespaceInput() {
        // Condition Coverage for input == null || input.trim().isEmpty()
        // Arrange
        RobotSimulator simulator = new RobotSimulator();

        // Act
        simulator.executeCommand(null);
        simulator.executeCommand("");
        simulator.executeCommand("   ");

        // Assert
        assertEquals(0, getHistory(simulator).size());
        assertEquals("", output.toString());
    }

    @Test
    void testCommandProcessingUpdatesRobotStateAndRecordsHistory() {
        // Statement Coverage, Decision Coverage
        // Arrange
        RobotSimulator simulator = new RobotSimulator();
        simulator.executeCommand("i 3");

        // Act
        simulator.executeCommand("d");
        simulator.executeCommand("r");
        simulator.executeCommand("l");
        simulator.executeCommand("u");

        // Assert
        Robot robot = getRobot(simulator);
        assertFalse(robot.isPenDown());
        assertEquals(Robot.Direction.NORTH, robot.getFacing());
        assertEquals(5, getHistory(simulator).size());
        assertEquals("i 3", getHistory(simulator).getCommand(0));
        assertEquals("u", getHistory(simulator).getCommand(4));
    }

    @Test
    void testMoveWithPenUpChangesPositionWithoutMarkingFloor() {
        // Statement Coverage, Mutation-killing for pen check
        // Arrange
        RobotSimulator simulator = new RobotSimulator();
        simulator.executeCommand("I 4");

        // Act
        simulator.executeCommand("M 2");

        // Assert
        Robot robot = getRobot(simulator);
        Floor floor = getFloor(simulator);
        assertEquals(0, robot.getX());
        assertEquals(2, robot.getY());
        assertEquals(0, floor.getValue(0, 0));
        assertEquals(0, floor.getValue(0, 1));
        assertEquals(0, floor.getValue(0, 2));
    }

    @Test
    void testMoveWithPenDownMarksEveryVisitedCellIncludingFinalPosition() {
        // Statement Coverage, Decision Coverage, Mutation-killing
        // Arrange
        RobotSimulator simulator = new RobotSimulator();
        simulator.executeCommand("I 5");
        simulator.executeCommand("D");

        // Act
        simulator.executeCommand("M 2");

        // Assert
        Robot robot = getRobot(simulator);
        Floor floor = getFloor(simulator);
        assertEquals(0, robot.getX());
        assertEquals(2, robot.getY());
        assertEquals(1, floor.getValue(0, 0));
        assertEquals(1, floor.getValue(0, 1));
        assertEquals(1, floor.getValue(0, 2));
    }

    @Test
    void testMoveRejectsNegativeDistanceAndLeavesRobotUnchanged() {
        // Decision Coverage, Boundary Coverage
        // Arrange
        RobotSimulator simulator = new RobotSimulator();
        simulator.executeCommand("I 3");

        // Act
        simulator.executeCommand("M -1");

        // Assert
        assertEquals(0, getRobot(simulator).getX());
        assertEquals(0, getRobot(simulator).getY());
        assertTrue(output.toString().contains("Error: Move distance must be non-negative"));
    }

    @Test
    void testMoveRejectsMissingAndNonNumericDistance() {
        // Condition Coverage, Decision Coverage
        // Arrange
        RobotSimulator simulator = new RobotSimulator();
        simulator.executeCommand("I 3");

        // Act
        simulator.executeCommand("M");
        simulator.executeCommand("M bad");

        // Assert
        String console = output.toString();
        assertTrue(console.contains("Error: Invalid move distance. Usage: M <number>"));
    }

    @Test
    void testMoveBeforeInitializationReportsErrorAndStillRecordsCommand() {
        // Decision Coverage
        // Arrange
        RobotSimulator simulator = new RobotSimulator();

        // Act
        simulator.executeCommand("M 1");

        // Assert
        assertTrue(output.toString().contains("Error: System not initialized. Use 'I n' command first."));
        assertEquals(1, getHistory(simulator).size());
        assertEquals("M 1", getHistory(simulator).getCommand(0));
    }

    @Test
    void testPrintBeforeInitializationReportsError() {
        // Decision Coverage
        // Arrange
        RobotSimulator simulator = new RobotSimulator();

        // Act
        simulator.executeCommand("P");

        // Assert
        assertTrue(output.toString().contains("Error: System not initialized. Use 'I n' command first."));
    }

    @Test
    void testCurrentPositionCommandPrintsRobotState() {
        // Statement Coverage
        // Arrange
        RobotSimulator simulator = new RobotSimulator();
        simulator.executeCommand("I 4");
        simulator.executeCommand("D");
        simulator.executeCommand("M 1");

        // Act
        simulator.executeCommand("C");

        // Assert
        assertTrue(output.toString().contains("Position: 0, 1 - Pen: down - Facing: north"));
    }

    @Test
    void testQuitCommandSetsRunningToFalseAndPrintsMessage() {
        // Statement Coverage
        // Arrange
        RobotSimulator simulator = new RobotSimulator();

        // Act
        simulator.executeCommand("Q");

        // Assert
        assertFalse(isRunning(simulator));
        assertTrue(output.toString().contains("Program ended."));
    }

    @Test
    void testUnknownCommandFallsThroughDefaultBranchAndIsRecorded() {
        // Decision Coverage
        // Arrange
        RobotSimulator simulator = new RobotSimulator();

        // Act
        simulator.executeCommand("x");

        // Assert
        assertTrue(output.toString().contains("Unknown command: x"));
        assertEquals(1, getHistory(simulator).size());
        assertEquals("x", getHistory(simulator).getCommand(0));
    }

    @Test
    void testHistoryReplayDoesNotRecordHistoryCommandItself() {
        // Statement Coverage, Decision Coverage
        // Arrange
        RobotSimulator simulator = new RobotSimulator();
        simulator.executeCommand("D");

        // Act
        simulator.executeCommand("H");

        // Assert
        assertTrue(getRobot(simulator).isPenDown());
        assertEquals(2, getHistory(simulator).size());
        assertEquals("D", getHistory(simulator).getCommand(0));
        assertEquals("D", getHistory(simulator).getCommand(1));
        assertTrue(output.toString().contains("Replaying history..."));
        assertTrue(output.toString().contains("History replay complete."));
    }

    @Test
    void testHistoryReplayWithInitializeRebuildsHistoryFromReplayedCommands() {
        // Data Flow Coverage over history definition/use pairs
        // Arrange
        RobotSimulator simulator = new RobotSimulator();
        simulator.executeCommand("I 4");
        simulator.executeCommand("D");
        simulator.executeCommand("M 1");

        // Act
        simulator.executeCommand("H");

        // Assert
        assertEquals(3, getHistory(simulator).size());
        assertEquals("I 4", getHistory(simulator).getCommand(0));
        assertEquals("D", getHistory(simulator).getCommand(1));
        assertEquals("M 1", getHistory(simulator).getCommand(2));
        assertEquals(0, getRobot(simulator).getX());
        assertEquals(1, getRobot(simulator).getY());
        assertEquals(1, getFloor(simulator).getValue(0, 0));
        assertEquals(1, getFloor(simulator).getValue(0, 1));
    }

    @Test
    void testInitializeCommandCoversConditionCombinationsForParsedSize() {
        // Multiple Condition Coverage for size <= 0 and parse success/failure paths
        // Arrange
        RobotSimulator simulator = new RobotSimulator();

        // Act
        simulator.executeCommand("I 1");
        simulator.executeCommand("I 0");
        simulator.executeCommand("I abc");

        // Assert
        String console = output.toString();
        assertTrue(console.contains("System initialized with 1 x 1 floor"));
        assertTrue(console.contains("Error: Floor size must be greater than zero"));
        assertTrue(console.contains("Error: Invalid floor size. Usage: I <number>"));
    }

    private Robot getRobot(RobotSimulator simulator) {
        return (Robot) readField(simulator, "robot");
    }

    private Floor getFloor(RobotSimulator simulator) {
        return (Floor) readField(simulator, "floor");
    }

    private CommandHistory getHistory(RobotSimulator simulator) {
        return (CommandHistory) readField(simulator, "history");
    }

    private boolean isRunning(RobotSimulator simulator) {
        return (boolean) readField(simulator, "running");
    }

    private Object readField(RobotSimulator simulator, String fieldName) {
        try {
            Field field = RobotSimulator.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(simulator);
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("Unable to read field: " + fieldName, exception);
        }
    }
}
