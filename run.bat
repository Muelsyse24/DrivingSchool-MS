@echo off
chcp 65001 >nul
cd /d "%~dp0"

if not exist "out\abcd\DrivingSchoolManagementSystem.class" (
    echo [!] Classes not found. Run build.bat first.
    pause
    exit /b 1
)

echo Starting Driving School Management System...
java -cp "out;lib/*" abcd.DrivingSchoolManagementSystem
if errorlevel 1 (
    echo.
    echo [!] Program exited with an error.
    pause
)
