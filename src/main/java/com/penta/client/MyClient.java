package com.penta.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyClient {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//创建连接到本机的socket
		Socket socket = new Socket("169.254.205.220", 8000);
		//创建线程来不断发送信息
		Thread thread = new Thread(new MyRun(socket));
		thread.start();
		//接收信息
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		while (true) {			
			String str = br.readLine().trim();
			System.out.println(str);
		}
	}
	
}

class MyRun implements Runnable {
	Socket client;
	
	public MyRun(Socket client) {
		this.client = client;
	}
	
	//发送信息
	public void run() {
		try {
			//从控制台录入信息
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
			PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
			//将信息输出到socket
			while (true) {
				String msg = br.readLine().trim();
				//当前用户发送的完整信息
				pw.println("Gaara:"+msg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


