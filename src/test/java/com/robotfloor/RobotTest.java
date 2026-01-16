package com.robotfloor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Robot class
 */
public class RobotTest {

    private Robot robot;

    @BeforeEach
    public void setUp() {
        robot = new Robot();
    }

    @Test
    public void testInitialPosition() {
        assertEquals(0, robot.getX(), "Initial X position should be 0");
        assertEquals(0, robot.getY(), "Initial Y position should be 0");
    }

    @Test
    public void testInitialPenState() {
        assertFalse(robot.isPenDown(), "Initial pen state should be up");
    }

    @Test
    public void testInitialFacing() {
        assertEquals(Robot.Direction.NORTH, robot.getFacing(), "Initial facing direction should be north");
    }

    @Test
    public void testPenDown() {
        robot.penDown();
        assertTrue(robot.isPenDown(), "Pen should be down after penDown()");
    }

    @Test
    public void testPenUp() {
        robot.penDown();
        robot.penUp();
        assertFalse(robot.isPenDown(), "Pen should be up after penUp()");
    }

    @Test
    public void testMoveNorthOneSpace() {
        robot.move(1);
        assertEquals(0, robot.getX(), "X position should be 0");
        assertEquals(1, robot.getY(), "Y position should be 1 after moving north");
    }

    @Test
    public void testMoveNorthMultipleSpaces() {
        robot.move(5);
        assertEquals(0, robot.getX(), "X position should be 0");
        assertEquals(5, robot.getY(), "Y position should be 5 after moving north 5 spaces");
    }

    @Test
    public void testTurnRight() {
        robot.turnRight();
        assertEquals(Robot.Direction.EAST, robot.getFacing(), "Should face east after turning right from north");
    }

    @Test
    public void testTurnRightMultipleTimes() {
        robot.turnRight(); // EAST
        robot.turnRight(); // SOUTH
        robot.turnRight(); // WEST
        robot.turnRight(); // NORTH
        assertEquals(Robot.Direction.NORTH, robot.getFacing(), "Should face north after turning right 4 times");
    }

    @Test
    public void testTurnLeft() {
        robot.turnLeft();
        assertEquals(Robot.Direction.WEST, robot.getFacing(), "Should face west after turning left from north");
    }

    @Test
    public void testTurnLeftMultipleTimes() {
        robot.turnLeft(); // WEST
        robot.turnLeft(); // SOUTH
        robot.turnLeft(); // EAST
        robot.turnLeft(); // NORTH
        assertEquals(Robot.Direction.NORTH, robot.getFacing(), "Should face north after turning left 4 times");
    }

    @Test
    public void testMoveEastAfterTurningRight() {
        robot.turnRight(); // Face EAST
        robot.move(3);
        assertEquals(3, robot.getX(), "X position should be 3 after moving east");
        assertEquals(0, robot.getY(), "Y position should be 0");
    }

    @Test
    public void testMoveSouthAfterTurningTwice() {
        robot.turnRight(); // EAST
        robot.turnRight(); // SOUTH
        robot.move(2);
        assertEquals(0, robot.getX(), "X position should be 0");
        assertEquals(-2, robot.getY(), "Y position should be -2 after moving south");
    }

    @Test
    public void testComplexMovement() {
        // Move north 4 spaces
        robot.move(4);
        assertEquals(0, robot.getX());
        assertEquals(4, robot.getY());

        // Turn right and move east 3 spaces
        robot.turnRight();
        robot.move(3);
        assertEquals(3, robot.getX());
        assertEquals(4, robot.getY());

        // Turn left and move north 2 spaces
        robot.turnLeft();
        robot.move(2);
        assertEquals(3, robot.getX());
        assertEquals(6, robot.getY());
    }

    @Test
    public void testReset() {
        robot.penDown();
        robot.move(5);
        robot.turnRight();

        robot.reset();

        assertEquals(0, robot.getX());
        assertEquals(0, robot.getY());
        assertFalse(robot.isPenDown());
        assertEquals(Robot.Direction.NORTH, robot.getFacing());
    }

    @Test
    public void testDirectionToString() {
        assertEquals("north", Robot.Direction.NORTH.toString());
        assertEquals("east", Robot.Direction.EAST.toString());
        assertEquals("south", Robot.Direction.SOUTH.toString());
        assertEquals("west", Robot.Direction.WEST.toString());
    }

    @Test
    public void testToString() {
        String output = robot.toString();
        assertTrue(output.contains("Position: 0, 0"));
        assertTrue(output.contains("Pen: up"));
        assertTrue(output.contains("Facing: north"));
    }

    @Test
    public void testToStringWithPenDown() {
        robot.penDown();
        robot.move(2);
        robot.turnRight();
        String output = robot.toString();
        assertTrue(output.contains("Position: 0, 2"));
        assertTrue(output.contains("Pen: down"));
        assertTrue(output.contains("Facing: east"));
    }
}
