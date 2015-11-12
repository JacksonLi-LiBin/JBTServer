package com.matrix.jbt.restful;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.matrix.jbt.entity.Inquiry;
import com.matrix.jbt.tool.UserPool;

@Path("/inquiry/{token}")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
public class InquiryRestful {
	/**
	 * create an inquiry
	 * 
	 * @param token
	 * @param fullname
	 * @param userid
	 * @param coursename
	 * @param coursenum
	 * @param cyclenum
	 * @param branch
	 * @param phone
	 * @param email
	 * @param subjectnum
	 * @param details
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/create")
	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public String addInquiry(@PathParam("token") String token,
			@QueryParam("fullname") String fullname,
			@QueryParam("id") int userid,
			@QueryParam("coursename") String coursename,
			@QueryParam("coursenum") String coursenum,
			@QueryParam("cyclenum") String cyclenum,
			@QueryParam("branch") String branch,
			@QueryParam("phone") String phone,
			@QueryParam("email") String email,
			@QueryParam("subjectnum") String subjectnum,
			@QueryParam("details") String details) {
		Boolean flag = false;
		if (UserPool.users.get(token) != null) {
			Inquiry inquiry = new Inquiry(fullname, coursename, coursenum,
					cyclenum, branch, phone, email, subjectnum, details, userid);
			flag = UserPool.userActions.get(token).addInruiry(inquiry);
			UserPool.userActions.get(token).reStartThread(new Date());
		}
		return flag.toString();
	}

}
