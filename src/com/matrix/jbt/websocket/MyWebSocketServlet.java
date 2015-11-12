package com.matrix.jbt.websocket;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
/**
 * @author JacksonLi
 * Servlet implementation class MyWebSocketServlet
 */
@SuppressWarnings("deprecation")
public class MyWebSocketServlet extends WebSocketServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected StreamInbound createWebSocketInbound(String arg0,
			HttpServletRequest arg1) {
		// TODO Auto-generated method stub
		System.out.println("create a new websocket");
		String token = arg1.getParameter("token");
		System.out.println("token:" + token);

		return new MyMessageInbound(token);
	}

}
