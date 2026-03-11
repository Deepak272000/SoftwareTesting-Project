# Robot Floor Simulator

![Java](https://img.shields.io/badge/Java-11%2B-blue)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36)
![Tests](https://img.shields.io/badge/Tests-45-success)
![Coverage](https://img.shields.io/badge/Coverage-90%25%2B-brightgreen)

A Java/Maven command-line simulator that controls a robot moving on an `N x N` floor, tracks pen state, records command history, and includes automated unit, integration, and coverage verification.

## Project Summary

This repository contains the full Software Testing project deliverables for:

- **Task 1**: robot simulator implementation and unit testing
- **Task 2**: integration testing, code coverage analysis, CI/CD validation, and release-readiness evaluation

The simulator supports initialization, movement, turning, pen control, floor printing, current-state reporting, command replay, and program termination.

## Quick Start

```bash
# 1. One-command build, test, and coverage run
./run-project.bat

# 2. Run the simulator interactively
./run-project.bat -RunApp

# 3. Or run a prepared scenario
./run-project.bat -Scenario scenario1.txt
```

After verification, open the JaCoCo HTML report here:

- `target/site/jacoco/index.html`

The runner scripts automatically:

- run `mvn clean verify`
- generate JaCoCo coverage output
- generate Surefire test reports
- optionally launch the simulator or run a prepared scenario

## Supported Commands

| Command | Description |
|---------|-------------|
| `U` / `u` | Pen up |
| `D` / `d` | Pen down |
| `R` / `r` | Turn right |
| `L` / `l` | Turn left |
| `M s` / `m s` | Move forward `s` spaces (`s` is a non-negative integer) |
| `P` / `p` | Print the `N x N` floor with indices |
| `C` / `c` | Print current position, pen state, and facing direction |
| `I n` / `i n` | Initialize system with `n x n` floor (`n > 0`) |
| `H` / `h` | Replay all commands since last initialization |
| `Q` / `q` | Quit the program |

## Repository Structure

```text
SoftwareTesting-Project/
├── .github/workflows/
│   ├── build-and-test.yml
│   └── code-quality.yml
├── pom.xml
├── README.md
├── run-project.ps1
├── run-project.bat
├── TESTING_GUIDE.md
├── CI_CD_SETUP.md
├── VISUAL_TESTING_GUIDE.md
├── scenario1.txt
├── scenario2.txt
├── scenario3.txt
├── scenario4.txt
├── scenario5.txt
├── src/
│   ├── main/java/com/robotfloor/
│   │   ├── Robot.java
│   │   ├── Floor.java
│   │   ├── CommandHistory.java
│   │   └── RobotSimulator.java
│   └── test/java/com/robotfloor/
│       ├── RobotTest.java
│       ├── FloorTest.java
│       ├── CommandHistoryTest.java
│       └── RobotSimulatorIntegrationTest.java
└── target/
    ├── surefire-reports/
    ├── site/jacoco/
    ├── robot-floor-simulator-1.0.0.jar
    └── robot-floor-simulator-1.0.0-jar-with-dependencies.jar
```

## Core Features

- Interactive command-line robot control
- Pen up/down path drawing on a floor grid
- Direction changes and movement tracking
- Command history and replay support
- Unit tests for core classes
- Integration tests for end-to-end command workflows
- JaCoCo coverage analysis and release gates
- GitHub Actions CI/CD workflows for validation

## Build, Test, and Run

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

### Common Commands

```bash
# Clean and build project
mvn clean install

# Run unit and integration tests
mvn clean test

# Run tests + JaCoCo coverage + verification gates
mvn clean verify

# Package application JARs
mvn clean package

# Run the main simulator
java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator

# Run a prepared scenario from PowerShell
Get-Content scenario1.txt | java -cp target/robot-floor-simulator-1.0.0.jar com.robotfloor.RobotSimulator
```

## Demo Scenarios

Prepared input files are included for quick demonstrations:

- `scenario1.txt`
- `scenario2.txt`
- `scenario3.txt`
- `scenario4.txt`
- `scenario5.txt`

These can be piped directly into the simulator for repeatable demos and visual checks.

## Usage Example

```text
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

## Test Suite

The current automated suite contains **45 tests**:

- **RobotTest** - 18 unit tests for movement, rotation, and pen state
- **FloorTest** - 14 unit tests for marking, printing, and bounds behavior
- **CommandHistoryTest** - 10 unit tests for history recording and replay support
- **RobotSimulatorIntegrationTest** - 3 integration tests for end-to-end command flows

### Integration Test Coverage

The integration test file validates:

- multi-step movement with mixed pen states and expected floor shape output
- invalid and uninitialized command handling
- history replay and quit flow behavior

Test reports are generated under `target/surefire-reports/`.

## Code Coverage and Release Readiness

JaCoCo is configured through Maven to generate HTML/XML coverage reports and enforce minimum verification gates.

### Coverage Command

```bash
mvn clean verify
```

### Coverage Report Location

- HTML report: `target/site/jacoco/index.html`
- XML report: `target/site/jacoco/jacoco.xml`

### Current Coverage Summary

- **Method coverage**: 49/51 = **96.08%**
- **Instruction coverage**: 715/769 = **92.98%**
- **Branch coverage**: 66/73 = **90.41%**
- **Line coverage**: 189/209 = **90.43%**
- **Complexity coverage**: 84/92 = **91.30%**

These results support the Task 2 release-readiness decision and show strong automated coverage across the core application logic.

### Maven Verification Gates

Configured in `pom.xml`:

- Method coverage >= 80%
- Line coverage >= 80%
- Branch coverage >= 70%

These gates are checked automatically during `mvn clean verify`.

## CI/CD Workflows

GitHub Actions workflows are included for automated validation:

- **build-and-test.yml**
    - Java matrix testing (11 and 17)
    - build + test execution
    - Surefire/site artifact publishing
- **code-quality.yml**
    - `mvn clean verify`
    - package and artifact generation

### Branch Workflow

The repository follows a staged workflow:

- `Deepak-dev` -> development work
- `Deepak-QA` -> QA validation branch
- `main` -> release-ready code

Workflow triggers are configured for push and pull request validation on these branches.

## Release Readiness Summary

The software was approved for release based on two conditions:

- all automated tests passed successfully (`45/45`)
- measured coverage exceeded the predefined report thresholds for function, statement, path approximation, condition, and line coverage

This decision is documented in the project report and supported by the generated JaCoCo and Surefire outputs.

## Build Artifacts

The Maven build generates:

- `target/robot-floor-simulator-1.0.0.jar` - main application JAR
- `target/robot-floor-simulator-1.0.0-jar-with-dependencies.jar` - runnable fat JAR
- `target/surefire-reports/` - test execution reports
- `target/site/jacoco/` - coverage reports

## Project Documentation

Additional documentation in this repository:

- `TESTING_GUIDE.md` - full testing and coverage notes
- `CI_CD_SETUP.md` - CI/CD setup and workflow details
- `VISUAL_TESTING_GUIDE.md` - visual/manual testing guidance
- `test_commands.txt` - prepared test command sequences
- `scenario1.txt` to `scenario5.txt` - demo scenarios

## GitHub Repository

Project repository:

- <https://github.com/Deepak272000/SoftwareTesting-Project>

## Author

Deepak Sunil Chavan  
Software Testing Project - 2026
