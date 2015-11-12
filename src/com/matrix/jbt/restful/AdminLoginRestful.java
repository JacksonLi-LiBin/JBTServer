package com.matrix.jbt.restful;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.matrix.jbt.servlet.AdminAction;
import com.matrix.jbt.tool.AdminPool;

@Path("/AdminLogin")
public class AdminLoginRestful {
	private AdminAction adminAction = new AdminAction();

	/**
	 * administrator login
	 * 
	 * @author JacksonLi
	 * @param adminName
	 * @param password
	 * @return
	 */
	@Path("/login")
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	public String login(@QueryParam("username") String adminName,
			@QueryParam("password") String password,
			@QueryParam("adminToken") String adminToken) {
		if (adminToken.equals("null")) {
			if (AdminPool.adminMap.get(adminName) != null) {
				return "already";
			} else {
				String token = adminAction.login(adminName, password);
				if (token.length() > 0) {
					AdminPool.adminActions.put(token, adminAction);
					AdminPool.adminMap.put(adminName, adminName);

					adminAction.handleLogoutAbnormal(new Date(), adminName,
							token);
					return token;
				}
			}
		} else {
			if (AdminPool.adminActions.get(adminToken) != null) {
				String token = adminAction.login(adminName, password);
				if (token.length() > 0) {
					AdminPool.adminActions.get(adminToken)
							.handleLogoutAbnormal(new Date(), adminName,
									adminToken);
					return adminToken;
				}
			} else {
				if (AdminPool.adminMap.get(adminName) != null) {
					return "already";
				} else {
					String token = adminAction.login(adminName, password);
					if (token.length() > 0) {
						AdminPool.adminActions.put(token, adminAction);
						AdminPool.adminMap.put(adminName, adminName);

						adminAction.handleLogoutAbnormal(new Date(), adminName,
								token);
						return token;
					}
				}
			}
		}
		return "false";
	}

	/**
	 * judge weather administrator's token is usable
	 * 
	 * @param adminToken
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/adminTokenUsable")
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	public String judgeTokenUsable(@QueryParam("adminToken") String adminToken) {
		if (AdminPool.adminActions.get(adminToken) != null) {
			AdminPool.adminActions.get(adminToken).reStartThread(new Date());
			return "true";
		}
		return "false";
	}

	/**
	 * administrator logout
	 * 
	 * @param adminToken
	 * @param adminName
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/logout")
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	public String adminLogout(@QueryParam("adminToken") String adminToken,
			@QueryParam("adminName") String adminName) {
		if (AdminPool.adminActions.get(adminToken) != null) {
			AdminPool.adminActions.get(adminToken).stopThread();
			AdminPool.adminMap.remove(adminName);
			AdminPool.adminActions.remove(adminToken);
			return "true";
		}
		return "false";
	}
}
