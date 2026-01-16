# Robot Floor Simulator - Testing Guide

## Quick Start Testing

### Prerequisites
- Java 11 or higher installed
- Maven 3.6 or higher installed
- Project downloaded and extracted

### Project Structure
```
SoftwareTesting-Project/
├── pom.xml                                    (Maven configuration)
├── README.md                                  (Project documentation)
├── src/
│   ├── main/java/com/robotfloor/
│   │   ├── Robot.java                        (Robot model)
│   │   ├── Floor.java                        (Floor grid model)
│   │   ├── CommandHistory.java              (Command tracking)
│   │   └── RobotSimulator.java              (Main application)
│   └── test/java/com/robotfloor/
│       ├── RobotTest.java                   (18 Robot tests)
│       ├── FloorTest.java                   (14 Floor tests)
│       └── CommandHistoryTest.java          (10 History tests)
└── target/                                   (Generated after build)
```

---

## PART 1: Unit Testing

### 1.1 Run All Unit Tests

**Command:**
```bash
mvn clean test
```

**Expected Output:**
```
Tests run: 42, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

**What it tests:**
- Robot movement in all directions (north, east, south, west)
- Pen up/down state management
- Rotation (left/right)
- Floor marking and boundaries
- Command history tracking
- Edge cases (out of bounds, negative values, etc.)

### 1.2 Run Specific Test Class

**Robot Tests Only:**
```bash
mvn test -Dtest=RobotTest
```

**Floor Tests Only:**
```bash
mvn test -Dtest=FloorTest
```

**Command History Tests Only:**
```bash
mvn test -Dtest=CommandHistoryTest
```

### 1.3 Run Specific Test Method

**Example - Test Initial Position:**
```bash
mvn test -Dtest=RobotTest#testInitialPosition
```

### 1.4 View Detailed Test Report

After running tests, view the report:
```bash
cd target/surefire-reports
```

View `RobotTest.txt`, `FloorTest.txt`, or `CommandHistoryTest.txt` for details.

---

## PART 2: Building the Application

### 2.1 Build Without Running Tests

```bash
mvn clean package -DskipTests
```

**Creates two JAR files:**
- `target/robot-floor-simulator-1.0.0.jar` (basic JAR)
- `target/robot-floor-simulator-1.0.0-jar-with-dependencies.jar` (fat JAR - recommended)

### 2.2 Build With Tests

```bash
mvn clean install
```

This compiles, tests, and packages the application.

---

## PART 3: Running the Application - Interactive Mode

### 3.1 Start the Simulator

```bash
java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

### 3.2 Interactive Commands

Once started, you'll see:
```
=== Robot Floor Simulator ===
Commands: U/D (pen up/down), L/R (turn left/right), M n (move n spaces)
         P (print), C (current position), I n (initialize), H (history), Q (quit)

> Enter command:
```

**Available Commands:**

| Command | Description | Example |
|---------|-------------|---------|
| `I n` | Initialize n×n floor | `I 10` |
| `D` | Pen down | `D` |
| `U` | Pen up | `U` |
| `M s` | Move s spaces | `M 5` |
| `R` | Turn right | `R` |
| `L` | Turn left | `L` |
| `C` | Show current status | `C` |
| `P` | Print floor | `P` |
| `H` | Replay history | `H` |
| `Q` | Quit | `Q` |

---

## PART 4: Test Scenarios

### Scenario 1: Basic Movement and Drawing

**Commands to Enter:**
```
I 10
C
D
M 4
R
M 3
P
Q
```

**Expected Output:**
```
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

> Enter command: Q
Program ended.
```

**What it tests:**
- ✅ System initialization
- ✅ Pen up state tracking
- ✅ Pen down state
- ✅ Movement in north direction
- ✅ Rotation to east
- ✅ Floor visualization with asterisks
- ✅ Graceful quit

---

### Scenario 2: Complex Drawing

**Commands to Enter:**
```
I 8
D
M 5
R
M 3
R
M 5
R
M 3
P
Q
```

**Expected Output:**
Square pattern traced on the floor
```
    0  1  2  3  4  5  6  7 
 7:  *  *  *  *  *           
 6:  *                    *
 5:  *                    *
 4:  *                    *
 3:  *  *  *  *  *           
```

**What it tests:**
- ✅ Multiple movements
- ✅ Multiple rotations
- ✅ Pattern drawing
- ✅ Boundary handling

---

### Scenario 3: History Replay

**Commands to Enter:**
```
I 5
D
M 2
R
H
Q
```

**Expected Output:**
```
> Enter command: H
Replaying history...
> Enter command: I 5
System initialized with 5 x 5 floor
> Enter command: D
> Enter command: M 2
> Enter command: R
History replay complete.
```

**What it tests:**
- ✅ Command history tracking
- ✅ History replay functionality
- ✅ State preservation during replay

---

### Scenario 4: Pen Up (No Drawing)

**Commands to Enter:**
```
I 5
U
M 3
R
M 2
D
M 2
P
Q
```

**Expected Output:**
Only the last line should be drawn (after pen down)
```
    0  1  2  3  4 
 4:              
 3:              
 2:  *  *         
 1:              
 0:              
```

**What it tests:**
- ✅ Pen up prevents marking
- ✅ Pen down starts marking
- ✅ Correct position tracking even with pen up

---

### Scenario 5: Boundary Testing

**Commands to Enter:**
```
I 3
D
M 5
P
C
Q
```

**Expected Output:**
Robot moves beyond grid but grid only shows 3×3
```
    0  1  2 
 2:  *  *  *
 1:         
 0:         

Position: 5, 0 - Pen: down - Facing: north
```

**What it tests:**
- ✅ Out-of-bounds movement handling
- ✅ Position tracking beyond grid
- ✅ Grid only displays within bounds

---

## PART 5: Batch Testing (From File)

### 5.1 Create Test File

Create `test_scenario.txt`:
```
I 10
D
M 4
R
M 3
C
P
Q
```

### 5.2 Run with Piped Input

**Windows PowerShell:**
```bash
Get-Content test_scenario.txt | java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

**Linux/Mac:**
```bash
cat test_scenario.txt | java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

---

## PART 6: Step-by-Step Testing Guide

### Step 1: Build the Project
```bash
cd e:\SoftwareTesting\Project-softwaretesting\SoftwareTesting-Project
mvn clean install
```
✅ Should see: `BUILD SUCCESS`

### Step 2: Run All Unit Tests
```bash
mvn test
```
✅ Should see: `Tests run: 42, Failures: 0`

### Step 3: Run Interactive App
```bash
java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

### Step 4: Enter Commands from Scenario 1
Follow the commands from "Scenario 1: Basic Movement and Drawing" above

### Step 5: Verify Output
- Floor displays correctly with asterisks
- Positions are accurate
- Commands execute in sequence

---

## PART 7: Troubleshooting

### Issue: Command not found: `mvn`
**Solution:** Maven not installed. Install Maven or use full path to maven.cmd

### Issue: Cannot find or open JAR
**Solution:** 
```bash
# First, verify JAR exists
dir target\*.jar

# Then run with full path
java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

### Issue: Tests fail
**Solution:**
```bash
# Run with verbose output
mvn test -e
```

### Issue: Application hangs on input
**Solution:** 
- Make sure you're not running interactively with piped input incorrectly
- Use explicit file redirection or proper piping

---

## PART 8: Test Coverage Summary

### What Gets Tested

**Robot Class (18 tests):**
- Initial position [0,0]
- Pen up/down state
- Movement in 4 directions
- Rotation left/right
- Complex multi-move scenarios
- Reset functionality

**Floor Class (14 tests):**
- Floor initialization
- Marking positions
- Out-of-bounds handling
- Grid independence
- Floor clearing

**CommandHistory Class (10 tests):**
- Adding commands
- Retrieving commands
- History clearing
- Multiple command tracking

### Test Results
- **Total Tests:** 42
- **Pass Rate:** 100%
- **Coverage:** Core functionality, edge cases, boundary conditions

---

## Quick Reference Commands

```bash
# Build and test
mvn clean install

# Run only tests
mvn clean test

# Build only (skip tests)
mvn clean package -DskipTests

# Run application
java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator

# Run with file input (PowerShell)
Get-Content commands.txt | java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator

# View test reports
cd target/surefire-reports
type *.txt
```

---

## Expected Behavior Summary

| Input | Expected Behavior |
|-------|-------------------|
| `I 10` | Floor initialized, robot at [0,0], pen up |
| `D` | Pen state changes to down |
| `M 4` | Robot moves 4 cells, marks floor if pen down |
| `R` | Robot rotates 90° clockwise |
| `L` | Robot rotates 90° counter-clockwise |
| `C` | Display position, pen state, direction |
| `P` | Display 10×10 grid with asterisks |
| `H` | Replay all previous commands |
| `Q` | Application terminates |

---

Good luck with testing!
