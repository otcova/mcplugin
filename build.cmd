@echo off & setlocal
cmd /c mvn package
copy ".\target\Octopus-1.0.0.jar" "C:\Users\Octova\Desktop\Swap2\plugins\Octopus.jar"
