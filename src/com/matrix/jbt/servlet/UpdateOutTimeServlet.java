package com.matrix.jbt.servlet;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.SchedulerException;

import com.matrix.jbt.service.TimeOutService;
import com.matrix.jbt.service.impl.TimeOutServiceImpl;
import com.matrix.jbt.tool.DateUtil;
import com.matrix.jbt.tool.ReadProperties;
import com.matrix.jbt.tool.UserPool;

/**
 * Servlet implementation class UpdateOutTimeServlet
 */
public class UpdateOutTimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TimeOutService timeOutManager = new TimeOutServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateOutTimeServlet() {
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
	 * set operate timeout and change passowrd timeout
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String OT = request.getParameter("outTime");
		String SGT = request.getParameter("setGetTime");
		String type = request.getParameter("type");
		String recoveryTime = ReadProperties.read("recoverytime",
				"recoverytime");

		String outTime = "";
		boolean flag = false;

		if (OT != null) {
			outTime = Integer.parseInt(OT) * 1000 + "";

			if (type.equals("0")) {
				// set operate time out
				flag = timeOutManager.updateOutTime(outTime);
				if (flag) {
					UserPool.timeOut = outTime;
				}
			} else if (type.equals("1")) {
				// set change password time out
				flag = timeOutManager.updateCPWDOutTime(outTime);
				if (flag) {
					UserPool.CPWDTimeOut = outTime;
				}
			}
		} else if (SGT.equals("true")) {
			if (type.equals("2")) {
				try {
					String firstH = request.getParameter("firstHour");
					String firstM = request.getParameter("firstMinute");
					String firstS = request.getParameter("firstSecond");
					String fTime = firstH + ":" + firstM + ":" + firstS;
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					String firstTime = fTime;
					String secondTime = timeOutManager.getSecondGetDataTime();

					Date d1 = sdf.parse(firstTime);
					Date d2 = sdf.parse(secondTime);

					// set first get data from john bryce time
					if (GetDataFromJBServlet.currentTime.equals("firstTime")) {
						if (GetDataFromJBServlet.isExecuting == 1) {
							out.write("executing");
						} else {
							try {
								if (d1.getTime() - d2.getTime() > 0) {
									System.out
											.println("The Time You Set Is Larger Than The Second Time");
								} else {
									GetDataFromJBServlet.scheduler
											.shutdown(true);
									GetDataFromJBServlet.trigger1 = newTrigger()
											.withIdentity("handleTrigger1",
													"group1")
											.startAt(DateUtil.toGMT(firstTime))
											.withSchedule(
													simpleSchedule()
															.withIntervalInHours(
																	24)
															.repeatForever())
											.build();

									GetDataFromJBServlet.trigger0 = newTrigger()
											.withIdentity("handleTrigger0",
													"group0")
											.startAt(
													DateUtil.toGMT(recoveryTime))
											.withSchedule(
													simpleSchedule()
															.withIntervalInHours(
																	24)
															.repeatForever())
											.build();

									GetDataFromJBServlet.scheduler = GetDataFromJBServlet.schedulerFactory
											.getScheduler();

									GetDataFromJBServlet.scheduler.scheduleJob(
											GetDataFromJBServlet.jobDetail1,
											GetDataFromJBServlet.trigger1);

									GetDataFromJBServlet.scheduler.scheduleJob(
											GetDataFromJBServlet.jobDetail0,
											GetDataFromJBServlet.trigger0);
									GetDataFromJBServlet.scheduler.start();

									flag = timeOutManager
											.updateFirstGetDataTime(fTime);
								}

							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SchedulerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else {
						if (d1.getTime() - d2.getTime() > 0) {
							System.out
									.println("The Time You Set Is Larger Than The Second Time");
						} else {
							flag = timeOutManager.updateFirstGetDataTime(fTime);
						}
					}
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (type.equals("3")) {
				try {
					String secondH = request.getParameter("secondHour");
					String secondM = request.getParameter("secondMinute");
					String secondS = request.getParameter("secondSecond");
					String sTime = secondH + ":" + secondM + ":" + secondS;
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					String firstTime = timeOutManager.getFirstGetDataTime();
					String secondTime = sTime;

					Date d1 = sdf.parse(firstTime);
					Date d2 = sdf.parse(secondTime);

					// set second get data from john bryce time
					if (GetDataFromJBServlet.currentTime.equals("secondTime")) {
						if (GetDataFromJBServlet.isExecuting == 1) {
							out.write("executing");
						} else {
							try {
								if (d1.getTime() - d2.getTime() > 0) {
									System.out
											.println("The Time You Set Is Smailler Than The First Time");
								} else {
									GetDataFromJBServlet.scheduler
											.shutdown(true);
									GetDataFromJBServlet.trigger1 = newTrigger()
											.withIdentity("handleTrigger1",
													"group1")
											.startAt(DateUtil.toGMT(secondTime))
											.withSchedule(
													simpleSchedule()
															.withIntervalInHours(
																	24)
															.repeatForever())
											.build();

									GetDataFromJBServlet.trigger0 = newTrigger()
											.withIdentity("handleTrigger0",
													"group0")
											.startAt(
													DateUtil.toGMT(recoveryTime))
											.withSchedule(
													simpleSchedule()
															.withIntervalInHours(
																	24)
															.repeatForever())
											.build();

									GetDataFromJBServlet.scheduler = GetDataFromJBServlet.schedulerFactory
											.getScheduler();

									GetDataFromJBServlet.scheduler.scheduleJob(
											GetDataFromJBServlet.jobDetail1,
											GetDataFromJBServlet.trigger1);
									GetDataFromJBServlet.scheduler.scheduleJob(
											GetDataFromJBServlet.jobDetail0,
											GetDataFromJBServlet.trigger0);
									GetDataFromJBServlet.scheduler.start();

									flag = timeOutManager
											.updateSecondGetDataTime(sTime);
								}

							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else {
						if (d1.getTime() - d2.getTime() > 0) {
							System.out
									.println("The Time You Set Is Smailler Than The First Time");
						} else {
							flag = timeOutManager
									.updateSecondGetDataTime(sTime);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}
		out.write(String.valueOf(flag));
	}

}
