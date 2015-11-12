package com.matrix.jbt.restful;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.matrix.jbt.entity.Area;
import com.matrix.jbt.entity.CourseSyllabusJB;
import com.matrix.jbt.entity.Domain;
import com.matrix.jbt.entity.GradesJB;
import com.matrix.jbt.entity.JobJB;
import com.matrix.jbt.entity.RefreshToken;
import com.matrix.jbt.tool.UserPool;

@Path("/User/{token}")
public class UserResful {

	/**
	 * user change password
	 * 
	 * @author JacksonLi
	 * @param token
	 * @param userId
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@Path("/changePassword")
	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public String changePassword(@PathParam("token") String token,
			@QueryParam("userId") String userId,
			@QueryParam("oldPwd") String oldPwd,
			@QueryParam("newPwd") String newPwd) {
		if (UserPool.users.get(token) != null) {
			if (UserPool.userActions.get(token).changePassword(userId, oldPwd,
					newPwd)) {
				UserPool.users.get(token).setPassword(newPwd);
				UserPool.userActions.get(token).reStartThread(new Date());
				return "true";
			}
		} else {
			return "timeout";
		}
		return "false";
	}

	/**
	 * get user info
	 * 
	 * @param token
	 * @return
	 * @author JacksonLi User
	 */
	@Path("/tokenIsUsable")
	@GET
	@Produces(value = MediaType.TEXT_PLAIN)
	public String tokenIsUsable(@PathParam("token") String token) {
		if (UserPool.users.get(token) != null) {
			UserPool.userActions.get(token).reStartThread(new Date());
			return "true";
		} else {
			return "false";
		}
	}

	/**
	 * save or update user's refresh token if user's refresh token in database
	 * is unusable then update it if user's refresh token does not exist in
	 * database then save it(existing email>>1.get the refresh token and save
	 * your own record;new email>>1.save it)
	 * 
	 * @param token
	 * @param refreshToken
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/saveOrUpdateRefreshToken")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String saveOrUpdateRefreshToken(@PathParam("token") String token,
			RefreshToken refreshToken) {
		if (UserPool.users.get(token) != null) {
			UserPool.userActions.get(token).reStartThread(new Date());
			boolean flag = false;
			flag = UserPool.userActions.get(token).saveOrUpdateRefreshToken(
					refreshToken);
			if (flag) {
				return "true";
			}
			return "false";
		} else {
			return "timeout";
		}
	}

	/**
	 * user gets his refresh token if refresh is existing return the value
	 * otherwise return error
	 * 
	 * @param token
	 * @param userid
	 * @param cloudtype
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/getRefreshToken")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getRefreshToken(@PathParam("token") String token,
			@QueryParam("userid") String userid,
			@QueryParam("cloudtype") String cloudtype) {
		if (UserPool.users.get(token) != null) {
			UserPool.userActions.get(token).reStartThread(new Date());
			String refreToken = null;
			refreToken = UserPool.userActions.get(token).getRefreshToken(
					Integer.parseInt(userid), cloudtype);
			if (refreToken.length() > 0) {
				return "{\"refresh_token\":\"" + refreToken + "\"}";
			} else {
				return "{\"refresh_token\":\"none\"}";
			}
		} else {
			return "{\"refresh_token\":\"timeout\"}";
		}
	}

	/**
	 * get all job list
	 * 
	 * @param token
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/getJobList")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getJobList(@PathParam("token") String token) {
		if (UserPool.users.get(token) != null) {
			UserPool.userActions.get(token).reStartThread(new Date());
			List<JobJB> listJob = null;
			listJob = UserPool.userActions.get(token).getJobList();
			if (listJob != null && listJob.size() > 0) {
				StringBuffer stringBuffer = new StringBuffer("{\"d\":\"[");
				for (JobJB job : listJob) {
					stringBuffer.append("{\\\"ID\\\":" + job.getJobId()
							+ ",\\\"Title\\\":\\\"" + job.getTitle()
							+ "\\\",\\\"Area\\\":\\\"" + job.getArea()
							+ "\\\",\\\"Domain\\\":\\\"" + job.getDomain()
							+ "\\\"},");
				}
				stringBuffer = new StringBuffer(
						(stringBuffer.toString()).subSequence(0,
								(stringBuffer.toString()).length() - 1));

				stringBuffer.append("]\"}");
				return stringBuffer.toString();
			} else {
				return "null";
			}
		} else {
			return "timeout";
		}
	}

	/**
	 * get job list with filter
	 * 
	 * @param token
	 * @param filterStr
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/getJobListWithFilter")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String getJobListWithFilter(@PathParam("token") String token,
			String filterStr) {
		if (UserPool.users.get(token) != null) {
			UserPool.userActions.get(token).reStartThread(new Date());

			JSONObject jsonObject = JSONObject.fromObject(filterStr);
			JSONArray domains = jsonObject.getJSONArray("Domain");
			JSONArray areas = jsonObject.getJSONArray("Area");

			String firstDomain = domains.getJSONObject(0).getString("Title");
			String firstArea = areas.getJSONObject(0).getString("Title");

			if (firstDomain.equals("All")) {
				if (firstArea.equals("All")) {
					// all filters are 'all'
					List<JobJB> listJob = null;
					listJob = UserPool.userActions.get(token).getJobList();
					if (listJob != null && listJob.size() > 0) {
						StringBuffer stringBuffer = new StringBuffer(
								"{\"d\":\"[");
						for (JobJB job : listJob) {
							stringBuffer.append("{\\\"ID\\\":" + job.getJobId()
									+ ",\\\"Title\\\":\\\"" + job.getTitle()
									+ "\\\"},");
						}
						stringBuffer = new StringBuffer(
								(stringBuffer.toString()).subSequence(0,
										(stringBuffer.toString()).length() - 1));

						stringBuffer.append("]\"}");
						return stringBuffer.toString();
					} else {
						return "null";
					}
				} else {
					// only domain filter is 'all'
					List<JobJB> listJob = null;
					listJob = UserPool.userActions.get(token)
							.getJobListWithAreaFilter(areas);
					if (listJob != null && listJob.size() > 0) {
						StringBuffer stringBuffer = new StringBuffer(
								"{\"d\":\"[");
						for (JobJB job : listJob) {
							stringBuffer.append("{\\\"ID\\\":" + job.getJobId()
									+ ",\\\"Title\\\":\\\"" + job.getTitle()
									+ "\\\"},");
						}
						stringBuffer = new StringBuffer(
								(stringBuffer.toString()).subSequence(0,
										(stringBuffer.toString()).length() - 1));

						stringBuffer.append("]\"}");
						return stringBuffer.toString();
					} else {
						return "null";
					}
				}
			} else {
				if (firstArea.equals("All")) {
					// only area filter is 'all'
					List<JobJB> listJob = null;
					listJob = UserPool.userActions.get(token)
							.getJobListWithDomainFilter(domains);
					if (listJob != null && listJob.size() > 0) {
						StringBuffer stringBuffer = new StringBuffer(
								"{\"d\":\"[");
						for (JobJB job : listJob) {
							stringBuffer.append("{\\\"ID\\\":" + job.getJobId()
									+ ",\\\"Title\\\":\\\"" + job.getTitle()
									+ "\\\"},");
						}
						stringBuffer = new StringBuffer(
								(stringBuffer.toString()).subSequence(0,
										(stringBuffer.toString()).length() - 1));

						stringBuffer.append("]\"}");
						return stringBuffer.toString();
					} else {
						return "null";
					}
				} else {
					// no filter is 'all'
					List<JobJB> listJob = null;
					listJob = UserPool.userActions.get(token)
							.getJobListWithDomainAndAreaFilter(domains, areas);
					if (listJob != null && listJob.size() > 0) {
						StringBuffer stringBuffer = new StringBuffer(
								"{\"d\":\"[");
						for (JobJB job : listJob) {
							stringBuffer.append("{\\\"ID\\\":" + job.getJobId()
									+ ",\\\"Title\\\":\\\"" + job.getTitle()
									+ "\\\"},");
						}
						stringBuffer = new StringBuffer(
								(stringBuffer.toString()).subSequence(0,
										(stringBuffer.toString()).length() - 1));

						stringBuffer.append("]\"}");
						return stringBuffer.toString();
					} else {
						return "null";
					}
				}
			}
		} else {
			return "timeout";
		}
	}

	/**
	 * get job details
	 * 
	 * @param token
	 * @param jobId
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/getJobDetail")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getJobDetail(@PathParam("token") String token,
			@QueryParam("jobId") String jobId) {
		if (UserPool.users.get(token) != null) {
			UserPool.userActions.get(token).reStartThread(new Date());
			JobJB job = null;
			job = UserPool.userActions.get(token).getJobById(jobId);
			if (job != null) {
				StringBuffer stringBuffer = new StringBuffer(
						"{\"d\":\"{\\\"ID\\\":" + job.getJobId()
								+ ",\\\"Title\\\":\\\"" + job.getTitle()
								+ "\\\",\\\"Company\\\":\\\""
								+ job.getCompany() + "\\\",\\\"Area\\\":\\\""
								+ job.getArea() + "\\\",\\\"Domain\\\":\\\""
								+ job.getDomain()
								+ "\\\",\\\"Experience\\\":\\\""
								+ job.getExperience()
								+ "\\\",\\\"Text\\\":\\\"" + job.getText()
								+ "\\\",\\\"Requirements\\\":\\\""
								+ job.getRequirements()
								+ "\\\",\\\"Email\\\":\\\"" + job.getEmail()
								+ "\\\"}\"}");
				return stringBuffer.toString();
			} else {
				return "null";
			}
		} else {
			return "timeout";
		}
	}

	/**
	 * get domains and areas
	 * 
	 * @param token
	 * @return
	 * @author JacksonLi String
	 */
	@Path("/getDomainAndArea")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getDomainAndArea(@PathParam("token") String token) {
		if (UserPool.users.get(token) != null) {
			UserPool.userActions.get(token).reStartThread(new Date());
			List<Area> areaList = null;
			areaList = UserPool.userActions.get(token).getAreas();
			List<Domain> domainList = null;
			domainList = UserPool.userActions.get(token).getDomains();
			if (areaList != null && domainList != null) {
				StringBuffer stringBuffer = new StringBuffer("{\"Domain\":\"[");
				for (int i = 0; i < domainList.size(); i++) {
					stringBuffer.append("{\\\"Title\\\":\\\""
							+ domainList.get(i).getDomain() + "\\\"},");
				}
				if (domainList.size() > 0) {
					stringBuffer = new StringBuffer(
							(stringBuffer.toString()).subSequence(0,
									(stringBuffer.toString()).length() - 1));
				}

				stringBuffer.append("]\",\"Area\":\"[");

				for (int i = 0; i < areaList.size(); i++) {
					stringBuffer.append("{\\\"Title\\\":\\\""
							+ areaList.get(i).getArea() + "\\\"},");
				}
				if (areaList.size() > 0) {
					stringBuffer = new StringBuffer(
							(stringBuffer.toString()).subSequence(0,
									(stringBuffer.toString()).length() - 1));
				}

				stringBuffer.append("]\"}");

				return stringBuffer.toString();
			} else {
				return "null";
			}
		} else {
			return "timeout";
		}
	}

	@Path("/getGradesByUser")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getGradesByUser(@PathParam("token") String token,
			@QueryParam("userId") String userId,
			@QueryParam("courseNumber") String courseNumber,
			@QueryParam("cycleNumber") String cycleNumber) {
		if (UserPool.users.get(token) != null) {
			UserPool.userActions.get(token).reStartThread(new Date());
			List<GradesJB> gradesJBs = null;
			gradesJBs = UserPool.userActions.get(token).getGradesByUserId(
					userId, courseNumber, cycleNumber);
			if (gradesJBs != null && gradesJBs.size() > 0) {
				StringBuffer buffer = new StringBuffer("{\"d\":\"[");
				for (GradesJB gradesJB : gradesJBs) {
					buffer.append("{\\\"ID\\\":" + gradesJB.getId()
							+ ",\\\"StudentID\\\":\\\""
							+ gradesJB.getStudentId()
							+ "\\\",\\\"CourseNumber\\\":"
							+ gradesJB.getCourseNumber()
							+ ",\\\"CycleNumber\\\":"
							+ gradesJB.getCycleNumber()
							+ ",\\\"Subject\\\":\\\"" + gradesJB.getSubject()
							+ "\\\",\\\"Points\\\":" + gradesJB.getPoints()
							+ "},");
				}
				buffer = new StringBuffer((new String(buffer)).subSequence(0,
						(new String(buffer)).length() - 1));
				buffer.append("]\"}");
				return buffer.toString();
			} else {
				return "null";
			}
		} else {
			return "timeout";
		}
	}

	@Path("/getCourseSyllabusByUser")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getCourseSyllabusByUser(@PathParam("token") String token,
			@QueryParam("userId") String userId) {
		if (UserPool.users.get(token) != null) {
			UserPool.userActions.get(token).reStartThread(new Date());
			List<CourseSyllabusJB> courseSyllabusList = UserPool.userActions
					.get(token).getCourseSyllabusByUserId(userId);
			if (courseSyllabusList != null && courseSyllabusList.size() > 0) {
				StringBuffer buffer = new StringBuffer("{\"d\":\"[");
				for (CourseSyllabusJB courseSyllabusJB : courseSyllabusList) {
					buffer.append("{\\\"MeetDate\\\":\\\""
							+ courseSyllabusJB.getMeetDate()
							+ "\\\",\\\"MeetStart\\\":\\\""
							+ courseSyllabusJB.getMeetStart()
							+ "\\\",\\\"MeetEnd\\\":\\\""
							+ courseSyllabusJB.getMeetEnd()
							+ "\\\",\\\"MeetNum\\\":"
							+ courseSyllabusJB.getMeetNum()
							+ ",\\\"MeetBranch\\\":"
							+ courseSyllabusJB.getMeetBranch()
							+ ",\\\"MeetBuildingNum\\\":"
							+ courseSyllabusJB.getMeetBuildingNum()
							+ ",\\\"MeetBuildingName\\\":\\\""
							+ courseSyllabusJB.getMeetBuildingName()
							+ "\\\",\\\"MeetClassNum\\\":"
							+ courseSyllabusJB.getMeetClassNum()
							+ ",\\\"MeetClassName\\\":\\\""
							+ courseSyllabusJB.getMeetClassName()
							+ "\\\",\\\"MeetStatus\\\":\\\""
							+ courseSyllabusJB.getMeetStatus()
							+ "\\\",\\\"MeetTopic\\\":\\\""
							+ courseSyllabusJB.getMeetTopic()
							+ "\\\",\\\"MeetTeacher\\\":\\\""
							+ courseSyllabusJB.getMeetTeacher() + "\\\"},");
				}
				buffer = new StringBuffer((new String(buffer)).subSequence(0,
						(new String(buffer)).length() - 1));
				buffer.append("]\"}");
				return buffer.toString();
			} else {
				return "null";
			}
		} else {
			return "timeout";
		}
	}
}
