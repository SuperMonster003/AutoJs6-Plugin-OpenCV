@ECHO OFF
SETLOCAL
CD /D "%~dp0\..\.."

CALL gradlew.bat :app:verifyOpenCvPublishableApks --stacktrace
IF ERRORLEVEL 1 EXIT /B 1

WHERE python >NUL 2>NUL
IF ERRORLEVEL 1 (
    py -3 scripts\release\prepare_release.py %*
) ELSE (
    python scripts\release\prepare_release.py %*
)
EXIT /B %ERRORLEVEL%
