# Robot Floor Simulator

A Java application that simulates a robot walking on an N×N floor under command control.

## Project Description

This project implements a robot simulator where:
- The robot starts at position [0, 0] facing north
- The robot has a pen that can be up or down
- When the pen is down, the robot traces its path on the floor
- The floor is represented as an N×N array

## Supported Commands

| Command | Description |
|---------|-------------|
| `U` / `u` | Pen up |
| `D` / `d` | Pen down |
| `R` / `r` | Turn right |
| `L` / `l` | Turn left |
| `M s` / `m s` | Move forward s spaces (s is a non-negative integer) |
| `P` / `p` | Print the N×N array with indices |
| `C` / `c` | Print current position, pen state, and facing direction |
| `I n` / `i n` | Initialize system with n×n floor (n > 0) |
| `H` / `h` | Replay all commands since last initialization |
| `Q` / `q` | Quit the program |

## Project Structure

```
robot-floor-simulator/
├── pom.xml                          # Maven configuration
├── README.md                        # This file
└── src/
    ├── main/java/com/robotfloor/
    │   ├── Robot.java              # Robot class with movement and state
    │   ├── Floor.java              # Floor array tracking marks
    │   ├── CommandHistory.java     # Command history tracking
    │   └── RobotSimulator.java     # Main simulator application
    └── test/java/com/robotfloor/
        ├── RobotTest.java          # Unit tests for Robot
        ├── FloorTest.java          # Unit tests for Floor
        └── CommandHistoryTest.java # Unit tests for CommandHistory
```

## Building the Project

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build Commands

```bash
# Clean and build the project
mvn clean install

# Run tests
mvn test

# Package the application
mvn package

# Run the application
java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

## Usage Example

```
=== Robot Floor Simulator ===
Commands: U/D (pen up/down), L/R (turn left/right), M n (move n spaces)
         P (print), C (current position), I n (initialize), H (history), Q (quit)

> Enter command: I 10
System initialized with 10 x 10 floor
> Enter command: C
Position: 0, 0 - Pen: up - Facing: north
> Enter command: D
> Enter command: M 4
> Enter command: R
> Enter command: M 3
> Enter command: P
    0  1  2  3  4  5  6  7  8  9 
 9:                          
 8:                          
 7:                          
 6:                          
 5:                          
 4:  *  *  *  *               
 3:  *                       
 2:  *                       
 1:  *                       
 0:  *                       

> Enter command: C
Position: 3, 4 - Pen: down - Facing: east
> Enter command: Q
Program ended.
```

## Features

- Interactive command-line interface
- Real-time tracking of robot position and pen state
- Visual representation of traced paths on the floor
- Command history with replay capability
- Comprehensive unit tests with JUnit 5
- Maven build automation

## Testing

The project includes comprehensive unit tests:
- **RobotTest**: Tests robot movement, rotation, and state management
- **FloorTest**: Tests floor marking and boundary conditions
- **CommandHistoryTest**: Tests history tracking and replay

Run tests with:
```bash
mvn test
```

## Requirements Met

- ✅ Maven build configuration
- ✅ Unit tests with JUnit
- ✅ Source code on GitHub
- ✅ Interactive command processing
- ✅ Pen up/down state management
- ✅ Movement tracking
- ✅ Floor visualization
- ✅ Command history and replay
- ✅ Initialization and reset capabilities

## Author

Software Testing Project - 2026
