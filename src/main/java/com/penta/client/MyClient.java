package com.penta.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyClient {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//连接服务器
		Socket socket = new Socket("localhost", 8000);
		new MyThread(socket).start();
		//获取输入流
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		while (true) {			
			String str = br.readLine().trim();
			System.out.println(str);
		}
	}
	
}

class MyThread extends Thread {
	Socket client;
	
	public MyThread(Socket client) {
		this.client = client;
	}
	
	//客户端发出消息
	public void run() {
		try {
			//捕获控制台输入信息
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	
			PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
			//发送信息给服务器
			while (true) {
				//要发送的信息
				String msg = br.readLine().trim();
				//发送
				pw.println("Gaara:"+msg);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


