package com.matrix.jbt.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.matrix.jbt.entity.AdIntermission;
import com.matrix.jbt.entity.TrainingSyllabus;
import com.matrix.jbt.entity.User;
import com.matrix.jbt.service.AdIntermissionService;
import com.matrix.jbt.service.InfoService;
import com.matrix.jbt.service.RegistionIdService;
import com.matrix.jbt.service.UserService;
import com.matrix.jbt.service.impl.AdIntermissionServiceImpl;
import com.matrix.jbt.service.impl.InfoServiceImpl;
import com.matrix.jbt.service.impl.RegistionIdServiceImpl;
import com.matrix.jbt.service.impl.UserServiceImpl;
import com.matrix.jbt.servlet.ChangePWDAction;
import com.matrix.jbt.servlet.UserAction;
import com.matrix.jbt.tool.JavaMail;
import com.matrix.jbt.tool.ReadProperties;
import com.matrix.jbt.tool.SendPushToGoogle;
import com.matrix.jbt.tool.UserPool;

@Path("/CommonMethods")
public class CommonMethods {
	private UserAction action = new UserAction();
	private UserService userManager = new UserServiceImpl();
	private AdIntermissionService adIntermissionManager = new AdIntermissionServiceImpl();
	private InfoService infoManager = new InfoServiceImpl();
	private ChangePWDAction changePWDAction = new ChangePWDAction();
	private RegistionIdService registionIdService = new RegistionIdServiceImpl();

	/**
	 * get background image
	 * 
	 * @author JacksonLi
	 * @return the path of the image
	 */
	@Path(value = "/getBackgroundImage")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getBackgroundImage() {
		List<AdIntermission> list = adIntermissionManager.getUsableAdIntermission(0, 0);
		if (list.size() > 0) {
			JSONArray array = JSONArray.fromObject(list);
			return array.toString();
		}
		return "false";
	}

	/**
	 * get banner images
	 * 
	 * @author JacksonLi
	 * @return
	 */
	@Path("/getBannersImage")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getBanners() {
		List<AdIntermission> list = adIntermissionManager.getUsableAdIntermission(1, 0);
		if (list.size() > 0) {
			JSONArray array = JSONArray.fromObject(list);
			return array.toString();
		}
		return "false";
	}

	/**
	 * get showtime by ad type
	 * 
	 * @author JacksonLi
	 * @param type
	 * @return
	 */
	@Path("/getShowTime")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getShowTime(@QueryParam("type") String type) {
		return adIntermissionManager.getShowTime(Integer.valueOf(type));
	}

	/**
	 * get user's infos including cyclenumber
	 * 
	 * @author JacksonLi
	 * @param userId
	 * @return
	 */
	@Path("/getUserInfos")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserInfos(@QueryParam("userId") String userId) {
		return infoManager.getInfos(userId);
	}

	@Path("/getUserInDatabase")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserInDatabase(@QueryParam("userId") String userId) {
		User user = userManager.getUserInDatabase(userId);
		if (user != null) {
			return user;
		} else {
			User user2 = new User();
			return user2;
		}
	}

	/**
	 * send email
	 * 
	 * @author JacksonLi
	 * @param userId
	 * @return
	 */
	@Path("/sendEmail")
	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public String sendEmail(@QueryParam("userId") String userId, @QueryParam("email") String email) {
		if (userId != "" && email != "") {
			JavaMail javaMail = new JavaMail();
			String cpwdToken = "";

			ExecutorService pool = Executors.newFixedThreadPool(1);
			if (UserPool.cpwdToken.get(userId) != null) {
				try {
					cpwdToken = UserPool.userPwdActions.get(userId).createToken();
					// boolean flag =
					// javaMail.doSendHtmlEmail("Change Password",
					// "<a href='" + ReadProperties.read("url", "url")
					// + "index_change_password.html?id=" + userId
					// + "&cpwdToken=" + cpwdToken + "'>"
					// + ReadProperties.read("url", "url")
					// + "index_change_password.html?id=" + userId
					// + "&cpwdToken=" + cpwdToken + "</a>", email);

					String[] recipients = { email };
					String flag = null;

					Callable<String> task1 = new MySendEmailCallrable(recipients, "Change Password",
							ReadProperties.read("url", "url") + "index_change_password.html?id=" + userId
									+ "&cpwdToken=" + cpwdToken,
							javaMail);
					Future<?> f1 = pool.submit(task1);
					pool.shutdown();
					while (true) {
						if (pool.isTerminated()) {
							flag = f1.get().toString();
							break;
						}
						TimeUnit.MILLISECONDS.sleep(100);
					}

					if (flag.equals("true")) {
						UserPool.cpwdToken.put(userId, cpwdToken);
						UserPool.userPwdActions.get(userId).reStartThread(new Date());
						return "true";
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "false";
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "false";
				}
			} else {
				try {
					cpwdToken = changePWDAction.createToken();
					// boolean flag =
					// javaMail.doSendHtmlEmail("Change Password",
					// "<a href='" + ReadProperties.read("url", "url")
					// + "index_change_password.html?id=" + userId
					// + "&cpwdToken=" + cpwdToken + "'>"
					// + ReadProperties.read("url", "url")
					// + "index_change_password.html?id=" + userId
					// + "&cpwdToken=" + cpwdToken + "</a>", email);

					String[] recipients = { email };
					String flag = null;

					Callable<String> task1 = new MySendEmailCallrable(recipients, "Change Password",
							ReadProperties.read("url", "url") + "index_change_password.html?id=" + userId
									+ "&cpwdToken=" + cpwdToken,
							javaMail);
					Future<?> f1 = pool.submit(task1);
					pool.shutdown();
					while (true) {
						if (pool.isTerminated()) {
							flag = f1.get().toString();
							break;
						}
						TimeUnit.MILLISECONDS.sleep(100);
					}
					if (flag.equals("true")) {
						UserPool.userPwdActions.put(userId, changePWDAction);
						UserPool.cpwdToken.put(userId, cpwdToken);
						changePWDAction.handleLogoutAbnormal(new Date(), userId);
						return "true";
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "false";
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "false";
				}
			}
		}
		return "false";
	}

	/**
	 * forgot password and update your new password
	 * 
	 * @author JacksonLi
	 * @param userId
	 * @param newPwd
	 * @param pwdToken
	 * @return
	 */
	@Path("/forgetPassword")
	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public String forgetPassword(@QueryParam("userId") String userId, @QueryParam("newPwd") String newPwd,
			@QueryParam("pwdToken") String pwdToken) {
		String to = UserPool.cpwdToken.get(userId);
		if (to != null) {
			if (userId != "" && newPwd != "" && pwdToken != "") {
				if (to.equals(pwdToken)) {
					if (userManager.forgetPassword(userId, newPwd)) {
						UserPool.userPwdActions.get(userId).stopThread();
						UserPool.userPwdActions.remove(userId);
						UserPool.cpwdToken.remove(userId);
						return "true";
					}
				}
				return "false";
			}
		} else {
			return "timeout";
		}
		return "false";
	}

	// get google response value
	private String pushToGoogleResponse = "";

	/**
	 * get maslul no push message and send to google
	 * 
	 * @param pushJsonStr
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/pushMaslulNo")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String pushMaslulNo(final String pushJsonStr) {
		// System.out.println(pushJsonStr);
		pushToGoogleResponse = "";
		new Thread(new Runnable() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject jsonObject = JSONObject.fromObject(pushJsonStr);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				Map<String, List<TrainingSyllabus>> trainingSyllabusMap = new HashMap<>();
				String registionId = "";
				SendPushToGoogle sendPushToGoogle = new SendPushToGoogle();
				for (int i = 0; i < jsonArray.size(); i++) {
					String userId = (String) jsonArray.getJSONObject(i).get("ID");
					Integer cycleNumber = (Integer) jsonArray.getJSONObject(i).get("CycleNumber");
					Integer courseNumber = (Integer) jsonArray.getJSONObject(i).get("CourseNumber");
					String courseName = (String) jsonArray.getJSONObject(i).get("CourseName");
					TrainingSyllabus trainingSyllabus = new TrainingSyllabus(cycleNumber, courseNumber, courseName);

					if (trainingSyllabusMap.get(userId) == null) {
						List<TrainingSyllabus> list = new ArrayList<>();
						list.add(trainingSyllabus);
						trainingSyllabusMap.put(userId, list);
					} else {
						trainingSyllabusMap.get(userId).add(trainingSyllabus);
					}
				}

				Iterator iter = trainingSyllabusMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String userid = (String) entry.getKey();
					// user is online
					if (UserPool.tokens.get(userid) != null) {
						registionId = registionIdService.getRegistionId(Integer.parseInt(userid));
						List<TrainingSyllabus> list = (List<TrainingSyllabus>) entry.getValue();

						JSONArray jsonArray2 = JSONArray.fromObject(list);
						String maslulNos = jsonArray2.toString();

						Map<Integer, String> map = new HashMap<>();
						map.put(Integer.parseInt(userid), registionId);

						pushToGoogleResponse = sendPushToGoogle.sendPushMessage("maslulNo", maslulNos, map);
						System.out.println(pushToGoogleResponse);
					} else {

					}
				}
			}
		}).start();

		return pushToGoogleResponse;
	}

	/**
	 * get job push message and send to google
	 * 
	 * @param pushJsonStr
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/pushJob")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String pushJob(final String pushJsonStr) {
		// System.out.println(pushJsonStr);
		pushToGoogleResponse = "";
		// get user's device registion id
		final Map<Integer, String> map = registionIdService.getAllRegistionId();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject jsonObject = JSONObject.fromObject(pushJsonStr);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				SendPushToGoogle sendPushToGoogle = new SendPushToGoogle();
				for (int i = 0; i < jsonArray.size(); i++) {
					pushToGoogleResponse = sendPushToGoogle.sendPushMessage("job", jsonArray.get(i).toString(), map);
					System.out.println(pushToGoogleResponse);
				}
			}
		}).start();

		return pushToGoogleResponse;
	}

	/**
	 * get class push message and send to google
	 * 
	 * @param pushJsonStr
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/pushClass")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String pushClass(final String pushJsonStr) {
		// System.out.println(pushJsonStr);
		pushToGoogleResponse = "";
		// get user's device registion id
		final Map<Integer, String> map = registionIdService.getAllRegistionId();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject jsonObject = JSONObject.fromObject(pushJsonStr);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				SendPushToGoogle sendPushToGoogle = new SendPushToGoogle();
				for (int i = 0; i < jsonArray.size(); i++) {
					pushToGoogleResponse = sendPushToGoogle.sendPushMessage("class", jsonArray.get(i).toString(), map);
					System.out.println(pushToGoogleResponse);
				}
			}
		}).start();

		return pushToGoogleResponse;
	}

	/**
	 * get grade push message and send to google
	 * 
	 * @param pushJsonStr
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/pushGrade")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String pushGrade(final String pushJsonStr) {
		// System.out.println(pushJsonStr);
		pushToGoogleResponse = "";
		// get user's device registion id
		final Map<Integer, String> map = registionIdService.getAllRegistionId();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject jsonObject = JSONObject.fromObject(pushJsonStr);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				SendPushToGoogle sendPushToGoogle = new SendPushToGoogle();
				for (int i = 0; i < jsonArray.size(); i++) {
					pushToGoogleResponse = sendPushToGoogle.sendPushMessage("grade", jsonArray.get(i).toString(), map);
					System.out.println(pushToGoogleResponse);
				}
			}
		}).start();

		return pushToGoogleResponse;
	}

	/**
	 * get general push message and send it to users
	 * 
	 * @param pushJsonStr
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/pushGeneral")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String pushGeneral(final String pushJsonStr) {
		pushToGoogleResponse = "";
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

			}
		});
		return pushToGoogleResponse;
	}

	/**
	 * judge weather user is logined
	 * 
	 * @param userId
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/judgeWeatherUserLogin")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String judgeWeatherUserLogin(@QueryParam("userId") String userId, @QueryParam("password") String password,
			@QueryParam("token") String token) {

		if (action.finduser(userId, password).equals("true") && UserPool.users.get(token) != null) {
			action.handleLogoutAbnormal(new Date(), token);
			return "usable";
		}
		User user = userManager.getUserInDatabase(userId);
		if (user != null) {
			if (user.getStatus() == 1) {
				return "true";
			} else {
				return "false";
			}
		}
		return "false";
	}

	@Path("/getUserFlag")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserFlag(@QueryParam("userId") String userId) {
		String userFlag = userManager.getUserFlag(userId);
		if (userFlag != null && !userFlag.equals("null")) {
			return userFlag;
		}
		return "false";
	}

	private class MySendEmailCallrable implements Callable<String> {
		private String[] recipients;
		private String subject;
		private String textContent;
		private JavaMail javaMail;

		public MySendEmailCallrable(String[] recipients, String subject, String textContent, JavaMail javaMail) {
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
