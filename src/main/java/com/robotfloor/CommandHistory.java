package com.robotfloor;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks the history of all commands executed
 */
public class CommandHistory {

    private List<String> commands;

    /**
     * Constructor for CommandHistory
     */
    public CommandHistory() {
        this.commands = new ArrayList<>();
    }

    /**
     * Add a command to history
     * @param command The command to add
     */
    public void addCommand(String command) {
        commands.add(command);
    }

    /**
     * Get all commands
     */
    public List<String> getCommands() {
        return new ArrayList<>(commands);
    }

    /**
     * Clear the history
     */
    public void clear() {
        commands.clear();
    }

    /**
     * Get the number of commands in history
     */
    public int size() {
        return commands.size();
    }

    /**
     * Get a specific command by index
     */
    public String getCommand(int index) {
        return commands.get(index);
    }

    @Override
    public String toString() {
        return "CommandHistory{" +
                "commands=" + commands +
                '}';
    }
}
