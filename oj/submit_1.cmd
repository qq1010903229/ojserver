set/p submitlang= <oj\temp\submitlang
set/p submitpid= <oj\temp\submitpid
if not exist "oj\data\%submitpid%\%submitpid%.txt" exit /b 4
if "%submitlang%"=="1" goto c
if "%submitlang%"=="2" goto cpp
exit /b 2
:c
copy oj\temp\submitcode oj\temp\submitcode.c
g++ oj\temp\submitcode.c -o oj\temp\submitcode.exe
if not "%errorlevel%" == "0" exit /b 3
goto execute
:cpp
copy oj\temp\submitcode oj\temp\submitcode.cpp
g++ oj\temp\submitcode.cpp -o oj\temp\submitcode.exe
if not "%errorlevel%" == "0" exit /b 3
goto execute
:execute
for /f %%a in (oj\data\%submitpid%\%submitpid%.txt) do (
copy oj\data\%submitpid%\%%a.in oj\temp\in.in
oj\temp\submitcode.exe <oj\temp\in.in >oj\temp\out.out
fc oj\temp\out.out oj\data\%submitpid%\%%a.out
if errorlevel 1 echo Wrong Answer>oj\temp\submitres1 && exit /b 0
)
echo Accepted>oj\temp\submitres1
exit /b 0