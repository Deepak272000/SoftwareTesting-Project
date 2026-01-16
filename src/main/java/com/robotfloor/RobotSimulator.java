package com.robotfloor;

import java.util.Scanner;

/**
 * Main Robot Floor Simulator Application
 * Controls the robot movement and command processing
 */
public class RobotSimulator {

    private Robot robot;
    private Floor floor;
    private CommandHistory history;
    private boolean running;

    /**
     * Constructor for RobotSimulator
     */
    public RobotSimulator() {
        this.robot = new Robot();
        this.floor = null;
        this.history = new CommandHistory();
        this.running = true;
    }

    /**
     * Initialize the system with a floor size
     * @param size The size of the floor (N x N)
     */
    public void initialize(int size) {
        if (size <= 0) {
            System.out.println("Error: Floor size must be greater than zero");
            return;
        }
        this.floor = new Floor(size);
        this.robot.reset();
        this.history.clear();
        System.out.println("System initialized with " + size + " x " + size + " floor");
    }

    /**
     * Execute a command
     * @param input The user input command
     */
    public void executeCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            return;
        }

        String trimmed = input.trim();
        char command = Character.toLowerCase(trimmed.charAt(0));

        switch (command) {
            case 'u':
                handlePenUp();
                break;
            case 'd':
                handlePenDown();
                break;
            case 'r':
                handleTurnRight();
                break;
            case 'l':
                handleTurnLeft();
                break;
            case 'm':
                handleMove(trimmed);
                break;
            case 'p':
                handlePrint();
                break;
            case 'c':
                handleCurrentPosition();
                break;
            case 'q':
                handleQuit();
                break;
            case 'i':
                handleInitialize(trimmed);
                break;
            case 'h':
                handleHistory();
                break;
            default:
                System.out.println("Unknown command: " + command);
        }

        if (command != 'h') {
            history.addCommand(input);
        }
    }

    /**
     * Handle pen up command
     */
    private void handlePenUp() {
        robot.penUp();
    }

    /**
     * Handle pen down command
     */
    private void handlePenDown() {
        robot.penDown();
    }

    /**
     * Handle turn right command
     */
    private void handleTurnRight() {
        robot.turnRight();
    }

    /**
     * Handle turn left command
     */
    private void handleTurnLeft() {
        robot.turnLeft();
    }

    /**
     * Handle move command
     * @param input The input string containing the move command and distance
     */
    private void handleMove(String input) {
        if (floor == null) {
            System.out.println("Error: System not initialized. Use 'I n' command first.");
            return;
        }

        try {
            int spaces = parseInteger(input, 1);
            if (spaces < 0) {
                System.out.println("Error: Move distance must be non-negative");
                return;
            }

            // Move and mark the floor
            for (int i = 0; i < spaces; i++) {
                if (robot.isPenDown()) {
                    floor.mark(robot.getX(), robot.getY());
                }
                robot.move(1);
            }

            // Mark the final position if pen is down
            if (robot.isPenDown()) {
                floor.mark(robot.getX(), robot.getY());
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid move distance. Usage: M <number>");
        }
    }

    /**
     * Handle print command
     */
    private void handlePrint() {
        if (floor == null) {
            System.out.println("Error: System not initialized. Use 'I n' command first.");
            return;
        }
        floor.print();
    }

    /**
     * Handle current position command
     */
    private void handleCurrentPosition() {
        System.out.println(robot.toString());
    }

    /**
     * Handle quit command
     */
    private void handleQuit() {
        running = false;
        System.out.println("Program ended.");
    }

    /**
     * Handle initialize command
     * @param input The input string containing the initialize command and size
     */
    private void handleInitialize(String input) {
        try {
            int size = parseInteger(input, 1);
            if (size <= 0) {
                System.out.println("Error: Floor size must be greater than zero");
                return;
            }
            initialize(size);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid floor size. Usage: I <number>");
        }
    }

    /**
     * Handle history replay command
     */
    private void handleHistory() {
        System.out.println("Replaying history...");
        for (String cmd : history.getCommands()) {
            System.out.println("> Enter command: " + cmd);
            executeCommand(cmd);
        }
        System.out.println("History replay complete.");
    }

    /**
     * Parse integer from command string
     * @param input The input string
     * @param startIndex The index to start looking for the integer
     * @return The parsed integer
     */
    private int parseInteger(String input, int startIndex) {
        if (input.length() <= startIndex) {
            throw new NumberFormatException("No integer provided");
        }
        String numStr = input.substring(startIndex).trim();
        return Integer.parseInt(numStr);
    }

    /**
     * Start the interactive simulator
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Robot Floor Simulator ===");
        System.out.println("Commands: U/D (pen up/down), L/R (turn left/right), M n (move n spaces)");
        System.out.println("         P (print), C (current position), I n (initialize), H (history), Q (quit)");
        System.out.println();

        while (running) {
            System.out.print("> Enter command: ");
            String input = scanner.nextLine();
            executeCommand(input);
        }

        scanner.close();
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        RobotSimulator simulator = new RobotSimulator();
        simulator.start();
    }
}
