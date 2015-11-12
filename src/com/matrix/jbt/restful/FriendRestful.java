package com.matrix.jbt.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.matrix.jbt.entity.Email;
import com.matrix.jbt.entity.Friend;
import com.matrix.jbt.tool.JavaMail;
import com.matrix.jbt.tool.UserPool;

@Path("/Friend/{token}")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
public class FriendRestful {
	/**
	 * add friend into database
	 * 
	 * @param token
	 * @param friend
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/add")
	@POST
	@Produces(value = MediaType.TEXT_PLAIN)
	@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String addFriend(@PathParam("token") String token, Friend friend) {
		Boolean flag = false;
		Boolean flag2 = false;
		System.out.println("friend-----" + friend.toString());
		if (UserPool.users.get(token) != null) {
			try {
				int friendid = UserPool.userActions.get(token).addFriend(friend);
				ArrayList<Email> emailList = (ArrayList<Email>) UserPool.userActions.get(token).getAll();
				JavaMail se = new JavaMail();

				ExecutorService pool = Executors.newFixedThreadPool(1);

				if (emailList != null) {
					String[] recipients = new String[emailList.size()];
					for (int i = 0; i < emailList.size(); i++) {
						// Boolean boo = se
						// .doSendHtmlEmail(
						// "recommend a friend",
						// "Name:"
						// + friend.getFirstName()
						// + "<br/>Phone:"
						// + friend.getPhone()
						// + (friend.getEmail().equals(
						// "NONAME@NONAME.COM") ? ""
						// : "<br/>Email:"
						// + friend.getEmail())
						// + (friend.getInterest().equals(
						// "NONE") ? ""
						// : "<br/>Interest:"
						// + friend.getInterest())
						// + "<br/>Time:"
						// + friend.getDataAndTimeOfLead(),
						// email.getEmail());

						recipients[i] = emailList.get(i).getEmail();

					}

					String boo = null;

					Callable<String> task1 = new MyCallableRecommendFriend(recipients, "Recommend A Friend",
							"Name:" + friend.getFirstName() + "\nPhone:" + friend.getPhone()
									+ (friend.getEmail().equals("NONAME@NONAME.COM") ? ""
											: "\nEmail:" + friend.getEmail())
									+ (friend.getInterest().equals("NONE") ? "" : "\nInterest:" + friend.getInterest())
									+ "\nTime:" + friend.getDataAndTimeOfLead(),
							se);
					Future<?> f1 = pool.submit(task1);
					pool.shutdown();
					while (true) {
						if (pool.isTerminated()) {
							boo = f1.get().toString();
							break;
						}
						TimeUnit.MILLISECONDS.sleep(100);
					}
					if (flag2 == false) {
						if (boo.equals("true")) {
							flag = UserPool.userActions.get(token).udpateFriend(friendid);
							flag2 = true;
							UserPool.userActions.get(token).reStartThread(new Date());
						}
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return flag.toString();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return flag.toString();
			}
		}
		return flag.toString();
	}

	private class MyCallableRecommendFriend implements Callable<String> {
		private String[] recipients;
		private String subject;
		private String textContent;
		private JavaMail javaMail;

		public MyCallableRecommendFriend(String[] recipients, String subject, String textContent, JavaMail javaMail) {
			super();
			this.recipients = recipients;
			this.subject = subject;
			this.textContent = textContent;
			this.javaMail = javaMail;
		}

		@Override
		public String call() throws Exception {
			// TODO Auto-generated method stub
			Boolean flag = javaMail.doSendEmailByGmail(recipients, subject, textContent);
			if (flag) {
				return "true";
			} else {
				return "false";
			}
		}
	}
}
