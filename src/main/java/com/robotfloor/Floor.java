package com.robotfloor;

/**
 * Represents the floor (N x N array) on which the robot moves
 * Tracks where the robot has traced with its pen
 */
public class Floor {

    private int[][] grid;
    private int size;

    /**
     * Constructor for Floor
     * @param size The size of the floor (N x N)
     */
    public Floor(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Floor size must be greater than zero");
        }
        this.size = size;
        this.grid = new int[size][size];
    }

    /**
     * Get the size of the floor
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the grid
     */
    public int[][] getGrid() {
        return grid;
    }

    /**
     * Mark a position on the floor
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public void mark(int x, int y) {
        if (isValidPosition(x, y)) {
            grid[y][x] = 1;
        }
    }

    /**
     * Check if a position is within the floor bounds
     * @param x The X coordinate
     * @param y The Y coordinate
     * @return True if the position is valid, false otherwise
     */
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    /**
     * Get the value at a specific position
     * @param x The X coordinate
     * @param y The Y coordinate
     * @return 1 if marked, 0 if not marked
     */
    public int getValue(int x, int y) {
        if (isValidPosition(x, y)) {
            return grid[y][x];
        }
        return 0;
    }

    /**
     * Clear the floor (reset all values to 0)
     */
    public void clear() {
        grid = new int[size][size];
    }

    /**
     * Print the floor to console
     */
    public void print() {
        // Print column indices
        System.out.print("    ");
        for (int i = 0; i < size; i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println();

        // Print the grid
        for (int y = size - 1; y >= 0; y--) {
            System.out.printf("%2d: ", y);
            for (int x = 0; x < size; x++) {
                if (grid[y][x] == 1) {
                    System.out.print(" * ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Get string representation of the floor
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Print column indices
        sb.append("    ");
        for (int i = 0; i < size; i++) {
            sb.append(String.format("%2d ", i));
        }
        sb.append("\n");

        // Print the grid
        for (int y = size - 1; y >= 0; y--) {
            sb.append(String.format("%2d: ", y));
            for (int x = 0; x < size; x++) {
                if (grid[y][x] == 1) {
                    sb.append(" * ");
                } else {
                    sb.append("   ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
