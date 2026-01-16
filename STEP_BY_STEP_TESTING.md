# Robot Floor Simulator - Step-by-Step Testing Tutorial

## 🎯 COMPLETE TESTING WORKFLOW

---

## Phase 1: Initial Setup & Build (5 minutes)

### Step 1.1: Navigate to Project Directory
```powershell
cd e:\SoftwareTesting\Project-softwaretesting\SoftwareTesting-Project
```

### Step 1.2: Verify Project Structure
```powershell
dir /s src
```
Should see:
```
main/java/com/robotfloor/
  - Robot.java
  - Floor.java
  - CommandHistory.java
  - RobotSimulator.java

test/java/com/robotfloor/
  - RobotTest.java
  - FloorTest.java
  - CommandHistoryTest.java
```

### Step 1.3: Build Project
```powershell
mvn clean install
```

Expected output (last lines):
```
[INFO] BUILD SUCCESS
[INFO] Total time: 5-10 seconds
```

---

## Phase 2: Unit Testing (10 minutes)

### Step 2.1: Run All Unit Tests
```powershell
mvn clean test
```

Watch for output:
```
Running com.robotfloor.RobotTest
Running com.robotfloor.FloorTest
Running com.robotfloor.CommandHistoryTest

Tests run: 42, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS ✅
```

### Step 2.2: View Individual Test Results
```powershell
cd target/surefire-reports
Get-Content RobotTest.txt
```

You'll see:
```
Tests run: 18
testInitialPosition OK
testPenDown OK
testMoveNorthOneSpace OK
... (all passed)
```

### Step 2.3: Return to Project Root
```powershell
cd e:\SoftwareTesting\Project-softwaretesting\SoftwareTesting-Project
```

---

## Phase 3: Manual Application Testing (15 minutes)

### Step 3.1: Start the Application
```powershell
java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

You should see:
```
=== Robot Floor Simulator ===
Commands: U/D (pen up/down), L/R (turn left/right), M n (move n spaces)
         P (print), C (current position), I n (initialize), H (history), Q (quit)

> Enter command:
```

---

### Step 3.2: Test 1 - Basic Initialization

**Type:** `I 10`
```
> Enter command: I 10
System initialized with 10 x 10 floor
> Enter command: 
```

✅ **Expected:** System initializes with 10×10 floor

---

### Step 3.3: Test 2 - Check Initial State

**Type:** `C`
```
> Enter command: C
Position: 0, 0 - Pen: up - Facing: north
> Enter command: 
```

✅ **Expected:** Robot at [0,0], pen up, facing north

---

### Step 3.4: Test 3 - Pen Down

**Type:** `D`
```
> Enter command: D
> Enter command: 
```

(No output, but pen state changes)

---

### Step 3.5: Test 4 - Move Forward with Pen Down

**Type:** `M 4`
```
> Enter command: M 4
> Enter command: 
```

✅ **Expected:** Robot moves 4 cells north, marks floor cells

---

### Step 3.6: Test 5 - Check New Position

**Type:** `C`
```
> Enter command: C
Position: 0, 4 - Pen: down - Facing: north
> Enter command: 
```

✅ **Expected:** Position changed to (0,4)

---

### Step 3.7: Test 6 - Turn Right

**Type:** `R`
```
> Enter command: R
> Enter command: 
```

(No output, but direction changes)

---

### Step 3.8: Test 7 - Move East

**Type:** `M 3`
```
> Enter command: M 3
> Enter command: 
```

---

### Step 3.9: Test 8 - Check Position After Rotation

**Type:** `C`
```
> Enter command: C
Position: 3, 4 - Pen: down - Facing: east
> Enter command: 
```

✅ **Expected:** Position is now (3,4), facing east

---

### Step 3.10: Test 9 - Print Floor Visualization

**Type:** `P`
```
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
```

✅ **Expected:** 
- Vertical line from (0,0) to (0,4)
- Horizontal line from (0,4) to (3,4)
- Forms an L-shaped path

---

### Step 3.11: Test 10 - Pen Up (Stop Drawing)

**Type:** `U`
```
> Enter command: U
> Enter command: 
```

---

### Step 3.12: Test 11 - Move with Pen Up

**Type:** `M 3`
```
> Enter command: M 3
> Enter command: 
```

✅ **Expected:** Robot moves but does NOT mark floor

---

### Step 3.13: Test 12 - Verify No New Marks

**Type:** `P`
```
> Enter command: P
    0  1  2  3  4  5  6  7  8  9 
 9:                          
 8:                          
 7:                          
 6:  *                       
 5:  *                       
 4:  *  *  *  *               
 3:  *                       
 2:  *                       
 1:  *                       
 0:  *                       
```

✅ **Expected:** Only marks at (3,4) to (3,7) because pen was up

---

### Step 3.14: Test 13 - Pen Down Again

**Type:** `D`
```
> Enter command: D
> Enter command: 
```

---

### Step 3.15: Test 14 - Mark Current Position

**Type:** `M 2`
```
> Enter command: M 2
> Enter command: 
```

---

### Step 3.16: Test 15 - Final Floor State

**Type:** `P`
```
> Enter command: P
    0  1  2  3  4  5  6  7  8  9 
 9:                          
 8:  *  *                    
 7:  *  *                    
 6:  *  *                    
 5:  *  *                    
 4:  *  *  *  *               
 3:  *                       
 2:  *                       
 1:  *                       
 0:  *                       
```

✅ **Expected:** New marks from (3,7) to (3,9)

---

### Step 3.17: Test 16 - Quit Application

**Type:** `Q`
```
> Enter command: Q
Program ended.
```

✅ **Expected:** Application exits gracefully

---

## Phase 4: Advanced Testing - History Replay (5 minutes)

### Step 4.1: Restart Application
```powershell
java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

### Step 4.2: Run New Scenario
```
I 5
D
M 2
R
M 1
H
Q
```

**Step by step:**

Type `I 5`:
```
> Enter command: I 5
System initialized with 5 x 5 floor
```

Type `D`:
```
> Enter command: D
```

Type `M 2`:
```
> Enter command: M 2
```

Type `R`:
```
> Enter command: R
```

Type `M 1`:
```
> Enter command: M 1
```

Type `H` (History Replay):
```
> Enter command: H
Replaying history...
> Enter command: I 5
System initialized with 5 x 5 floor
> Enter command: D
> Enter command: M 2
> Enter command: R
> Enter command: M 1
History replay complete.
```

✅ **Expected:** All previous commands replayed in order

Type `Q`:
```
> Enter command: Q
Program ended.
```

---

## Phase 5: Edge Case Testing (5 minutes)

### Test Case 1: Out of Bounds Movement

```powershell
java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

Enter:
```
I 3
D
M 10
P
C
Q
```

Expected:
- Robot moves to position (0,10) even though floor is 3×3
- Floor only displays 3×3 grid
- Position shows the actual position beyond grid

---

### Test Case 2: Rotation Testing

```powershell
java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

Enter:
```
I 5
C
R
C
R
C
R
C
R
C
Q
```

Expected:
```
Position: 0, 0 - Pen: up - Facing: north
Position: 0, 0 - Pen: up - Facing: east
Position: 0, 0 - Pen: up - Facing: south
Position: 0, 0 - Pen: up - Facing: west
Position: 0, 0 - Pen: up - Facing: north
```

✅ **Expected:** After 4 right turns, facing returns to north

---

## Phase 6: Test Report Generation (5 minutes)

### Step 6.1: Generate Test Report
```powershell
mvn surefire-report:report
```

### Step 6.2: View HTML Report
```powershell
cd target/site
explorer surefire-report.html
```

This will show:
- Total tests: 42
- Success rate: 100%
- Test execution time
- Details for each test class

---

## 📊 Complete Test Summary

| Phase | Tests | Time | Status |
|-------|-------|------|--------|
| Build | 1 | 5s | ✅ |
| Unit Tests | 42 | 10s | ✅ |
| Manual Tests | 16 | 15 min | ✅ |
| Advanced Tests | 2 | 5 min | ✅ |
| Edge Cases | 2 | 5 min | ✅ |
| **Total** | **63+** | **~40 min** | ✅ |

---

## ✅ Verification Checklist

After completing all phases, verify:

- [ ] Project builds without errors
- [ ] All 42 unit tests pass
- [ ] Application starts successfully
- [ ] Initialization command works (I n)
- [ ] Current position displays correctly (C)
- [ ] Pen up/down toggles (U/D)
- [ ] Movement commands work (M s)
- [ ] Rotation works (L/R)
- [ ] Floor prints with asterisks (P)
- [ ] History replays commands (H)
- [ ] Application quits cleanly (Q)
- [ ] Out of bounds handled correctly
- [ ] Multiple rotations work
- [ ] Pen state affects drawing

---

## 🚀 Final Verification Command

Run this once to confirm everything works:

```powershell
mvn clean install ; java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

Then enter:
```
I 8
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

If you see a perfect square, everything works! ✅

---

## Quick Notes

- **Robot moves forward in the direction it's facing**
- **Pen must be DOWN to draw**
- **Floor indices: top-left is (0,size-1), bottom-left is (0,0)**
- **History stores all commands except 'H' itself**
- **Out-of-bounds movements are allowed but not displayed**
- **Reset happens when you reinitialize (I command)**

Good luck! 🎉
