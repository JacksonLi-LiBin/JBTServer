package com.matrix.jbt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.matrix.jbt.entity.CourseJB;
import com.matrix.jbt.entity.Email;
import com.matrix.jbt.entity.User;
import com.matrix.jbt.entity.UserAndCourse;
import com.matrix.jbt.service.EmailService;
import com.matrix.jbt.service.impl.EmailServiceImpl;
import com.matrix.jbt.tool.ReadProperties;

/**
 * Servlet implementation class EmailServlet
 */
public class EmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmailService emailManager = new EmailServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8;pageEncoding=UTF-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		this.doPost(request, response);
	}

	/**
	 * edit email
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8;pageEncoding=UTF-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		Integer type = Integer.parseInt(request.getParameter("type"));
		Integer getEmailCount = Integer.valueOf(ReadProperties.read("jdbc",
				"getEmailCount"));
		switch (type) {
		case 0:
			Integer currentRecommendEmailPage = Integer.parseInt(request
					.getParameter("currentRecommendEmailPage"));
			List<Email> emailList = emailManager.getEmailsByPageNumber(
					(currentRecommendEmailPage - 1) * getEmailCount,
					getEmailCount);
			Integer emailCounts = emailManager.getAllEmailCount();

			Integer sumPages = null;
			if (emailCounts % getEmailCount == 0) {
				sumPages = emailCounts / getEmailCount;
			} else {
				sumPages = emailCounts / getEmailCount + 1;
			}

			out.write("{\"d\":{\"sumPages\":" + sumPages + ",\"getEmail\":"
					+ JSONArray.fromObject(emailList).toString() + "}}");
			break;
		case 1:
			String oldEmail = new String(request.getParameter("oldEmail")
					.getBytes("ISO-8859-1"), "utf-8");
			String newEmail = new String(request.getParameter("newEmail")
					.getBytes("ISO-8859-1"), "utf-8");
			boolean flag = emailManager.updateEmail(oldEmail, newEmail);
			if (flag) {
				out.write("true");
			} else {
				out.write("false");
			}
			break;
		case 2:
			String emailGet = new String(request.getParameter("email")
					.getBytes("ISO-8859-1"), "utf-8");
			Email email = new Email(emailGet);
			boolean flag2 = emailManager.addEmail(email);
			if (flag2) {
				out.write("true");
			} else {
				out.write("false");
			}
			break;
		case 3:
			String emailname = new String(request.getParameter("emailName")
					.getBytes("ISO-8859-1"), "utf-8");
			boolean flag1 = emailManager.deleteEmail(emailname);
			if (flag1) {
				out.write("true");
			} else {
				out.write("false");
			}
			break;
		case 4:
			Integer currentUserEmailPage = Integer.parseInt(request
					.getParameter("currentUserEmailPage"));
			Map<User, List<CourseJB>> map = emailManager.getUserEmailByPage(
					(currentUserEmailPage - 1) * getEmailCount, getEmailCount);

			List<UserAndCourse> userAndCourses = new ArrayList<>();
			UserAndCourse userAndCourse = null;

			if (map != null) {
				for (Entry<User, List<CourseJB>> entry : map.entrySet()) {
					userAndCourse = new UserAndCourse();

					User user = entry.getKey();
					List<CourseJB> courseJBs = entry.getValue();
					List<String> maslulNos = new ArrayList<>();
					for (CourseJB courseJB : courseJBs) {
						String maslulNo = courseJB.getCourseNum() + "/"
								+ courseJB.getCycleNum();

						maslulNos.add(maslulNo);
					}

					userAndCourse.setUserId(user.getUserId());
					userAndCourse.setFirstName(user.getFirstName());
					userAndCourse.setLastName(user.getLastName());
					userAndCourse.setEmail(user.getEmail());
					userAndCourse.setMaslulNos(maslulNos);

					userAndCourses.add(userAndCourse);
				}
			}

			Integer userEmailCounts = emailManager.getUserEmailCount();

			Integer sumUserPages = null;
			if (userEmailCounts % getEmailCount == 0) {
				sumUserPages = userEmailCounts / getEmailCount;
			} else {
				sumUserPages = userEmailCounts / getEmailCount + 1;
			}

			out.write("{\"d\":{\"sumUserPages\":" + sumUserPages
					+ ",\"getUserAndCourses\":"
					+ JSONArray.fromObject(userAndCourses).toString() + "}}");
			break;
		case 5:
			String userId = new String(request.getParameter("userId").getBytes(
					"ISO-8859-1"), "utf-8");
			String userEmail = new String(request.getParameter("userEmail")
					.getBytes("ISO-8859-1"), "utf-8");
			boolean flag3 = emailManager.updateUserEmail(
					Integer.parseInt(userId), userEmail);
			if (flag3) {
				out.write("true");
			} else {
				out.write("false");
			}
			break;
		case 6:
			Integer recommendEmailPage = Integer.parseInt(request
					.getParameter("currentRecommendEmailPage"));
			String character = URLDecoder.decode(
					new String(request.getParameter("character").getBytes(
							"ISO-8859-1"), "utf-8"), "utf-8");

			List<Email> emailListWithFilter = emailManager
					.getEmailsByPageNumberWithFilter((recommendEmailPage - 1)
							* getEmailCount, getEmailCount, character);

			Integer emailCountsFilter = emailManager
					.getEmailCountWithFilter(character);

			Integer sumFilterPages = null;
			if (emailCountsFilter % getEmailCount == 0) {
				sumFilterPages = emailCountsFilter / getEmailCount;
			} else {
				sumFilterPages = emailCountsFilter / getEmailCount + 1;
			}

			out.write("{\"d\":{\"sumFilterPages\":" + sumFilterPages
					+ ",\"getFilterEmail\":"
					+ JSONArray.fromObject(emailListWithFilter).toString()
					+ "}}");
			break;
		case 7:
			Integer userEmailPage = Integer.parseInt(request
					.getParameter("currentUserEmailPage"));
			String characterName = URLDecoder.decode(new String(request
					.getParameter("characterName").getBytes("ISO-8859-1"),
					"utf-8"), "utf-8");

			Map<User, List<CourseJB>> mapFilter = emailManager
					.getUserEmailByPageWithFilter((userEmailPage - 1)
							* getEmailCount, getEmailCount, characterName);

			List<UserAndCourse> userAndCoursesFilter = new ArrayList<>();
			UserAndCourse userAndCourseFilter = null;

			if (mapFilter != null) {
				for (Entry<User, List<CourseJB>> entry : mapFilter.entrySet()) {
					userAndCourseFilter = new UserAndCourse();

					User user = entry.getKey();
					List<CourseJB> courseJBs = entry.getValue();
					List<String> maslulNos = new ArrayList<>();
					for (CourseJB courseJB : courseJBs) {
						String maslulNo = courseJB.getCourseNum() + "/"
								+ courseJB.getCycleNum();

						maslulNos.add(maslulNo);
					}

					userAndCourseFilter.setUserId(user.getUserId());
					userAndCourseFilter.setFirstName(user.getFirstName());
					userAndCourseFilter.setLastName(user.getLastName());
					userAndCourseFilter.setEmail(user.getEmail());
					userAndCourseFilter.setMaslulNos(maslulNos);

					userAndCoursesFilter.add(userAndCourseFilter);
				}
			}

			Integer userFilterEmailCounts = emailManager
					.getUserEmailCountWithFilter(characterName);

			Integer sumFilterUserPages = null;
			if (userFilterEmailCounts % getEmailCount == 0) {
				sumFilterUserPages = userFilterEmailCounts / getEmailCount;
			} else {
				sumFilterUserPages = userFilterEmailCounts / getEmailCount + 1;
			}

			out.write("{\"d\":{\"sumFilterUserPages\":" + sumFilterUserPages
					+ ",\"getFilterUserAndCourses\":"
					+ JSONArray.fromObject(userAndCoursesFilter).toString()
					+ "}}");
			break;
		}

	}
}
