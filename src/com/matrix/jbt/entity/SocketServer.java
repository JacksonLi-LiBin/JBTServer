package com.matrix.jbt.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	private ServerSocket serverSocket;

	public SocketServer(int port) {
		super();
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {

		}
	}

	public void startServerSocket() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				new Thread(new ClientThread(socket)).start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class ClientThread implements Runnable {
		private Socket socket;

		public ClientThread(Socket socket) {
			super();
			this.socket = socket;
		}

		@Override
		public void run() {
			BufferedReader in;
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				while (!socket.isClosed()) {
					String str;
					str = in.readLine();
					out.println("Hello!world!! " + str);
					out.flush();
					if (str == null || str.equals("end"))
						break;
					System.out.println(str);
				}
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
