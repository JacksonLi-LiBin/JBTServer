package com.matrix.jbt.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import com.matrix.jbt.init.InitServlet;
import com.matrix.jbt.tool.ChatUserTool;
/**
 * 
 * @author JacksonLi
 * @date 2014/3/10
 */
@SuppressWarnings("deprecation")
public class MyMessageInbound extends MessageInbound {
	private String token;

	public MyMessageInbound(String token) {
		super();
		this.token = token;
	}

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onTextMessage(CharBuffer msg) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("message:" + msg.toString());
		for (MessageInbound messageInbound : InitServlet.getSocketList()) {
			if (messageInbound != this) {
				CharBuffer buffer = CharBuffer.wrap(msg);
				WsOutbound outbound = messageInbound.getWsOutbound();
				outbound.writeTextMessage(buffer);
				outbound.flush();
			}
		}

		System.out.println("chat user counts:" + ChatUserTool.chatUser.size());
	}

	@Override
	protected void onClose(int status) {
		// TODO Auto-generated method stub
		System.out.println("close a websocket");

		ChatUserTool.chatUser.remove(token);

		InitServlet.getSocketList().remove(this);
		super.onClose(status);
	}

	@Override
	protected void onOpen(WsOutbound outbound) {
		// TODO Auto-generated method stub
		super.onOpen(outbound);

		ChatUserTool.chatUser.put(token, token);

		InitServlet.getSocketList().add(this);
	}
}
