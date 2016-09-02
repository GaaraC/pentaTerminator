package com.penta.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MyServer {

	//存储多个客户端socket
	public static ArrayList<Socket> clients = new ArrayList<Socket>();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//创建serverSocket
		ServerSocket serverSocket = new ServerSocket(8000);

		System.out.println("服务器启动");
		//客户端连接数量
		int num = 1;
		//接收多个客户端连接请求
		while (true) {
			Socket socket = serverSocket.accept();
			System.out.println("客户端已连接"+num);
			//将当前客户端装入clients
			clients.add(socket);
			new MyThread(socket, clients).start();;
			num++;
		}
	}

}

class MyThread extends Thread {
	Socket client;
	ArrayList<Socket> clients;
	BufferedReader br;
	
	public MyThread(Socket client, ArrayList<Socket> clients) throws IOException {
		this.client = client;
		this.clients = clients;
		br = new BufferedReader(new InputStreamReader(client.getInputStream()));
	}
	
	//将客户端发来的信息传递给其他客户端
	public void run() {
		String content = null;
		while (true) {
			try {
				//读取客户端发来的信息
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
