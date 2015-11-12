package com.matrix.jbt.restful;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.matrix.jbt.tool.AdminPool;

@Path("/Admin/{token}")
public class AdminRestful {

	@Path("/getTimeAndDate")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getTimeAndData(@PathParam("token") String token) {
		if (AdminPool.adminActions.get(token) != null) {
			String timeOut = AdminPool.adminActions.get(token).getTimeOut();
			String cpwdTimeOut = AdminPool.adminActions.get(token)
					.getChangePasswordTimeOut();
			String firstTimeGetData = AdminPool.adminActions.get(token)
					.getFirstTimeGetData();
			String secondTimeGetData = AdminPool.adminActions.get(token)
					.getSecondTimeGetData();
			AdminPool.adminActions.get(token).reStartThread(new Date());
			if (timeOut != null && cpwdTimeOut != null
					&& firstTimeGetData != null && secondTimeGetData != null) {
				return "{\"timeOut\":\"" + timeOut + "\",\"cpwdTimeOut\":\""
						+ cpwdTimeOut + "\",\"firstTimeGetData\":\""
						+ firstTimeGetData + "\",\"secondTimeGetData\":\""
						+ secondTimeGetData + "\"}";
			}
		}
		return "null";
	}
}
