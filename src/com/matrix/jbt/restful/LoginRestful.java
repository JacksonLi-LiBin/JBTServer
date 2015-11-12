package com.matrix.jbt.restful;

import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.matrix.jbt.entity.User;
import com.matrix.jbt.servlet.UserAction;
import com.matrix.jbt.tool.UserPool;

@Path("/UserLogin")
public class LoginRestful {
	private UserAction action = new UserAction();

	/**
	 * 
	 * @author JacksonLi
	 * @param userId
	 * @param userName
	 * @param password
	 * @param email
	 * @return a token returned to the user
	 */
	@Path("/login")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@QueryParam("userId") String userId,
			@QueryParam("password") String password,
			@QueryParam("registionId") String registionId,
			@QueryParam("passToken") String passToken,
			@QueryParam("flag") String userFlag) {

		if (UserPool.timeOut.equals("0")) {
			UserPool.timeOut = action.getTimeOut();
		}

		User user = new User(userId, "", "", password, "",
				Integer.parseInt(userFlag), 1);
		String token = "";

		// 0 new user 1 old user
		token = action.userLogin(user, registionId);

		if (token != "" && !token.equals("")) {
			if (userFlag.equals("0")) {
				if (UserPool.users.get(passToken) != null) {
					UserPool.userActions.get(passToken).stopThread();
					UserPool.userActions.remove(passToken);
					UserPool.tokens.remove(UserPool.users.get(passToken)
							.getUserId() + "");
					UserPool.users.remove(passToken);
				}
				UserPool.userActions.put(token, action);
				UserPool.users.put(token, user);
				UserPool.tokens.put(userId, token);
				action.handleLogoutAbnormal(new Date(), token);
				return token + "1";
			} else if (userFlag.equals("1")) {
				if (UserPool.users.get(passToken) != null) {
					UserPool.userActions.get(passToken).stopThread();
					UserPool.userActions.remove(passToken);
					UserPool.tokens.remove(UserPool.users.get(passToken)
							.getUserId() + "");
					UserPool.users.remove(passToken);
				}
				UserPool.userActions.put(token, action);
				UserPool.users.put(token, user);
				UserPool.tokens.put(userId, token);
				// calculate request time
				action.handleLogoutAbnormal(new Date(), token);
				return token + "0";
			}
		}
		return "";
	}

	/**
	 * user logout
	 * 
	 * @param token
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/logout/{token}")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String logout(@PathParam("token") String token) {
		try {
			if (UserPool.users.get(token) != null) {
				String userId = UserPool.users.get(token).getUserId();
				boolean flag = UserPool.userActions.get(token)
						.updateUserStatus(userId, 0);
				UserPool.userActions.get(token).stopThread();
				UserPool.userActions.remove(token);
				UserPool.tokens.remove(UserPool.users.get(token).getUserId()
						+ "");
				UserPool.users.remove(token);
				if (flag) {
					return "true";
				}
				return "false";
			} else {
				return "timeout";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "false";
		}
	}
}
