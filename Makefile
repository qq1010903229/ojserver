# This file is for Windows.

all: Server.class oj/ready.exe oj/submit.exe

Server.class: Server.java
	javac Server.java

oj/submit.exe: oj/submit.cpp
	g++ oj\submit.cpp -o oj\submit.exe -static-libgcc -g3