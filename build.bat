@echo off
chcp 65001 >nul
cd /d "%~dp0"

echo Compiling...
if not exist out mkdir out
javac -encoding UTF-8 -cp "lib/*" -d out src\abcd\*.java
if errorlevel 1 (
    echo.
    echo [!] Compile failed.
    pause
    exit /b 1
)

echo.
echo Build OK. Run run.bat to start the program.
pause
