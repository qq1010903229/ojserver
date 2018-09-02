#include <cstdlib>
#include <cstring>
#include <windows.h>
#include <iostream>
using namespace std;
char c[2000]="start /wait ";
int main(int argc,char** argv){
	Sleep(1000);
	strcpy(c+12,argv[1]);
	cout<<c<<endl;
	system(c);
}
