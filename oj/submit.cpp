#include <iostream>
#include <fstream>
#include <cstring>
using namespace std;
char formdata[20000];
int unescape1(char* t){
	if(*t != '%')return -1;
	int a1=*(t+1);
	int a2=*(t+2);
	if(a1>='a')a1-=32;
	if(a2>='a')a2-=32;
	if(a1>='A')a1-=('A'-10);
	if(a2>='A')a2-=('A'-10);
	if(a1>='0')a1-=('0');
	if(a2>='0')a2-=('0');
	a1*=16;
	a1+=a2;
	return a1;
}
int main(){
	ifstream form("oj\\temp\\submitform");
	ofstream code("oj\\temp\\submitcode");
	ofstream pid("oj\\temp\\submitpid");
	form>>formdata;
	char* t=strstr(formdata,"pid=");
	t+=4;
	while(*t>='0'&&*t<='9'){
		pid<<*t;
		t++;
	}
	t=strstr(formdata,"code=");
	t+=5;
	while(*t!='\0'){
		if(*t=='%'){
			int q=unescape1(t);
			if(q!=13)code<<(char)q;
			t++;
			t++;
		}else if(*t=='+'){
			code<<' ';
		}else{
			code<<*t;
		}
		t++;
	}
}