package com.qq1010903229.ojserver;
import java.io.*;
import java.net.*;
import java.util.*;
import com.qq1010903229.ojserver.oj;
public class Server{
	public static PrintStream print;
	static{
		try{
			print=new PrintStream(new FileOutputStream(new File("log"+System.currentTimeMillis()+".log"),true));
		}catch(Exception exception){
			exception.printStackTrace(System.out);
		}
	}
	public static void main(String[] arguments){
		{
			print.println("[SERVER LOG]");
			String s=new Date().toString();
			print.print("[DATE]");
			print.println(s);
		}
		ThreadA a=new ThreadA();
		//ThreadB b=new ThreadB();
		//ThreadC c=new ThreadC();
		String e=new String("");
		try{
			while (!e.equalsIgnoreCase("quit")){
				System.out.println("Input 'quit' to terminate the server.");
				print.print("[OUTPUT]");
				print.println("Input 'quit' to terminate the server.");
				byte[] h=new byte[1024];
				int n=System.in.read(h);
				e=new String(h,0,n-2);
				print.print("[INPUT]");
				print.println(e);
			}
		}catch(Exception exception){
			exception.printStackTrace(System.out);
			print.print("[EXCEPTION]");
			exception.printStackTrace(print);
		}
		a.stopThread();
		//b.stopThread();
		//c.stopThread();
	}
}
class ThreadA extends Thread{
	ServerSocket ss;
	Socket s;
	OutputStream o;
	InputStream i;
	final static int PORT=80;
	boolean running;
	public ThreadA(){
		try{
			ss=new ServerSocket(PORT);
		}catch(Exception exception){
			exception.printStackTrace(System.out);
			Server.print.print("[EXCEPTION]");
			exception.printStackTrace(Server.print);
		}
		running=true;
		start();
	}
	public void run(){
		while(running){
			try{
				s=ss.accept();
				i=s.getInputStream();
				o=s.getOutputStream();
				byte[] h=new byte[1048576];
				int n=i.read(h);
				if(n>100000){
					String e2="HTTP/1.1 414 Request Header Too LARGE!\r\nServer:Java/0\r\nContent-Type:text/plain\r\nContent-Length:29\r\n\r\n414 Request Header Too LARGE!";
					byte[] h2=e2.getBytes("UTF-8");
					o.write(h2,0,h2.length);
					o.flush();
					i.close();
					o.close();
					s.close();
					continue;
				}
				String e=new String(h,0,n);
				System.out.println(e);
				Server.print.print("[RESPONSE]");
				Server.print.println(e);
				if(e.startsWith("GET / HTTP")){
					FileInputStream f=new FileInputStream(new File("IndexHeader"));
					byte[] h2=new byte[1000000];
					int len=f.read(h2);
					o.write(h2,0,len);
					o.flush();
					f=new FileInputStream(new File("Index"));
					h2=new byte[1000000];
					len=f.read(h2);
					o.write(h2,0,len);
					o.flush();
				}else if(e.startsWith("GET /favicon.ico HTTP")){
					FileInputStream f=new FileInputStream(new File("FavIconHeader"));
					byte[] h2=new byte[1000000];
					int len=f.read(h2);
					o.write(h2,0,len);
					o.flush();
					f=new FileInputStream(new File("FavIcon"));
					h2=new byte[1000000];
					len=f.read(h2);
					o.write(h2,0,len);
					o.flush();
				}else if(e.startsWith("POST")){
					String e2;
					boolean pqst=true;
					while(pqst){
						try{
							e2=e.split("\r\n\r\n")[1];
							pqst=false;
						}catch(ArrayIndexOutOfBoundsException exception){
							int n1=0;
							n1=i.read(h,n,65536);
							if(n1==-1){
								i.close();
								o.close();
								s.close();
								break;
							}
							if(n1>0)n+=n1;
							e=new String(h,0,n);
						}
						
					}
					if(pqst)continue;
					if(e.split("\r\n\r\n")[0].toLowerCase().indexOf("content-length:")==-1){
						String e5="HTTP/1.1 411 Length Required\r\nServer:Java/0\r\nContent-Type:text/plain\r\nContent-Length:19\r\n\r\n411 Length Required";
						byte[] h5=e5.getBytes("UTF-8");
						o.write(h5,0,h5.length);
						o.flush();
						i.close();
						o.close();
						s.close();
						continue;
					}
					int len1;
					try{
						len1=Integer.parseInt(e.split("\r\n\r\n")[0].toLowerCase().split("content-length:")[1].trim().split("\r\n")[0]);
					}catch(NumberFormatException exception){
						String e5="HTTP/1.1 411 Length Required\r\nServer:Java/0\r\nContent-Type:text/plain\r\nContent-Length:19\r\n\r\n411 Length Required";
						byte[] h5=e5.getBytes("UTF-8");
						o.write(h5,0,h5.length);
						o.flush();
						i.close();
						o.close();
						s.close();
						continue;
					}
					if(len1>10000){
						String e5="HTTP/1.1 413 Request Entity Too Large\r\nServer:Java/0\r\nContent-Type:text/plain\r\nContent-Length:28\r\n\r\n413 Request Entity Too Large";
						byte[] h5=e5.getBytes("UTF-8");
						o.write(h5,0,h5.length);
						o.flush();
						i.close();
						o.close();
						s.close();
						continue;
					}
					e=new String(h,0,n);
					pqst=e.split("\r\n\r\n")[1].length()<len1;
					while(e.split("\r\n\r\n")[1].length()<len1){
						pqst=true;
						int n1=0;
						n1=i.read(h,n,65536);
						if(n1==-1){
							i.close();
							o.close();
							s.close();
							break;
						}
						if(n1>0)n+=n1;
						e=new String(h,0,n);
						pqst=false;
					}
					if(pqst)continue;
					e2=e.split(" ")[1].split("\\?")[0];
					if(e2.equals("/submit")){
						e2=e.split("\r\n\r\n")[1];
						byte[] h2=e2.getBytes("UTF-8");
						byte[] h3=new byte[64];
						FileOutputStream fo=new FileOutputStream(new File("oj\\temp\\submitform"));
						fo.write(h2,0,h2.length);
						fo.flush();
						fo.close();
						oj.submit();
						FileInputStream fi=new FileInputStream(new File("oj\\temp\\submitres"));
						int nn=fi.read(h3);
						e2="HTTP/1.1 200 OK\r\nServer:Java/0\r\nContent-Type:text/plain\r\nContent-Length:";
						h2=e2.getBytes("UTF-8");
						o.write(h2,0,h2.length);
						e2=String.valueOf(nn);
						h2=e2.getBytes("UTF-8");
						o.write(h2,0,h2.length);
						e2="\r\n\r\n";
						h2=e2.getBytes("UTF-8");
						o.write(h2,0,h2.length);
						o.flush();
						o.write(h3,0,nn);
						o.flush();
					}else{
						e=new String(h,0,n);
						e2="HTTP/1.1 405 Method Not Allowed\r\nServer:Java/0\r\nContent-Type:text/plain\r\n\r\n405 Method Not Allowed (POST)\r\n\r\nURL:";
						byte[] h2=e2.getBytes("UTF-8");
						o.write(h2,0,h2.length);
						o.flush();
						e2=e.split(" ")[1].split("\\?")[0];
						h2=e2.getBytes("UTF-8");
						o.write(h2,0,h2.length);
						o.flush();
						e2="\r\n\r\nForm:";
						h2=e2.getBytes("UTF-8");
						o.write(h2,0,h2.length);
						o.flush();
						e2=e.split("\r\n\r\n")[1];
						h2=e2.getBytes("UTF-8");
						o.write(h2,0,h2.length);
						o.flush();
					}
				}else if(e.startsWith("GET")){
					try{
						String[] e1=e.split(" ");
						FileInputStream f=new FileInputStream(new File("data"+e1[1].split("\\?")[0]));
						byte[] h2=new byte[1000000];
						int len=f.read(h2);
						o.write(h2,0,len);
						o.flush();
					}catch(FileNotFoundException exception){
						exception.printStackTrace(System.out);
						Server.print.print("[EXCEPTION404]");
						exception.printStackTrace(Server.print);
						String e2="HTTP/1.1 404 Not Found\r\nServer:Java/0\r\nContent-Type:text/plain\r\nContent-Length:13\r\n\r\n404 Not Found";
						byte[] h2=e2.getBytes("UTF-8");
						o.write(h2,0,h2.length);
						o.flush();
					}
				}else{
					String e2="HTTP/1.1 405 Method Not Allowed\r\nServer:Java/0\r\nContent-Type:text/plain\r\nContent-Length:22\r\n\r\n405 Method Not Allowed";
					byte[] h2=e2.getBytes("UTF-8");
					o.write(h2,0,h2.length);
					o.flush();
				}
			}catch(Exception exception){
				exception.printStackTrace(System.out);
				Server.print.print("[EXCEPTION]");
				exception.printStackTrace(Server.print);
			}finally{
				try{
					i.close();
					o.close();
					s.close();
				}catch(Exception exception){
					exception.printStackTrace(System.out);
					Server.print.print("[EXCEPTION]");
					exception.printStackTrace(Server.print);
				}
			}
		}
	}
	public void stopThread(){
		running = false;
				try{
					ss.close();
				}catch(Exception exception){
					exception.printStackTrace(System.out);
					Server.print.print("[EXCEPTION]");
					exception.printStackTrace(Server.print);
				}
	}
}
		
class ThreadB{}
class ThreadC{}
