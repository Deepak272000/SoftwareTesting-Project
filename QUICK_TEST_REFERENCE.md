# Robot Floor Simulator - Quick Test Commands Reference

## Files to Run

### 1. UNIT TESTS (Automated)
**Location:** `src/test/java/com/robotfloor/`

**Files:**
- `RobotTest.java` - 18 tests
- `FloorTest.java` - 14 tests
- `CommandHistoryTest.java` - 10 tests

**How to Run:**
```
mvn clean test
```

---

## Main Application File

**Location:** `src/main/java/com/robotfloor/`

**Files:**
- `RobotSimulator.java` - Main entry point
- `Robot.java` - Robot logic
- `Floor.java` - Floor grid logic
- `CommandHistory.java` - History tracking

**How to Run:**
```
java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

---

## Test Scenarios - Copy & Paste These

### TEST 1: Simple Line Drawing
```
I 10
D
M 5
P
C
Q
```
**Expected:** Vertical line from (0,0) to (0,5)

---

### TEST 2: Square Drawing
```
I 10
D
M 4
R
M 4
R
M 4
R
M 4
P
Q
```
**Expected:** 4×4 square pattern

---

### TEST 3: Pen Up/Down Switching
```
I 10
D
M 3
U
M 2
D
M 3
P
Q
```
**Expected:** Two separate line segments

---

### TEST 4: Test History Replay
```
I 5
D
M 2
R
M 1
H
Q
```
**Expected:** Commands replay automatically

---

### TEST 5: Edge Cases
```
I 3
D
M 10
P
C
Q
```
**Expected:** Robot moves beyond grid, only 3×3 displayed

---

## Command Reference at a Glance

```
U       → Pen UP
D       → Pen DOWN
L       → Turn LEFT
R       → Turn RIGHT
M 5     → Move 5 spaces
P       → Print floor
C       → Current position
I 10    → Initialize 10x10 floor
H       → History replay
Q       → Quit
```

---

## Build & Run Steps (Fast Track)

### Step 1: Navigate to project
```
cd e:\SoftwareTesting\Project-softwaretesting\SoftwareTesting-Project
```

### Step 2: Build
```
mvn clean install
```
⏱️ Takes ~3-5 seconds

### Step 3: Run Tests
```
mvn test
```
✅ Should pass all 42 tests

### Step 4: Run Application
```
java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

### Step 5: Enter Commands
Copy a scenario from above and paste commands one by one, pressing Enter after each

---

## What Each Class Tests

### RobotTest.java (18 tests)
- ✅ Initial state (position 0,0, pen up, facing north)
- ✅ Pen up/down toggle
- ✅ Movement in 4 directions
- ✅ Left/right rotation
- ✅ Complex movement sequences
- ✅ State reset

### FloorTest.java (14 tests)
- ✅ Floor initialization with size
- ✅ Marking floor positions
- ✅ Boundary checking
- ✅ Out-of-bounds handling
- ✅ Floor clearing
- ✅ Grid independence

### CommandHistoryTest.java (10 tests)
- ✅ Adding commands
- ✅ Retrieving commands
- ✅ Clearing history
- ✅ Order preservation
- ✅ Size tracking

---

## Sample Output Explanations

### When you run: `I 10`
```
System initialized with 10 x 10 floor
```
- Creates a 10×10 grid
- Places robot at [0, 0]
- Sets pen to UP
- Sets facing to NORTH

### When you run: `D` then `M 4`
```
(No visible output, but robot moves and draws)
```
- Pen is now DOWN
- Robot moves 4 cells north
- Floor marks cells [0,0], [0,1], [0,2], [0,3], [0,4]

### When you run: `P`
```
    0  1  2  3  4  5  6  7  8  9
 9:                          
 8:                          
 7:                          
 6:                          
 5:                          
 4:  *                       
 3:  *                       
 2:  *                       
 1:  *                       
 0:  *                       
```
- Top shows column indices
- Left shows row indices
- `*` marks traced cells
- Blank spaces are unmarked cells

### When you run: `C`
```
Position: 0, 4 - Pen: down - Facing: north
```
- X position: 0
- Y position: 4
- Pen state: down
- Direction: north (↑)

---

## Directory Structure After Build

```
SoftwareTesting-Project/
├── src/
│   ├── main/java/com/robotfloor/
│   │   ├── Robot.java
│   │   ├── Floor.java
│   │   ├── CommandHistory.java
│   │   └── RobotSimulator.java
│   └── test/java/com/robotfloor/
│       ├── RobotTest.java
│       ├── FloorTest.java
│       └── CommandHistoryTest.java
│
├── target/
│   ├── classes/                    (Compiled main code)
│   ├── test-classes/               (Compiled test code)
│   ├── robot-floor-simulator-1.0.0.jar       (JAR)
│   ├── robot-floor-simulator-1.0.0-jar-with-dependencies.jar
│   └── surefire-reports/           (Test reports)
│
├── pom.xml                         (Maven config)
├── README.md                       (Project docs)
├── TESTING_GUIDE.md               (Detailed testing)
└── test_commands.txt              (Sample commands)
```

---

## Time to Complete Each Test

| Test | Time |
|------|------|
| Unit Tests (all 42) | 5-10 seconds |
| Single Scenario | 1-2 minutes |
| Full Manual Testing | 10-15 minutes |
| Build from Scratch | 3-5 seconds |

---

## Common Issues & Fixes

**Q: Maven not found**
A: Use full path: `C:\Users\Deepak Chavan\.maven\maven-3.9.12\bin\mvn.cmd`

**Q: JAR not found**
A: Build first: `mvn clean package`

**Q: Tests fail**
A: Run: `mvn clean test -e` for error details

**Q: Application won't start**
A: Ensure Java is installed: `java -version`

---

## Success Checklist

- [ ] Project builds successfully
- [ ] All 42 unit tests pass
- [ ] Application starts without errors
- [ ] Can enter commands and see output
- [ ] Floor displays with asterisks
- [ ] Position updates correctly
- [ ] Pen up/down works
- [ ] Rotation works
- [ ] History replay works
- [ ] Quit exits cleanly

---

**Ready to test? Start with:**
```
mvn clean test
```
