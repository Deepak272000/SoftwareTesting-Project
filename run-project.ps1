param(
    [switch]$RunApp,
    [string]$Scenario
)

$ErrorActionPreference = 'Stop'

function Write-Step {
    param([string]$Message)
    Write-Host "`n==> $Message" -ForegroundColor Cyan
}

function Fail {
    param([string]$Message)
    Write-Host "ERROR: $Message" -ForegroundColor Red
    exit 1
}

if (-not (Test-Path "pom.xml")) {
    Fail "Please run this script from the project root folder."
}

Write-Step "Running Maven verify (build + tests + JaCoCo coverage)"
mvn clean verify
if ($LASTEXITCODE -ne 0) {
    Fail "Maven verify failed."
}

$mainJar = "target/robot-floor-simulator-1.0.0.jar"
$fatJar = "target/robot-floor-simulator-1.0.0-jar-with-dependencies.jar"
$jacocoReport = "target/site/jacoco/index.html"
$testReports = "target/surefire-reports"

Write-Step "Verification completed successfully"
Write-Host "Generated artifacts:" -ForegroundColor Green
if (Test-Path $mainJar) { Write-Host " - $mainJar" }
if (Test-Path $fatJar) { Write-Host " - $fatJar" }
if (Test-Path $jacocoReport) { Write-Host " - $jacocoReport" }
if (Test-Path $testReports) { Write-Host " - $testReports" }

if ($Scenario) {
    if (-not (Test-Path $Scenario)) {
        Fail "Scenario file '$Scenario' not found."
    }

    Write-Step "Running simulator with scenario file: $Scenario"
    Get-Content $Scenario | java -cp $mainJar com.robotfloor.RobotSimulator
    if ($LASTEXITCODE -ne 0) {
        Fail "Scenario execution failed."
    }
}
elseif ($RunApp) {
    Write-Step "Launching simulator interactively"
    java -cp $mainJar com.robotfloor.RobotSimulator
    if ($LASTEXITCODE -ne 0) {
        Fail "Application launch failed."
    }
}
else {
    Write-Host "`nDone. To launch the app next, run:" -ForegroundColor Yellow
    Write-Host "  .\run-project.ps1 -RunApp"
    Write-Host "Or to run a prepared scenario:" -ForegroundColor Yellow
    Write-Host "  .\run-project.ps1 -Scenario scenario1.txt"
}
