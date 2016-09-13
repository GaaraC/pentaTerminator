package com.penta.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MyServer {

	//用于保存当前连接的用户 
	public static ArrayList<Socket> clients = new ArrayList<Socket>();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//创建一个serverSocket，用于监听客户端Socket的连接请求 
		ServerSocket serverSocket = new ServerSocket(8000);

		System.out.println("server run");
		//计数连接客户端数量
		int num = 1;
		//循环不断接收不同客户端的请求
		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("客户端"+num);
			System.out.println(socket.getInetAddress());
			//将连接的客户端加入list
			clients.add(socket);
			Thread thread = new Thread(new MyCenter(socket, clients));
			thread.start();
			num++;
		}
	}

}

class MyCenter implements Runnable {
	Socket client;
	ArrayList<Socket> clients;
	BufferedReader br;
	
	public MyCenter(Socket client, ArrayList<Socket> clients) throws IOException {
		this.client = client;
		this.clients = clients;
		br = new BufferedReader(new InputStreamReader(client.getInputStream()));
	}
	
	//把接收到的消息发送给其他各客户端
	public void run() {
		String content = null;
		while (true) {
			try {
				//从客户端读取信息
				content = br.readLine().trim();
				if(content != null)
				{
					for(int i=0; i<clients.size(); i++)
					{
						//发送给其他客户端
						if(clients.get(i) != client)
						{
							PrintWriter pw = new PrintWriter(clients.get(i).getOutputStream(), true);
							pw.println(content);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
