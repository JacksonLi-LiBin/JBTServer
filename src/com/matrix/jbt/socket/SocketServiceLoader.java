package com.matrix.jbt.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.matrix.jbt.tool.ReadProperties;

public class SocketServiceLoader implements ServletContextListener {
	// socket server 线程
	private SocketThread socketThread;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if (null != socketThread && !socketThread.isInterrupted()) {
			socketThread.closeSocketServer();
			socketThread.interrupt();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if (null == socketThread) {
			// 新建线程类
			socketThread = new SocketThread(null);
			// 启动线程
			socketThread.start();
		}
	}

	class SocketThread extends Thread {
		private ServerSocket serverSocket = null;

		public SocketThread(ServerSocket serverScoket) {
			try {
				if (null == serverSocket) {
					this.serverSocket = new ServerSocket(Integer.parseInt(ReadProperties.read("url", "socketPort")));
					System.out.println("server socket start");
				}
			} catch (Exception e) {
				System.out.println("SocketThread create socket failed");
				e.printStackTrace();
			}

		}

		public void run() {
			while (!this.isInterrupted()) {
				try {
					Socket socket = serverSocket.accept();
					if (null != socket && !socket.isClosed()) {
						// 处理接受的数据
						new Thread(new SocketOperate(socket)).start();
					}
					socket.setSoTimeout(0);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public void closeSocketServer() {
			try {
				if (null != serverSocket && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class SocketOperate implements Runnable {
		private Socket socket;

		public SocketOperate(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
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
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {

			}
		}
	}
}
