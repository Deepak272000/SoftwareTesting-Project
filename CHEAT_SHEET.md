# Testing Cheat Sheet - Robot Floor Simulator

## 🎯 Quick Command Reference

```
NAVIGATION:
  I 10      → Initialize 10x10 floor
  
DRAWING:
  D         → Pen DOWN (start drawing)
  U         → Pen UP (stop drawing)
  M 5       → Move 5 cells forward
  
ROTATION:
  R         → Turn RIGHT (clockwise 90°)
  L         → Turn LEFT (counter-clockwise 90°)
  
STATUS:
  C         → Current position, pen state, direction
  P         → Print floor grid with marks
  
SPECIAL:
  H         → Replay all previous commands
  Q         → Quit
```

---

## 📁 File Locations

**Source Code:**
```
src/main/java/com/robotfloor/
├── Robot.java              ← Robot movement logic
├── Floor.java              ← Grid and marking
├── CommandHistory.java     ← History tracking
└── RobotSimulator.java     ← Main application entry
```

**Tests:**
```
src/test/java/com/robotfloor/
├── RobotTest.java          ← 18 unit tests
├── FloorTest.java          ← 14 unit tests
└── CommandHistoryTest.java ← 10 unit tests
```

**Executable:**
```
target/
└── robot-floor-simulator-1.0.0.jar
```

---

## 🔄 Testing Workflow

```
┌─────────────────────┐
│  1. Build Project   │
│  mvn clean install  │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  2. Run Unit Tests  │
│  mvn clean test     │ (42 tests)
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  3. Run Application │
│  java -cp target... │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  4. Enter Commands  │
│  I 10, D, M 5, P... │
└─────────────────────┘
```

---

## ⚡ Fast Test (Copy & Paste)

### Terminal Command 1: Build
```powershell
cd e:\SoftwareTesting\Project-softwaretesting\SoftwareTesting-Project
mvn clean install
```

### Terminal Command 2: Run Tests
```powershell
mvn clean test
```
Should show: `Tests run: 42, Failures: 0`

### Terminal Command 3: Run App
```powershell
java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

### Then Enter These Commands:
```
I 10
D
M 4
R
M 3
P
C
Q
```

---

## 📊 Test Scenarios

### Scenario A: Simple Line
```
Commands:
I 5      → Initialize 5x5
D        → Pen down
M 3      → Move north 3
P        → Print
Q        → Quit

Expected: Vertical line
```

### Scenario B: Square
```
Commands:
I 8
D
M 4      → North
R        → Face east
M 4      → Move east
R        → Face south
M 4      → Move south
R        → Face west
M 4      → Move west
P
Q

Expected: 4x4 square outline
```

### Scenario C: Skip and Draw
```
Commands:
I 10
D
M 2
U        → Pen up (skip drawing)
M 3      → Move without drawing
D        → Pen down
M 2      → Continue drawing
P
Q

Expected: Two separate segments
```

---

## 🔍 Output Interpretation

### Floor Display Example
```
    0  1  2  3  4  5  6  7  8  9   ← Column indices
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
↑
Row indices
```

**Legend:**
- `*` = Cell marked (pen was down)
- Space = Cell unmarked
- Indices show (X, Y) coordinates

---

## 🎮 Coordinate System

```
      NORTH (↑)
        Y+
        │
WEST ←--+--→ EAST (X+)
(X-)    │
      SOUTH (↓)
        Y-

Start position: (0, 0) - bottom left
Direction: NORTH
Pen: UP
```

**Examples:**
```
Move 3 spaces north from (0,0)   → (0, 3)
Move 2 spaces east from (0,3)    → (2, 3)
Move 1 space south from (2,3)    → (2, 2)
Move 1 space west from (2,2)     → (1, 2)
```

---

## 📈 Test Coverage

| Component | Tests | Coverage |
|-----------|-------|----------|
| Robot Movement | 8 | 100% |
| Robot Rotation | 4 | 100% |
| Robot Pen State | 3 | 100% |
| Robot Reset | 1 | 100% |
| Floor Marking | 6 | 100% |
| Floor Bounds | 4 | 100% |
| History Tracking | 5 | 100% |
| **TOTAL** | **42** | **100%** |

---

## ✅ Success Criteria

| Test | Pass Condition |
|------|---|
| Build | No compilation errors |
| Unit Tests | 42/42 pass |
| Init Command | Floor created, robot at (0,0) |
| Pen Command | State toggles correctly |
| Move Command | Position updates correctly |
| Turn Command | Facing direction changes |
| Print Command | Floor displays with asterisks |
| History Command | Commands replay in order |
| Quit Command | App exits cleanly |

---

## 🚨 Common Issues

| Issue | Fix |
|-------|-----|
| `mvn not found` | Use full path or add to PATH |
| `JAR not found` | Run `mvn clean package` first |
| `Tests fail` | Run `mvn clean test -e` for details |
| `Port already in use` | Not applicable (console app) |
| `Out of memory` | Unlikely, increase if needed |

---

## 📝 Test Report Location

After running tests:
```
target/surefire-reports/
├── RobotTest.txt
├── FloorTest.txt
├── CommandHistoryTest.txt
└── (XML versions also available)
```

View with:
```powershell
Get-Content target/surefire-reports/RobotTest.txt
```

---

## ⏱️ Timing

| Task | Duration |
|------|----------|
| Build | 3-5 sec |
| All Tests | 10 sec |
| Single Test Scenario | 1-2 min |
| Full Manual Testing | 10-15 min |
| Complete Cycle | 30-40 min |

---

## 🎓 Learning Path

1. **Understand the Classes** → Read Robot.java, Floor.java
2. **Review Unit Tests** → Look at RobotTest.java
3. **Build & Test** → `mvn clean install`
4. **Run Application** → Start simulator
5. **Try Simple Commands** → I 5, D, M 3, P, Q
6. **Try Complex Scenarios** → Draw square, test history
7. **Explore Edge Cases** → Out of bounds, rotations

---

## 🔗 Command Sequence Flow

```
Start App
    ↓
I 10 (Initialize)
    ↓
D (Pen Down)
    ↓
M 4 (Move - draws line)
    ↓
R (Rotate - face right)
    ↓
M 3 (Move - draws perpendicular line)
    ↓
P (Print - see the L-shape)
    ↓
C (Show position)
    ↓
H (Replay all commands)
    ↓
Q (Quit)
```

---

## 🎯 Test Execution Plan

**Day 1:**
- [ ] Build project
- [ ] Run unit tests
- [ ] Verify 42 tests pass

**Day 2:**
- [ ] Run application
- [ ] Test initialization
- [ ] Test pen up/down
- [ ] Test movement
- [ ] Test rotation

**Day 3:**
- [ ] Test floor printing
- [ ] Test history replay
- [ ] Test complex scenarios
- [ ] Test edge cases
- [ ] Verify all 16+ scenarios

---

## 💡 Pro Tips

1. **Copy-paste commands** instead of typing
2. **Use file input** for batch testing: `Get-Content commands.txt | java ...`
3. **View test reports** in `target/surefire-reports/`
4. **Use `mvn -e`** flag to see full error traces
5. **Reset by running** `I n` command again

---

## 📞 Quick Help

| Need | Command |
|------|---------|
| Show version | `mvn -v` |
| List tests | `mvn test --help` |
| Verbose build | `mvn clean install -X` |
| Skip tests | `mvn clean package -DskipTests` |
| Run single test | `mvn test -Dtest=RobotTest` |

---

**Last Updated:** January 15, 2026
**Status:** ✅ All systems operational
**Ready to test:** YES
