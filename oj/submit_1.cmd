set/p submitlang= <oj\temp\submitlang
set/p submitpid= <oj\temp\submitpid
echo lang=%submitlang% >oj\temp\1
if "%submitlang%"=="1" goto cpp
if "%submitlang%"=="2" goto c
exit /b 2
:cpp
copy oj\temp\submitcode oj\temp\submitcode.cpp
g++ oj\temp\submitcode.cpp -o oj\temp\submitcode.exe
if not "%errorlevel%" == "0" exit /b 3
goto execute
:c
copy oj\temp\submitcode oj\temp\submitcode.c
g++ oj\temp\submitcode.c -o oj\temp\submitcode.exe
if not "%errorlevel%" == "0" exit /b 3
goto execute
:execute
exit /b 0