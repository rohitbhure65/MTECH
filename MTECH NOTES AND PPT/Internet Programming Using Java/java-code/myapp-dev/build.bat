@echo off
REM ---- Edit this line if Tomcat is somewhere else ----
set CATALINA_HOME=C:\tomcat
set APP=%CATALINA_HOME%\webapps\myapp

if not exist "%APP%\WEB-INF\classes" mkdir "%APP%\WEB-INF\classes"
if not exist "%APP%\WEB-INF\lib" mkdir "%APP%\WEB-INF\lib"

REM 1. make a list of all .java files (works for any number of packages)
dir /s /b src\*.java > sources.txt

REM 2. compile into WEB-INF\classes  (servlet-api.jar comes from Tomcat itself)
javac -encoding UTF-8 -cp "%CATALINA_HOME%\lib\servlet-api.jar" -d "%APP%\WEB-INF\classes" @sources.txt
if errorlevel 1 (echo COMPILE FAILED & exit /b 1)

REM 3. copy html, WEB-INF\web.xml, META-INF\context.xml
xcopy web "%APP%" /E /I /Y > nul

echo Done. Start Tomcat and open http://localhost:8080/myapp/
