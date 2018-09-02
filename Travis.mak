# This file is for Travis CI.

all: Server.class oj/submit.o

Server.class: Server.java
	javac Server.java

oj/submit.o: oj/submit.cpp
	gcc oj/submit.cpp -o oj/submit.o -c