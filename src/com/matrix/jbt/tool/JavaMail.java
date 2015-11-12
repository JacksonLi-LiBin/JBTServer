package com.matrix.jbt.tool;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMail {
	// set server
	private static String KEY_SMTP = ReadProperties.read("email", "KEY_SMTP");
	private static String VALUE_SMTP = ReadProperties.read("email",
			"VALUE_SMTP");
	// Server validation
	private static String KEY_PROPS_AUTH = ReadProperties.read("email",
			"KEY_PROPS_AUTH");
	private static boolean VALUE_PROPS_AUTH = Boolean.getBoolean(ReadProperties
			.read("email", "VALUE_PROPS_AUTH"));
	private static String KEY_PROPS_STARTTLS = ReadProperties.read("email",
			"KEY_PROPS_STARTTLS");
	private static String VALUE_PROPS_STARTTLS = ReadProperties.read("email",
			"VALUE_PROPS_STARTTLS");
	private static String KEY_PROPS_SSL = ReadProperties.read("email",
			"KEY_PROPS_SSL");
	private static String VALUE_PROPS_SSL = ReadProperties.read("email",
			"VALUE_PROPS_SSL");
	private static String KEY_PROPS_FALLBACK = ReadProperties.read("email",
			"KEY_PROPS_FALLBACK");
	private static String VALUE_PROPS_FALLBACK = ReadProperties.read("email",
			"VALUE_PROPS_FALLBACK");

	private static String KEY_PROPS_TIMEOUT = ReadProperties.read("email",
			"KEY_PROPS_TIMEOUT");
	private static String VALUE_PROPS_TIMEOUT = ReadProperties.read("email",
			"VALUE_PROPS_TIMEOUT");

	private static String KEY_PROPS_PORT = ReadProperties.read("email",
			"KEY_PROPS_PORT");
	private static String KEY_PROPS_SOCKET_PORT = ReadProperties.read("email",
			"KEY_PROPS_SOCKET_PORT");
	private static String VALUE_PROPS_PORT = ReadProperties.read("email",
			"VALUE_PROPS_PORT");

	private static String KEY_PROPS_USER = ReadProperties.read("email",
			"KEY_PROPS_USER");
	private static String KEY_PROPS_PASSWORD = ReadProperties.read("email",
			"KEY_PROPS_PASSWORD");
	// sender's name and password
	private String SEND_USER = ReadProperties.read("email", "SEND_USER");
	private String SEND_UNAME = ReadProperties.read("email", "SEND_UNAME");
	private String SEND_PWD = ReadProperties.read("email", "SEND_PWD");
	private String NICK_NAME = ReadProperties.read("email", "NICK_NAME");

	/**
	 * send email 126
	 * 
	 * @param email_head
	 * @param email_content
	 * @param email_reciver
	 * @return
	 * @author JacksonLi boolean
	 */
	// public boolean doSendHtmlEmail(String email_head, String email_content,
	// String email_reciver) {
	// try {
	// Properties props = System.getProperties();
	// props.setProperty(KEY_SMTP, VALUE_SMTP);
	// props.put(KEY_PROPS_AUTH, VALUE_PROPS_AUTH);
	// Session s = Session.getInstance(props);
	// /* s.setDebug(true);There is debug information after starting */
	// Message message = new MimeMessage(s);
	// InternetAddress address = new InternetAddress(SEND_USER, NICK_NAME);
	//
	// message.setFrom(address);
	// // receiver
	// InternetAddress to = new InternetAddress(email_reciver);
	// message.setRecipient(Message.RecipientType.TO, to);
	// // subject of the email
	// message.setSubject(email_head);
	// String content = email_content.toString();
	// // Email content, also can make plain text "text/plain"
	// message.setContent(content, "text/html;charset=utf-8");
	// message.saveChanges();
	// Transport transport = s.getTransport("smtp");
	// // SMTP authentication,user name and password of the email you use
	// // to send email
	// transport.connect(VALUE_SMTP, SEND_UNAME, SEND_PWD);
	// // send
	// transport.sendMessage(message, message.getAllRecipients());
	// transport.close();
	// return true;
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return false;
	// } catch (MessagingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return false;
	// }
	// }

	public boolean doSendEmailByGmail(String[] recipients, String sendSubject,
			String sendText) {
		try {
			Properties props = System.getProperties();
			props.put(KEY_PROPS_STARTTLS, VALUE_PROPS_STARTTLS);
			props.put(KEY_SMTP, VALUE_SMTP);
			props.put(KEY_PROPS_USER, SEND_USER);
			props.put(KEY_PROPS_PASSWORD, SEND_PWD);
			props.setProperty(KEY_PROPS_SSL, VALUE_PROPS_SSL);
			props.setProperty(KEY_PROPS_FALLBACK, VALUE_PROPS_FALLBACK);
			props.setProperty(KEY_PROPS_TIMEOUT, VALUE_PROPS_TIMEOUT);
			props.setProperty(KEY_PROPS_PORT, VALUE_PROPS_PORT);
			props.setProperty(KEY_PROPS_SOCKET_PORT, VALUE_PROPS_PORT);
			props.put(KEY_PROPS_AUTH, VALUE_PROPS_AUTH);
			String[] to = recipients;// 收件人
			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SEND_UNAME, NICK_NAME));
			InternetAddress[] toAddress = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}
			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}
			message.setSubject(sendSubject);
			message.setText(sendText);

			Transport transport = session.getTransport("smtp");
			transport.connect(VALUE_SMTP, SEND_USER, SEND_PWD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("send email success");

			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("send email fail");

			return false;
		}
	}
}