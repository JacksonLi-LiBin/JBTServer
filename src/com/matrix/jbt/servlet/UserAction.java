package com.matrix.jbt.servlet;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONArray;

import com.matrix.jbt.entity.Area;
import com.matrix.jbt.entity.CourseSyllabusJB;
import com.matrix.jbt.entity.Domain;
import com.matrix.jbt.entity.Email;
import com.matrix.jbt.entity.Friend;
import com.matrix.jbt.entity.GradesJB;
import com.matrix.jbt.entity.Inquiry;
import com.matrix.jbt.entity.JobJB;
import com.matrix.jbt.entity.RefreshToken;
import com.matrix.jbt.entity.User;
import com.matrix.jbt.service.AreaService;
import com.matrix.jbt.service.CourseSyllabusService;
import com.matrix.jbt.service.DomainService;
import com.matrix.jbt.service.EmailService;
import com.matrix.jbt.service.GradesService;
import com.matrix.jbt.service.JobService;
import com.matrix.jbt.service.RefreshTokenService;
import com.matrix.jbt.service.TimeOutService;
import com.matrix.jbt.service.impl.AreaServiceImpl;
import com.matrix.jbt.service.impl.CourseSyllabusServiceImpl;
import com.matrix.jbt.service.impl.DomainServiceImpl;
import com.matrix.jbt.service.impl.EmailServiceImpl;
import com.matrix.jbt.service.impl.FriendServiceImpl;
import com.matrix.jbt.service.impl.GradesServiceImpl;
import com.matrix.jbt.service.impl.InquiryServiceImpl;
import com.matrix.jbt.service.impl.JobServiceImpl;
import com.matrix.jbt.service.impl.RefreshTokenServiceImpl;
import com.matrix.jbt.service.impl.TimeOutServiceImpl;
import com.matrix.jbt.service.impl.UserServiceImpl;
import com.matrix.jbt.tool.UserPool;

/**
 * user action
 * 
 * @author JacksonLi
 * @date 2014/3/13
 */
public class UserAction {
	private UserServiceImpl managerImpl = new UserServiceImpl();
	private FriendServiceImpl friendManagerImpl = new FriendServiceImpl();
	private EmailService emailManager = new EmailServiceImpl();
	private InquiryServiceImpl inquiryManagerImpl = new InquiryServiceImpl();
	private TimeOutService timeOutManager = new TimeOutServiceImpl();
	private RefreshTokenService refreshTokenManager = new RefreshTokenServiceImpl();
	private JobService jobService = new JobServiceImpl();
	private AreaService areaService = new AreaServiceImpl();
	private DomainService domainService = new DomainServiceImpl();
	private GradesService gradesService = new GradesServiceImpl();
	private CourseSyllabusService courseSyllabusService = new CourseSyllabusServiceImpl();

	private Thread thread = null;
	private boolean flag = false;
	private Date operateT;

	public String createToken() {
		return UUID.randomUUID().toString();
	}

	public boolean changePassword(String userId, String oldPwd, String newPwd) {
		return managerImpl.changePassword(userId, oldPwd, newPwd);
	}

	public List<Email> getAll() {
		return emailManager.getAll();
	}

	public int addFriend(Friend friend) {
		return friendManagerImpl.addFriend(friend);
	}

	public boolean udpateFriend(int friendid) {
		return friendManagerImpl.udpateFriend(friendid);
	}

	public boolean addInruiry(Inquiry inquiry) {
		return inquiryManagerImpl.addInruiry(inquiry);
	}

	public String getTimeOut() {
		return timeOutManager.getTimeOut();
	}

	public void handleLogoutAbnormal(Date operationTime, String tok) {
		flag = true;
		operateT = operationTime;
		thread = new Thread(new MyRunnable(tok));
		thread.start();
	}

	public void stopThread() {
		flag = false;
		thread = null;
		System.gc();
	}

	public void reStartThread(Date time) {
		operateT = time;
	}

	class MyRunnable implements Runnable {
		private String to;

		public MyRunnable(String to) {
			super();
			this.to = to;
		}

		@Override
		public void run() {
			while (flag) {
				Long waitTime = new Date().getTime() - operateT.getTime();
				if (waitTime > Integer.parseInt(UserPool.timeOut)) {
					System.out.println(to + " operate time out");
					String userId = UserPool.users.get(to).getUserId();
					managerImpl.updateStatus(userId, 0);
					UserPool.userActions.remove(to);
					UserPool.tokens.remove(UserPool.users.get(to).getUserId()
							+ "");
					UserPool.users.remove(to);
					stopThread();
					break;
				} else {
					String leftTime = ((Integer.valueOf(UserPool.timeOut) - waitTime) / 1000)
							+ "";
					UserPool.reLoginLeftTime.put(to, Integer.parseInt(leftTime)
							+ "");
				}
			}
		}
	}

	/**
	 * delete user's refresh token according to his userid
	 * 
	 * @param userid
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteRefreshToken(Integer userid) {
		// TODO Auto-generated method stub
		return refreshTokenManager.deleteRefreshToken(userid);
	}

	/**
	 * update user's refresh token where email equals to the passing value
	 * 
	 * @param refreshToken
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean saveOrUpdateRefreshToken(RefreshToken refreshToken) {
		// TODO Auto-generated method stub
		return refreshTokenManager.saveOrUpdateRefreshToken(refreshToken);
	}

	/**
	 * get user's refresh token
	 * 
	 * @param userid
	 * @param cloudtype
	 * @return
	 * @author JacksonLi String
	 */
	public String getRefreshToken(Integer userid, String cloudtype) {
		// TODO Auto-generated method stub
		return refreshTokenManager.getRefreshToken(userid, cloudtype);
	}

	/**
	 * get all job from database
	 * 
	 * @return
	 * @author JacksonLi List<Job>
	 */
	public List<JobJB> getJobList() {
		return jobService.getJobList();
	}

	/**
	 * get job by job id
	 * 
	 * @param jobId
	 * @return
	 * @author JacksonLi Job
	 */
	public JobJB getJobById(String jobId) {
		return jobService.getJobById(jobId);
	}

	/**
	 * get all areas
	 * 
	 * @return
	 * @author JacksonLi List<Area>
	 */
	public List<Area> getAreas() {
		return areaService.getAreas();
	}

	/**
	 * get all domains
	 * 
	 * @return
	 * @author JacksonLi List<Domain>
	 */
	public List<Domain> getDomains() {
		return domainService.getDomains();
	}

	/**
	 * get all jobs with area filter
	 * 
	 * @param areas
	 * @return
	 * @author JacksonLi List<JobJB>
	 */
	public List<JobJB> getJobListWithAreaFilter(JSONArray areas) {
		return jobService.getJobListWithAreaFilter(areas);
	}

	/**
	 * get all jobs with domain filter
	 * 
	 * @param domains
	 * @return
	 * @author JacksonLi List<JobJB>
	 */
	public List<JobJB> getJobListWithDomainFilter(JSONArray domains) {
		return jobService.getJobListWithDomainFilter(domains);
	}

	/**
	 * get all jobs with domain and area filter
	 * 
	 * @param domains
	 * @param areas
	 * @return
	 * @author JacksonLi List<JobJB>
	 */
	public List<JobJB> getJobListWithDomainAndAreaFilter(JSONArray domains,
			JSONArray areas) {
		return jobService.getJobListWithDomainAndAreaFilter(domains, areas);
	}

	/**
	 * update user status
	 * 
	 * @param userId
	 * @param status
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean updateUserStatus(String userId, int status) {
		return managerImpl.updateStatus(userId, status);
	}

	/**
	 * get grades list by user id
	 * 
	 * @param userId
	 * @param courseNumber
	 * @param cycleNumber
	 * @return
	 * @author JacksonLi List<GradesJB>
	 */
	public List<GradesJB> getGradesByUserId(String userId, String courseNumber,
			String cycleNumber) {
		return gradesService.getGradesByUserId(userId, courseNumber,
				cycleNumber);
	}

	/**
	 * get course syllabus by user id
	 * 
	 * @param userId
	 * @return List<CourseSyllabusJB>
	 * @author Brad
	 */
	public List<CourseSyllabusJB> getCourseSyllabusByUserId(String userId) {
		return courseSyllabusService.getCourseSyllabusByUserId(userId);
	}

	/**
	 * user login and update his status or flag
	 * 
	 * @param user
	 * @return
	 * @author JacksonLi boolean
	 */
	public String userLogin(User user, String registionId) {
		boolean flag = managerImpl.userLogin(user, registionId);
		if (flag) {
			String tok = createToken();
			return tok;
		}
		return "";
	}

	public String finduser(String userId, String pwd) {
		boolean flag = managerImpl.findUser(userId, pwd);
		if (flag) {
			return "true";
		}
		return "";
	}
}
