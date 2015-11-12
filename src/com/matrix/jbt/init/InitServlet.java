package com.matrix.jbt.init;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.catalina.websocket.MessageInbound;

/**
 * start web service when server start up
 * @author JacksonLi
 * Servlet implementation class InitServlet
 */
@SuppressWarnings("deprecation")
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static List<MessageInbound> socketList;

	/**
	 * Default constructor.
	 */
	public InitServlet() {
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		InitServlet.socketList = new ArrayList<MessageInbound>();
		super.init(config);
		System.out.println("Server start===============");
	}

	public static List<MessageInbound> getSocketList() {
		return InitServlet.socketList;
	}
}
