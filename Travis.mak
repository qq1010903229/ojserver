# This file is for Travis CI.

all: oj/submit.o

oj/submit.o: oj/submit.cpp
	gcc oj/submit.cpp -o oj/submit.o -c