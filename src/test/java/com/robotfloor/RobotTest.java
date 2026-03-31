package com.robotfloor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RobotTest {

    @Test
    void testRobotStartsAtOriginWithPenUpFacingNorth() {
        // Statement Coverage
        // Arrange
        Robot robot = new Robot();

        // Act / Assert
        assertEquals(0, robot.getX());
        assertEquals(0, robot.getY());
        assertFalse(robot.isPenDown());
        assertEquals(Robot.Direction.NORTH, robot.getFacing());
    }

    @Test
    void testPenStateTransitionsCoverBothBooleanStates() {
        // Decision Coverage
        // Arrange
        Robot robot = new Robot();

        // Act
        robot.penDown();
        boolean afterPenDown = robot.isPenDown();
        robot.penUp();

        // Assert
        assertTrue(afterPenDown);
        assertFalse(robot.isPenDown());
    }

    @Test
    void testTurnRightCyclesThroughAllDirections() {
        // Statement Coverage, Decision Coverage
        // Arrange
        Robot robot = new Robot();

        // Act / Assert
        robot.turnRight();
        assertEquals(Robot.Direction.EAST, robot.getFacing());

        robot.turnRight();
        assertEquals(Robot.Direction.SOUTH, robot.getFacing());

        robot.turnRight();
        assertEquals(Robot.Direction.WEST, robot.getFacing());

        robot.turnRight();
        assertEquals(Robot.Direction.NORTH, robot.getFacing());
    }

    @Test
    void testTurnLeftCyclesThroughAllDirections() {
        // Statement Coverage, Decision Coverage
        // Arrange
        Robot robot = new Robot();

        // Act / Assert
        robot.turnLeft();
        assertEquals(Robot.Direction.WEST, robot.getFacing());

        robot.turnLeft();
        assertEquals(Robot.Direction.SOUTH, robot.getFacing());

        robot.turnLeft();
        assertEquals(Robot.Direction.EAST, robot.getFacing());

        robot.turnLeft();
        assertEquals(Robot.Direction.NORTH, robot.getFacing());
    }

    @Test
    void testMoveZeroAndPositiveSpacesCoverLoopFalseAndTrueBranches() {
        // Decision Coverage, Boundary Coverage
        // Arrange
        Robot robot = new Robot();

        // Act
        robot.move(0);
        int xAfterZeroMove = robot.getX();
        int yAfterZeroMove = robot.getY();
        robot.move(2);

        // Assert
        assertEquals(0, xAfterZeroMove);
        assertEquals(0, yAfterZeroMove);
        assertEquals(0, robot.getX());
        assertEquals(2, robot.getY());
    }

    @Test
    void testMoveFollowsFacingVectorInEveryDirection() {
        // Condition Coverage
        // Arrange
        Robot northRobot = new Robot();
        Robot eastRobot = new Robot();
        Robot southRobot = new Robot();
        Robot westRobot = new Robot();

        // Act
        northRobot.move(1);

        eastRobot.turnRight();
        eastRobot.move(1);

        southRobot.turnRight();
        southRobot.turnRight();
        southRobot.move(1);

        westRobot.turnLeft();
        westRobot.move(1);

        // Assert
        assertEquals(0, northRobot.getX());
        assertEquals(1, northRobot.getY());

        assertEquals(1, eastRobot.getX());
        assertEquals(0, eastRobot.getY());

        assertEquals(0, southRobot.getX());
        assertEquals(-1, southRobot.getY());

        assertEquals(-1, westRobot.getX());
        assertEquals(0, westRobot.getY());
    }

    @Test
    void testResetRestoresInitialStateAfterMovementAndPenChange() {
        // Statement Coverage
        // Arrange
        Robot robot = new Robot();
        robot.penDown();
        robot.turnRight();
        robot.move(3);

        // Act
        robot.reset();

        // Assert
        assertEquals(0, robot.getX());
        assertEquals(0, robot.getY());
        assertFalse(robot.isPenDown());
        assertEquals(Robot.Direction.NORTH, robot.getFacing());
    }

    @Test
    void testToStringReflectsCurrentState() {
        // Statement Coverage
        // Arrange
        Robot robot = new Robot();
        robot.penDown();
        robot.move(2);
        robot.turnRight();

        // Act
        String description = robot.toString();

        // Assert
        assertTrue(description.contains("Position: 0, 2"));
        assertTrue(description.contains("Pen: down"));
        assertTrue(description.contains("Facing: east"));
    }
}
