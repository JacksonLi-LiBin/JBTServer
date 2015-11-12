package com.matrix.jbt.restful;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.matrix.jbt.tool.UserPool;

@Path("/Job/{token}")
public class JobRestful {
	/**
	 * get job list
	 * 
	 * @param token
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/jobListRequest")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String jobListRequest(@PathParam("token") String token) {
		if (UserPool.users.get(token) != null) {
			UserPool.userActions.get(token).reStartThread(new Date());
			return "true";
		} else {
			return "timeout";
		}
	}
}
