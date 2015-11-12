package com.matrix.jbt.servlet;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import com.matrix.jbt.getdatafromjb.HandleDataFromJB;
import com.matrix.jbt.getdatafromjb.HandleStaticVariable;
import com.matrix.jbt.service.TimeOutService;
import com.matrix.jbt.service.UserService;
import com.matrix.jbt.service.impl.TimeOutServiceImpl;
import com.matrix.jbt.service.impl.UserServiceImpl;
import com.matrix.jbt.tool.DateUtil;
import com.matrix.jbt.tool.ReadProperties;

/**
 * Servlet implementation class GetDataFromJBServlet
 */
public class GetDataFromJBServlet extends HttpServlet {
	private TimeOutService timeOutService = new TimeOutServiceImpl();
	private UserService userService = new UserServiceImpl();
	// get current execute time
	public static String currentTime = "firstTime";
	// get current execute times
	public static int executeCount = 0;
	// judge weather system is getting data from JB 0 false 1 true
	public static int isExecuting = 0;

	public static SchedulerFactory schedulerFactory = null;
	public static Scheduler scheduler = null;

	public static JobDetail jobDetail1 = null;
	public static SimpleTrigger trigger1 = null;

	// handle execute static variable
	public static SimpleTrigger trigger0 = null;
	public static JobDetail jobDetail0 = null;

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetDataFromJBServlet() {
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
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);

		userService.initAllUserFlag();

		Calendar cal = Calendar.getInstance();
		TimeZone timeZone = cal.getTimeZone();
		DateUtil.defaultTimeZone = timeZone;

		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));

		String firstTime = timeOutService.getFirstGetDataTime();
		String recoveryTime = ReadProperties.read("recoverytime",
				"recoverytime");

		// Quartz schedule
		// update database twice per day(specified time)
		try {
			schedulerFactory = new StdSchedulerFactory();
			scheduler = schedulerFactory.getScheduler();

			jobDetail1 = newJob(HandleDataFromJB.class).withIdentity(
					"handleJob1", "group1").build();

			trigger1 = newTrigger()
					.withIdentity("handleTrigger1", "group1")
					.startAt(DateUtil.toGMT(firstTime))
					.withSchedule(
							simpleSchedule().withIntervalInHours(24)
									.repeatForever()).build();

			// recovery data
			jobDetail0 = newJob(HandleStaticVariable.class).withIdentity(
					"handleJob0", "group0").build();

			trigger0 = newTrigger()
					.withIdentity("handleTrigger0", "group0")
					.startAt(DateUtil.toGMT(recoveryTime))
					.withSchedule(
							simpleSchedule().withIntervalInHours(24)
									.repeatForever()).build();

			scheduler.scheduleJob(jobDetail1, trigger1);
			scheduler.scheduleJob(jobDetail0, trigger0);

			scheduler.start();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
