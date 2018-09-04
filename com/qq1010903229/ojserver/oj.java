package com.qq1010903229.ojserver;
import java.io.IOException;
import java.util.*;
public class oj{
	public static void submit() throws IOException{
		Process p=new ProcessBuilder("oj\\submit.exe").start();
		try{p.waitFor();}catch (InterruptedException e){}
	}
}