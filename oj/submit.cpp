#include <iostream>
#include <fstream>
#include <cstring>
#include <cstdlib>
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
	ofstream lang("oj\\temp\\submitlang");
	ofstream pid("oj\\temp\\submitpid");
	ofstream res("oj\\temp\\submitres");
	form>>formdata;
	char* t=strstr(formdata,"pid=");
	if(t==NULL){
		return 1;
	}
	t+=4;
	while(*t>='0'&&*t<='9'){
		pid<<*t;
		t++;
	}
	t=strstr(formdata,"lang=");
	if(t==NULL){
		return 1;
	}
	t+=5;
	while(*t>='0'&&*t<='9'){
		lang<<*t;
		t++;
	}
	t=strstr(formdata,"code=");
	if(t==NULL){
		return 1;
	}
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
	code.close();
	lang.close();
	pid.close();
	int aaa=system("oj\\submit_1.cmd");
	if(aaa==2)res<<"Language Error";
	else if(aaa==3)res<<"Compile Error";
	else if(aaa==4)res<<"PID Error";
	else if(aaa==0){
		res.close();
		system("copy oj\\temp\\submitres1 oj\\temp\\submitres");
	}
	else res<<"Unknown Error";
}
