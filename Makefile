# This file is for Windows.

all: com/qq1010903229/ojserver/Server.class oj/submit.exe

com/qq1010903229/ojserver/Server.class: com/qq1010903229/ojserver/Server.java
	javac com\qq1010903229\ojserver\Server.java

oj/submit.exe: oj/submit.cpp
	g++ oj\submit.cpp -o oj\submit.exe -static-libgcc -g3