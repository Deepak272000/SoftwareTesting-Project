package com.robotfloor;

/**
 * Represents the Robot that moves on the floor
 * The robot has a position, pen state (up/down), and facing direction
 */
public class Robot {

    /**
     * Enum for the direction the robot is facing
     */
    public enum Direction {
        NORTH(0, 1),
        EAST(1, 0),
        SOUTH(0, -1),
        WEST(-1, 0);

        private final int dx;
        private final int dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public int getDx() {
            return dx;
        }

        public int getDy() {
            return dy;
        }

        /**
         * Turn right (clockwise)
         */
        public Direction turnRight() {
            return values()[(this.ordinal() + 1) % 4];
        }

        /**
         * Turn left (counter-clockwise)
         */
        public Direction turnLeft() {
            return values()[(this.ordinal() - 1 + 4) % 4];
        }

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    private int x;
    private int y;
    private boolean penDown;
    private Direction facing;

    /**
     * Constructor for Robot
     * Initializes at position [0, 0] with pen up and facing north
     */
    public Robot() {
        this.x = 0;
        this.y = 0;
        this.penDown = false;
        this.facing = Direction.NORTH;
    }

    /**
     * Get the X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Get the Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Check if pen is down
     */
    public boolean isPenDown() {
        return penDown;
    }

    /**
     * Get the current facing direction
     */
    public Direction getFacing() {
        return facing;
    }

    /**
     * Put the pen down
     */
    public void penDown() {
        this.penDown = true;
    }

    /**
     * Put the pen up
     */
    public void penUp() {
        this.penDown = false;
    }

    /**
     * Turn the robot right
     */
    public void turnRight() {
        this.facing = this.facing.turnRight();
    }

    /**
     * Turn the robot left
     */
    public void turnLeft() {
        this.facing = this.facing.turnLeft();
    }

    /**
     * Move the robot forward by the specified number of spaces
     * @param spaces Number of spaces to move forward
     */
    public void move(int spaces) {
        for (int i = 0; i < spaces; i++) {
            x += facing.getDx();
            y += facing.getDy();
        }
    }

    /**
     * Reset the robot to initial state at [0, 0]
     */
    public void reset() {
        this.x = 0;
        this.y = 0;
        this.penDown = false;
        this.facing = Direction.NORTH;
    }

    @Override
    public String toString() {
        return String.format("Position: %d, %d - Pen: %s - Facing: %s",
                x, y, penDown ? "down" : "up", facing.toString());
    }
}
