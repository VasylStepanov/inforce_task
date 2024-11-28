@echo off

REM Перевіряємо кількість введених аргументів
if "%~1"=="" (
    echo No arguments provided. Starting the application without parameter replacement.
    java -jar build\libs\Inforce_Task-1.0.jar
    exit /b 0
)

if "%~3"=="" (
    echo Error: You must provide exactly 3 arguments: DB_URL DB_USERNAME DB_PASSWORD.
    echo Usage: replace_params.bat DB_URL DB_USERNAME DB_PASSWORD
    exit /b 1
)

REM Якщо є 3 аргументи, виконуємо заміну
set "DB_URL=%~1"
set "DB_USERNAME=%~2"
set "DB_PASSWORD=%~3"

REM Шлях до persistence.xml
set "PERSISTENCE_FILE=src\main\resources\META-INF\persistence.xml"

REM Замінюємо параметри в файлі persistence.xml
powershell -Command "(Get-Content %PERSISTENCE_FILE%) -replace '\$\{DB_URL\}', '%DB_URL%' | Set-Content %PERSISTENCE_FILE%"
powershell -Command "(Get-Content %PERSISTENCE_FILE%) -replace '\$\{DB_USERNAME\}', '%DB_USERNAME%' | Set-Content %PERSISTENCE_FILE%"
powershell -Command "(Get-Content %PERSISTENCE_FILE%) -replace '\$\{DB_PASSWORD\}', '%DB_PASSWORD%' | Set-Content %PERSISTENCE_FILE%"

echo Parameters replaced in %PERSISTENCE_FILE%.

REM Запускаємо програму
java -jar build\libs\Inforce_Task-1.0.jar