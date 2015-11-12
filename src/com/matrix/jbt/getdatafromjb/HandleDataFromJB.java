package com.matrix.jbt.getdatafromjb;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.alibaba.fastjson.JSONException;
import com.matrix.jbt.entity.BranchJB;
import com.matrix.jbt.entity.CourseJB;
import com.matrix.jbt.entity.CourseSyllabusJB;
import com.matrix.jbt.entity.GradesJB;
import com.matrix.jbt.entity.JobJB;
import com.matrix.jbt.entity.User;
import com.matrix.jbt.service.CourseSyllabusService;
import com.matrix.jbt.service.GradesService;
import com.matrix.jbt.service.JobService;
import com.matrix.jbt.service.TimeOutService;
import com.matrix.jbt.service.UserService;
import com.matrix.jbt.service.impl.CourseSyllabusServiceImpl;
import com.matrix.jbt.service.impl.GradesServiceImpl;
import com.matrix.jbt.service.impl.JobServiceImpl;
import com.matrix.jbt.service.impl.TimeOutServiceImpl;
import com.matrix.jbt.service.impl.UserServiceImpl;
import com.matrix.jbt.servlet.GetDataFromJBServlet;
import com.matrix.jbt.tool.DateUtil;
import com.matrix.jbt.tool.GetDataFromWeb;
import com.matrix.jbt.tool.ReadProperties;

public class HandleDataFromJB implements Job {
	private final static String DATA_URL1 = ReadProperties.read("url", "getjobs");
	private final static String DATA_URL2 = ReadProperties.read("url", "getgrades");
	private final static String DATA_URL3 = ReadProperties.read("url", "getcoursesyllabus");
	// private JobsGradesCourseSyllabusService jobsGradesCourseSyllabusService =
	// new JobsGradesCourseSyllabusServiceImpl();
	private JobService jobService = new JobServiceImpl();
	private GradesService gradesService = new GradesServiceImpl();
	private CourseSyllabusService courseSyllabusService = new CourseSyllabusServiceImpl();
	private UserService userService = new UserServiceImpl();
	private TimeOutService timeOutManager = new TimeOutServiceImpl();
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String getJobsFromJB = "";
	private String getGradesFromJB = "";
	private String getCourseSyllabusFromJB = "";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("---->>" + GetDataFromJBServlet.currentTime + "----->>" + GetDataFromJBServlet.isExecuting
				+ "---->" + GetDataFromJBServlet.executeCount);

		String recoveryTime = ReadProperties.read("recoverytime", "recoverytime");

		if (GetDataFromJBServlet.executeCount < 2) {
			if (GetDataFromJBServlet.currentTime.equals("firstTime") && GetDataFromJBServlet.isExecuting == 0) {
				try {
					String startDate = sdf1.format(new Date());
					System.out.println(sdf2.format(new Date()));

					GetDataFromJBServlet.isExecuting = 1;
					GetDataFromJBServlet.executeCount += 1;

					getData();

					updateJobs(getJobsFromJB);
					updateGrades(getGradesFromJB);
					updateCourseSyllabus(getCourseSyllabusFromJB);

					Date endGetDate = new Date();
					String endDate = sdf1.format(endGetDate);
					System.out.println(sdf2.format(endGetDate));

					if (startDate.equals(endDate)) {
						String secondTime = timeOutManager.getSecondGetDataTime();
						Date d1 = sdf.parse(sdf.format(endGetDate));
						Date d2 = sdf.parse(secondTime);

						// second get data from jb function will not be executed
						if (d1.getTime() - d2.getTime() > 0) {
							GetDataFromJBServlet.currentTime = "firstTime";

							GetDataFromJBServlet.executeCount += 1;

							String firstTime = timeOutManager.getFirstGetDataTime();

							System.out.println("first--->first--->" + firstTime);

							firstSchedule(firstTime, recoveryTime);

							GetDataFromJBServlet.isExecuting = 0;
						} else {
							// execute second get data from jb function
							GetDataFromJBServlet.currentTime = "secondTime";

							System.out.println("first--->secondTime--->" + secondTime);

							secondSchedule(secondTime, recoveryTime);

							GetDataFromJBServlet.isExecuting = 0;
						}
					} else {
						String firstTime = timeOutManager.getFirstGetDataTime();
						Date d1 = sdf.parse(sdf.format(endGetDate));
						Date d2 = sdf.parse(firstTime);

						if (d1.getTime() - d2.getTime() > 0) {
							String secondTime = timeOutManager.getSecondGetDataTime();
							System.out.println("first--->secondTime--->" + secondTime);

							GetDataFromJBServlet.currentTime = "secondTime";

							GetDataFromJBServlet.executeCount += 1;

							secondSchedule(secondTime, recoveryTime);

							GetDataFromJBServlet.isExecuting = 0;
						} else {
							System.out.println("firstTime----->firstTime----->" + firstTime);
							// execute second get data from jb function
							GetDataFromJBServlet.currentTime = "firstTime";

							firstSchedule(firstTime, recoveryTime);

							GetDataFromJBServlet.isExecuting = 0;
						}
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (GetDataFromJBServlet.currentTime.equals("secondTime") && GetDataFromJBServlet.isExecuting == 0) {
				try {
					String startDate = sdf1.format(new Date());
					System.out.println(sdf2.format(new Date()));

					GetDataFromJBServlet.isExecuting = 1;
					GetDataFromJBServlet.executeCount += 1;

					getData();

					updateJobs(getJobsFromJB);
					updateGrades(getGradesFromJB);
					updateCourseSyllabus(getCourseSyllabusFromJB);

					Date endGetDate = new Date();
					String endDate = sdf1.format(endGetDate);
					System.out.println(sdf2.format(endGetDate));

					if (startDate.equals(endDate)) {
						String firstTime = timeOutManager.getFirstGetDataTime();

						System.out.println("second----->firstTime----->" + firstTime);
						// execute second get data from jb function
						GetDataFromJBServlet.currentTime = "firstTime";

						firstSchedule(firstTime, recoveryTime);

						GetDataFromJBServlet.isExecuting = 0;
					} else {
						String firstTime = timeOutManager.getFirstGetDataTime();
						Date d1 = sdf.parse(sdf.format(endGetDate));
						Date d2 = sdf.parse(firstTime);

						if (d1.getTime() - d2.getTime() > 0) {
							String secondTime = timeOutManager.getSecondGetDataTime();

							System.out.println("second----->second----->" + secondTime);

							GetDataFromJBServlet.currentTime = "secondTime";

							GetDataFromJBServlet.executeCount += 1;

							secondSchedule(secondTime, recoveryTime);

							GetDataFromJBServlet.isExecuting = 0;
						} else {
							System.out.println("second----->firstTime----->" + firstTime);
							// execute second get data from jb function
							GetDataFromJBServlet.currentTime = "firstTime";

							firstSchedule(firstTime, recoveryTime);

							GetDataFromJBServlet.isExecuting = 0;
						}
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void getData() {
		try {
			System.out.println("Begin get data......");
			// create a thread pool
			ExecutorService pool = Executors.newFixedThreadPool(3);
			// create three tasks with returned value
			Callable<String> task1 = new MyCallable(DATA_URL1);
			Callable<String> task2 = new MyCallable(DATA_URL2);
			Callable<String> task3 = new MyCallable(DATA_URL3);
			// execute task an return object Future
			Future<?> f1 = pool.submit(task1);
			Future<?> f2 = pool.submit(task2);
			Future<?> f3 = pool.submit(task3);
			pool.shutdown();
			while (true) {
				if (pool.isTerminated()) {
					// get returned value from object Future
					getJobsFromJB = f1.get().toString();
					getGradesFromJB = f2.get().toString();
					getCourseSyllabusFromJB = f3.get().toString();
					break;
				}
				TimeUnit.MILLISECONDS.sleep(100);
			}
			System.out.println("End get data......");
			System.out.println(new Date());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateJobs(String getJobs) {
		if (getJobs.equals("false")) {

		} else {
			if (getJobs == null || getJobs.equals("")) {
				boolean flag = jobService.deleteJobs();
				System.out.println("delete jobs " + (flag == true ? "success" : "fail"));
			} else {
				com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(getJobs);
				try {
					com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
							.parseArray(jsonObject.getString("d"));

					List<JobJB> jobList = new ArrayList<>();
					JobJB job = null;

					for (int i = 0; i < jsonArray.size(); i++) {
						String jobid = jsonArray.getJSONObject(i).getString("ID") == null ? "null"
								: jsonArray.getJSONObject(i).getString("ID");
						String title = jsonArray.getJSONObject(i).getString("Title") == null ? "null"
								: jsonArray.getJSONObject(i).getString("Title");
						String company = jsonArray.getJSONObject(i).getString("Company") == null ? "null"
								: jsonArray.getJSONObject(i).getString("Company");
						String area = jsonArray.getJSONObject(i).getString("Area") == null ? "null"
								: jsonArray.getJSONObject(i).getString("Area");
						String domain = jsonArray.getJSONObject(i).getString("Domain") == null ? "null"
								: jsonArray.getJSONObject(i).getString("Domain");
						String experience = jsonArray.getJSONObject(i).getString("Experience") == null ? "null"
								: jsonArray.getJSONObject(i).getString("Experience");
						String text = jsonArray.getJSONObject(i).getString("Text") == null ? "null"
								: jsonArray.getJSONObject(i).getString("Text");
						String requirements = jsonArray.getJSONObject(i).getString("Requirements") == null ? "null"
								: jsonArray.getJSONObject(i).getString("Requirements");
						String email = jsonArray.getJSONObject(i).getString("Email") == null ? "null"
								: jsonArray.getJSONObject(i).getString("Email");

						job = new JobJB(jobid, title, company, area, domain, experience, text, requirements, email);

						jobList.add(job);
					}
					boolean flag = jobService.updateJob(jobList);
					System.out.println("update jobs " + (flag == true ? "success" : "fail"));
				} catch (JSONException e) {
					// TODO: handle exception
					String isEmpty = jsonObject.getString("d");
					if (isEmpty.equals("\"Empty\"")) {
						boolean flag = jobService.deleteJobs();
						System.out.println("delete jobs " + (flag == true ? "success" : "fail"));
					}
				}
			}
		}
	}

	private void updateGrades(String getGrades) {
		if (getGrades.equals("false")) {

		} else {
			if (getGrades == null || getGrades.equals("")) {
				boolean flag = gradesService.deleteGrades();
				System.out.println("delete grades " + (flag == true ? "success" : "fail"));
			} else {
				com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(getGrades);
				try {
					com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
							.parseArray(jsonObject.getString("d"));

					List<GradesJB> gradesJBs = new ArrayList<>();
					GradesJB gradesJB = null;

					for (int i = 0; i < jsonArray.size(); i++) {
						String id = jsonArray.getJSONObject(i).getString("ID") == null ? "null"
								: jsonArray.getJSONObject(i).getString("ID");
						String studentId = jsonArray.getJSONObject(i).getString("StudentID") == null ? "null"
								: jsonArray.getJSONObject(i).getString("StudentID");
						String courseNumber = jsonArray.getJSONObject(i).getString("CourseNumber") == null ? "null"
								: jsonArray.getJSONObject(i).getString("CourseNumber");
						String cycleNumber = jsonArray.getJSONObject(i).getString("CycleNumber") == null ? "null"
								: jsonArray.getJSONObject(i).getString("CycleNumber");
						String subject = jsonArray.getJSONObject(i).getString("Subject") == null ? "null"
								: jsonArray.getJSONObject(i).getString("Subject");
						String points = jsonArray.getJSONObject(i).getString("Points") == null ? "null"
								: jsonArray.getJSONObject(i).getString("Points");

						gradesJB = new GradesJB(id, Integer.parseInt(studentId), courseNumber, cycleNumber, subject,
								points);

						gradesJBs.add(gradesJB);
					}

					boolean flag = gradesService.updateGrades(gradesJBs);
					System.out.println("update grades " + (flag == true ? "success" : "fail"));
				} catch (JSONException e) {
					// TODO: handle exception
					String isEmpty = jsonObject.getString("d");
					if (isEmpty.equals("\"Empty\"")) {
						boolean flag = gradesService.deleteGrades();
						System.out.println("delete grades " + (flag == true ? "success" : "fail"));
					}
				}

			}
		}
	}

	private void updateCourseSyllabus(String getCourseSyllabus) {
		if (getCourseSyllabus.equals("false")) {

		} else {
			if (getCourseSyllabus == null || getCourseSyllabus.equals("")) {
				boolean flag = courseSyllabusService.deleteCourseSyllabus();
				System.out.println("delete course syllabus " + (flag == true ? "success" : "fail"));
			} else {
				com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject
						.parseObject(getCourseSyllabus);
				try {
					com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
							.parseArray(jsonObject.getString("d"));

					List<CourseSyllabusJB> courseSyllabusJBs = new ArrayList<>();
					List<CourseJB> courseJBs = new ArrayList<>();
					List<BranchJB> branchJBs = new ArrayList<>();
					CourseSyllabusJB courseSyllabusJB = null;
					CourseJB courseJB = null;
					BranchJB branchJB = null;
					List<User> usersGetFromJB = new ArrayList<>();
					for (int i = 0; i < jsonArray.size(); i++) {
						// course syllabus
						String userId = jsonArray.getJSONObject(i).getString("ID") == null ? "null"
								: jsonArray.getJSONObject(i).getString("ID");
						String meetDate = jsonArray.getJSONObject(i).getString("MeetDate") == null ? "null"
								: jsonArray.getJSONObject(i).getString("MeetDate");
						String meetStart = jsonArray.getJSONObject(i).getString("MeetStart") == null ? "null"
								: jsonArray.getJSONObject(i).getString("MeetStart");
						String meetEnd = jsonArray.getJSONObject(i).getString("MeetEnd") == null ? "null"
								: jsonArray.getJSONObject(i).getString("MeetEnd");
						String meetNum = jsonArray.getJSONObject(i).getString("MeetNum") == null ? "null"
								: jsonArray.getJSONObject(i).getString("MeetNum");
						String meetBranch = jsonArray.getJSONObject(i).getString("MeetBranch") == null ? "null"
								: jsonArray.getJSONObject(i).getString("MeetBranch");
						String meetBuildingNum = jsonArray.getJSONObject(i).getString("MeetBuildingNum") == null
								? "null" : jsonArray.getJSONObject(i).getString("MeetBuildingNum");
						String meetBuildingName = jsonArray.getJSONObject(i).getString("MeetBuildingName") == null
								? "null" : jsonArray.getJSONObject(i).getString("MeetBuildingName");
						String meetClassNum = jsonArray.getJSONObject(i).getString("MeetClassNum") == null ? "null"
								: jsonArray.getJSONObject(i).getString("MeetClassNum");
						String meetTeacher = jsonArray.getJSONObject(i).getString("MeetTeacher") == null ? "null"
								: jsonArray.getJSONObject(i).getString("MeetTeacher");
						String meetStatus = jsonArray.getJSONObject(i).getString("MeetStatus") == null ? "null"
								: jsonArray.getJSONObject(i).getString("MeetStatus");
						String meetTopic = jsonArray.getJSONObject(i).getString("MeetTopic") == null ? "null"
								: jsonArray.getJSONObject(i).getString("MeetTopic");
						String meetClassName = jsonArray.getJSONObject(i).getString("MeetClassName") == null ? "null"
								: jsonArray.getJSONObject(i).getString("MeetClassName");

						courseSyllabusJB = new CourseSyllabusJB();
						courseSyllabusJB.setUserId(userId);
						courseSyllabusJB.setMeetDate(meetDate);
						courseSyllabusJB.setMeetStart(meetStart);
						courseSyllabusJB.setMeetEnd(meetEnd);
						courseSyllabusJB.setMeetNum(meetNum);
						courseSyllabusJB.setMeetBranch(meetBranch);
						courseSyllabusJB.setMeetBuildingNum(meetBuildingNum);
						courseSyllabusJB.setMeetBuildingName(meetBuildingName);
						courseSyllabusJB.setMeetClassNum(meetClassNum);
						courseSyllabusJB.setMeetTeacher(meetTeacher);
						courseSyllabusJB.setMeetStatus(meetStatus);
						courseSyllabusJB.setMeetTopic(meetTopic);
						courseSyllabusJB.setMeetClassName(meetClassName);

						// course
						String courseName = jsonArray.getJSONObject(i).getString("CourseName") == null ? "null"
								: jsonArray.getJSONObject(i).getString("CourseName");
						String courseNum = jsonArray.getJSONObject(i).getString("CourseNumber") == null ? "null"
								: jsonArray.getJSONObject(i).getString("CourseNumber");
						String cycleNum = jsonArray.getJSONObject(i).getString("CycleNumber") == null ? "null"
								: jsonArray.getJSONObject(i).getString("CycleNumber");
						String cycleOpenDate = jsonArray.getJSONObject(i).getString("CycleOpenDate") == null ? "null"
								: jsonArray.getJSONObject(i).getString("CycleOpenDate");
						String domainNumber = jsonArray.getJSONObject(i).getString("DomainNumber") == null ? "null"
								: jsonArray.getJSONObject(i).getString("DomainNumber");
						String domainName = jsonArray.getJSONObject(i).getString("DomainName") == null ? "null"
								: jsonArray.getJSONObject(i).getString("DomainName");

						courseJB = new CourseJB();
						courseJB.setCourseName(courseName);
						courseJB.setCourseNum(courseNum);
						courseJB.setCycleNum(cycleNum);
						courseJB.setCycleOpenDate(cycleOpenDate);
						courseJB.setDomainNumber(domainNumber);
						courseJB.setDomainName(domainName);
						courseJB.setUserId(userId);

						// branch
						String branch = jsonArray.getJSONObject(i).getString("Branch") == null ? "null"
								: jsonArray.getJSONObject(i).getString("Branch");

						branchJB = new BranchJB();
						branchJB.setBranch(branch);
						branchJB.setUserId(userId);

						courseSyllabusJBs.add(courseSyllabusJB);

						courseJBs.add(courseJB);

						// judge weather user's
						// branch is
						// existing
						boolean isBranchExist = false;
						if (branchJBs.size() == 0) {
							branchJBs.add(branchJB);
						} else {
							for (Object branchJB2 : branchJBs) {
								if (((BranchJB) branchJB2).getBranch().equals(branchJB.getBranch())
										&& ((BranchJB) branchJB2).getUserId().equals(branchJB.getUserId())) {
									isBranchExist = true;
									break;
								}
							}
							if (!isBranchExist) {
								branchJBs.add(branchJB);
							}
						}

						// user
						String firstName = jsonArray.getJSONObject(i).getString("FirstName") == null ? "null"
								: jsonArray.getJSONObject(i).getString("FirstName");
						String lastName = jsonArray.getJSONObject(i).getString("LastName") == null ? "null"
								: jsonArray.getJSONObject(i).getString("LastName");
						String email = jsonArray.getJSONObject(i).getString("Email") == null ? "null"
								: jsonArray.getJSONObject(i).getString("Email");

						// judge weather user is existing
						boolean isUserExist = false;
						User user = new User(userId, firstName, lastName, "000000", email, 0, 0);
						if (usersGetFromJB.size() == 0) {
							usersGetFromJB.add(user);
						} else {
							for (Object user2 : usersGetFromJB) {
								if (user.getUserId().equals(((User) user2).getUserId())) {
									isUserExist = true;
									break;
								}
							}
							if (!isUserExist) {
								usersGetFromJB.add(user);
							}
						}
					}

					// compare and update table tb_user
					List<User> usersInDataBase = userService.getAllUsers();
					if (usersInDataBase.size() != 1) {
						List<User> updatedUsers = new ArrayList<>();
						List<User> insertUsers = new ArrayList<>();
						for (User user : usersGetFromJB) {
							boolean flag = false;
							for (User user2 : usersInDataBase) {
								if (user.getUserId().equals(user2.getUserId())) {
									updatedUsers.add(user);
									flag = true;
									break;
								}
							}
							if (!flag) {
								insertUsers.add(user);
							}
						}
						boolean flag = courseSyllabusService.updateCourseSyllabus(courseSyllabusJBs, courseJBs,
								branchJBs, updatedUsers, insertUsers);
						System.out.println("update course syllabus " + (flag == true ? "success" : "fail"));
					} else {
						boolean flag = courseSyllabusService.updateCourseSyllabus(courseSyllabusJBs, courseJBs,
								branchJBs, null, usersGetFromJB);
						System.out.println("update course syllabus " + (flag == true ? "success" : "fail"));
					}

				} catch (JSONException e) {
					// TODO: handle exception
					String isEmpty = jsonObject.getString("d");
					if (isEmpty.equals("\"Empty\"")) {
						boolean flag = courseSyllabusService.deleteCourseSyllabus();
						System.out.println("delete course syllabus " + (flag == true ? "success" : "fail"));
					}
				}
			}
		}
	}

	private void firstSchedule(String firstTime, String recoveryTime) {
		try {
			GetDataFromJBServlet.scheduler.deleteJob(jobKey("handleJob1", "group1"));
			GetDataFromJBServlet.scheduler.deleteJob(jobKey("handleJob0", "group0"));

			GetDataFromJBServlet.jobDetail1 = newJob(HandleDataFromJB.class).withIdentity("handleJob1", "group1")
					.build();
			GetDataFromJBServlet.trigger1 = newTrigger().withIdentity("handleTrigger1", "group1")
					.startAt(DateUtil.toGMT(firstTime))
					.withSchedule(simpleSchedule().withIntervalInHours(24).repeatForever()).build();

			GetDataFromJBServlet.jobDetail0 = newJob(HandleStaticVariable.class).withIdentity("handleJob0", "group0")
					.build();
			GetDataFromJBServlet.trigger0 = newTrigger().withIdentity("handleTrigger0", "group0")
					.startAt(DateUtil.toGMT(recoveryTime))
					.withSchedule(simpleSchedule().withIntervalInHours(24).repeatForever()).build();

			GetDataFromJBServlet.scheduler.scheduleJob(GetDataFromJBServlet.jobDetail1, GetDataFromJBServlet.trigger1);

			GetDataFromJBServlet.scheduler.scheduleJob(GetDataFromJBServlet.jobDetail0, GetDataFromJBServlet.trigger0);

			GetDataFromJBServlet.scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void secondSchedule(String secondTime, String recoveryTime) {
		try {
			GetDataFromJBServlet.scheduler.deleteJob(jobKey("handleJob1", "group1"));
			GetDataFromJBServlet.scheduler.deleteJob(jobKey("handleJob0", "group0"));

			GetDataFromJBServlet.jobDetail1 = newJob(HandleDataFromJB.class).withIdentity("handleJob1", "group1")
					.build();
			GetDataFromJBServlet.trigger1 = newTrigger().withIdentity("handleTrigger1", "group1")
					.startAt(DateUtil.toGMT(secondTime))
					.withSchedule(simpleSchedule().withIntervalInHours(24).repeatForever()).build();

			GetDataFromJBServlet.jobDetail0 = newJob(HandleStaticVariable.class).withIdentity("handleJob0", "group0")
					.build();
			GetDataFromJBServlet.trigger0 = newTrigger().withIdentity("handleTrigger0", "group0")
					.startAt(DateUtil.toGMT(recoveryTime))
					.withSchedule(simpleSchedule().withIntervalInHours(24).repeatForever()).build();

			GetDataFromJBServlet.scheduler.scheduleJob(GetDataFromJBServlet.jobDetail1, GetDataFromJBServlet.trigger1);
			GetDataFromJBServlet.scheduler.scheduleJob(GetDataFromJBServlet.jobDetail0, GetDataFromJBServlet.trigger0);

			GetDataFromJBServlet.scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// private void updateDatabase(String jobsData, String gradeData,
	// String courseSyllabusData) {
	// if (jobsData.equals("false") || gradeData.equals("false")
	// || courseSyllabusData.equals("false")) {
	//
	// } else {
	// if (jobsData == null || jobsData.equals("")) {
	// if (gradeData == null || gradeData.equals("")) {
	// if (courseSyllabusData == null
	// || courseSyllabusData.equals("")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteJobsGradesCourseSyllabus();
	// System.out
	// .println("delete jobs grades course syllabus "
	// + (flag == true ? "success" : "fail"));
	// } else {
	// com.alibaba.fastjson.JSONObject jsonObject =
	// com.alibaba.fastjson.JSONObject
	// .parseObject(courseSyllabusData);
	// try {
	// com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
	// .parseArray(jsonObject.getString("d"));
	//
	// Map<String, List<Object>> map = analysisCourseSyllabus(jsonArray);
	//
	// List<Object> courseSyllabusJBs = map
	// .get("courseSyllabusJBs");
	// List<Object> courseJBs = map.get("courseJBs");
	// List<Object> branchJBs = map.get("branchJBs");
	// List<Object> usersGetFromJB = map
	// .get("usersGetFromJB");
	//
	// // compare and update table tb_user
	// List<User> usersInDataBase = userService
	// .getAllUsers();
	// if (usersInDataBase.size() != 1) {
	// List<Object> updatedUsers = new ArrayList<>();
	// List<Object> insertUsers = new ArrayList<>();
	// for (Object user : usersGetFromJB) {
	// boolean flag = false;
	// for (User user2 : usersInDataBase) {
	// if (((User) user).getUserId().equals(
	// user2.getUserId())) {
	// updatedUsers.add(user);
	// flag = true;
	// break;
	// }
	// }
	// if (!flag) {
	// insertUsers.add(user);
	// }
	// }
	//
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateCourseSyllabus(
	// courseSyllabusJBs, courseJBs,
	// branchJBs, updatedUsers,
	// insertUsers);
	// System.out
	// .println("delete jobs grades and update course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateCourseSyllabus(
	// courseSyllabusJBs, courseJBs,
	// branchJBs, null, usersGetFromJB);
	// System.out
	// .println("delete jobs grades and update course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// } catch (com.alibaba.fastjson.JSONException e) {
	// // TODO: handle exception
	// String isEmpty = jsonObject.getString("d");
	// if (isEmpty.equals("\"Empty\"")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteJobsGradesCourseSyllabus();
	// System.out
	// .println("delete jobs grades course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// }
	// }
	// } else {
	// com.alibaba.fastjson.JSONObject jsonObject =
	// com.alibaba.fastjson.JSONObject
	// .parseObject(gradeData);
	// try {
	// com.alibaba.fastjson.JSONArray jsonArray1 =
	// com.alibaba.fastjson.JSONArray
	// .parseArray(jsonObject.getString("d"));
	// List<GradesJB> gradesJBs = analysisGrades(jsonArray1);
	//
	// if (courseSyllabusData == null
	// || courseSyllabusData.equals("")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateGrades(gradesJBs);
	// System.out
	// .println("delete jobs course syllabus and update grades "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// com.alibaba.fastjson.JSONObject jsonObject1 =
	// com.alibaba.fastjson.JSONObject
	// .parseObject(courseSyllabusData);
	// try {
	//
	// com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
	// .parseArray(jsonObject1.getString("d"));
	//
	// Map<String, List<Object>> map = analysisCourseSyllabus(jsonArray);
	//
	// List<Object> courseSyllabusJBs = map
	// .get("courseSyllabusJBs");
	// List<Object> courseJBs = map.get("courseJBs");
	// List<Object> branchJBs = map.get("branchJBs");
	// List<Object> usersGetFromJB = map
	// .get("usersGetFromJB");
	//
	// // compare and update table tb_user
	// List<User> usersInDataBase = userService
	// .getAllUsers();
	// if (usersInDataBase.size() != 1) {
	// List<Object> updatedUsers = new ArrayList<>();
	// List<Object> insertUsers = new ArrayList<>();
	// for (Object user : usersGetFromJB) {
	// boolean flag = false;
	// for (User user2 : usersInDataBase) {
	// if (((User) user).getUserId()
	// .equals(user2.getUserId())) {
	// updatedUsers.add(user);
	// flag = true;
	// break;
	// }
	// }
	// if (!flag) {
	// insertUsers.add(user);
	// }
	// }
	//
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteJobsAndUpdateGradesCourseSyllabus(
	// gradesJBs,
	// courseSyllabusJBs,
	// courseJBs, branchJBs,
	// updatedUsers, insertUsers);
	// System.out
	// .println("delete jobs and update grades course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteJobsAndUpdateGradesCourseSyllabus(
	// gradesJBs,
	// courseSyllabusJBs,
	// courseJBs, branchJBs, null,
	// usersGetFromJB);
	// System.out
	// .println("delete jobs and update grades course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// } catch (com.alibaba.fastjson.JSONException e1) {
	// // TODO: handle exception
	// String isEmpty1 = jsonObject.getString("d");
	// if (isEmpty1.equals("\"Empty\"")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateGrades(gradesJBs);
	// System.out
	// .println("delete jobs course syllabus and update grades "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// }
	// }
	// } catch (com.alibaba.fastjson.JSONException e) {
	// // TODO: handle exception
	// String isEmpty = jsonObject.getString("d");
	// if (isEmpty.equals("\"Empty\"")) {
	// if (courseSyllabusData == null
	// || courseSyllabusData.equals("")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteJobsGradesCourseSyllabus();
	// System.out
	// .println("delete jobs grades course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// com.alibaba.fastjson.JSONObject jsonObject1 =
	// com.alibaba.fastjson.JSONObject
	// .parseObject(courseSyllabusData);
	// try {
	//
	// com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
	// .parseArray(jsonObject1
	// .getString("d"));
	//
	// Map<String, List<Object>> map = analysisCourseSyllabus(jsonArray);
	//
	// List<Object> courseSyllabusJBs = map
	// .get("courseSyllabusJBs");
	// List<Object> courseJBs = map
	// .get("courseJBs");
	// List<Object> branchJBs = map
	// .get("branchJBs");
	// List<Object> usersGetFromJB = map
	// .get("usersGetFromJB");
	//
	// // compare and update table tb_user
	// List<User> usersInDataBase = userService
	// .getAllUsers();
	// if (usersInDataBase.size() != 1) {
	// List<Object> updatedUsers = new ArrayList<>();
	// List<Object> insertUsers = new ArrayList<>();
	// for (Object user : usersGetFromJB) {
	// boolean flag = false;
	// for (User user2 : usersInDataBase) {
	// if (((User) user).getUserId()
	// .equals(user2
	// .getUserId())) {
	// updatedUsers.add(user);
	// flag = true;
	// break;
	// }
	// }
	// if (!flag) {
	// insertUsers.add(user);
	// }
	// }
	//
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateCourseSyllabus(
	// courseSyllabusJBs,
	// courseJBs, branchJBs,
	// updatedUsers,
	// insertUsers);
	// System.out
	// .println("delete jobs grades and update course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateCourseSyllabus(
	// courseSyllabusJBs,
	// courseJBs, branchJBs,
	// null, usersGetFromJB);
	// System.out
	// .println("delete jobs grades and update course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// } catch (com.alibaba.fastjson.JSONException e1) {
	// // TODO: handle exception
	// String isEmpty1 = jsonObject.getString("d");
	// if (isEmpty1.equals("\"Empty\"")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteJobsGradesCourseSyllabus();
	// System.out
	// .println("delete jobs grades course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// }
	// }
	// }
	// }
	// }
	// } else {
	// com.alibaba.fastjson.JSONObject jsonObject5 =
	// com.alibaba.fastjson.JSONObject
	// .parseObject(jobsData);
	// try {
	// com.alibaba.fastjson.JSONArray jsonArray5 =
	// com.alibaba.fastjson.JSONArray
	// .parseArray(jsonObject5.getString("d"));
	//
	// List<JobJB> jobList = analysisJobs(jsonArray5);
	//
	// if (gradeData == null || gradeData.equals("")) {
	// if (courseSyllabusData == null
	// || courseSyllabusData.equals("")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateJobs(jobList);
	// System.out
	// .println("delete grades course syllabus and update jobs "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// com.alibaba.fastjson.JSONObject jsonObject =
	// com.alibaba.fastjson.JSONObject
	// .parseObject(courseSyllabusData);
	// try {
	//
	// com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
	// .parseArray(jsonObject.getString("d"));
	//
	// Map<String, List<Object>> map = analysisCourseSyllabus(jsonArray);
	//
	// List<Object> courseSyllabusJBs = map
	// .get("courseSyllabusJBs");
	// List<Object> courseJBs = map.get("courseJBs");
	// List<Object> branchJBs = map.get("branchJBs");
	// List<Object> usersGetFromJB = map
	// .get("usersGetFromJB");
	//
	// // compare and update table tb_user
	// List<User> usersInDataBase = userService
	// .getAllUsers();
	// if (usersInDataBase.size() != 1) {
	// List<Object> updatedUsers = new ArrayList<>();
	// List<Object> insertUsers = new ArrayList<>();
	// for (Object user : usersGetFromJB) {
	// boolean flag = false;
	// for (User user2 : usersInDataBase) {
	// if (((User) user).getUserId()
	// .equals(user2.getUserId())) {
	// updatedUsers.add(user);
	// flag = true;
	// break;
	// }
	// }
	// if (!flag) {
	// insertUsers.add(user);
	// }
	// }
	//
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteGradesAndUpdateJobsCourseSyllabus(
	// jobList, courseSyllabusJBs,
	// courseJBs, branchJBs,
	// updatedUsers,
	// usersGetFromJB);
	// System.out
	// .println("delete grades and update jobs course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteGradesAndUpdateJobsCourseSyllabus(
	// jobList, courseSyllabusJBs,
	// courseJBs, branchJBs, null,
	// usersGetFromJB);
	// System.out
	// .println("delete grades and update jobs course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// } catch (com.alibaba.fastjson.JSONException e1) {
	// // TODO: handle exception
	// String isEmpty = jsonObject.getString("d");
	// if (isEmpty.equals("\"Empty\"")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateJobs(jobList);
	// System.out
	// .println("delete grades course syllabus and update jobs "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// }
	// }
	// } else {
	// com.alibaba.fastjson.JSONObject jsonObject =
	// com.alibaba.fastjson.JSONObject
	// .parseObject(gradeData);
	// try {
	// com.alibaba.fastjson.JSONArray jsonArray1 =
	// com.alibaba.fastjson.JSONArray
	// .parseArray(jsonObject.getString("d"));
	// List<GradesJB> gradesJBs = analysisGrades(jsonArray1);
	//
	// if (courseSyllabusData == null
	// || courseSyllabusData.equals("")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteCourseSyllabusAndUpdateJobsGrades(
	// jobList, gradesJBs);
	// System.out
	// .println("delete course syllabus and update jobs grades "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// com.alibaba.fastjson.JSONObject jsonObject1 =
	// com.alibaba.fastjson.JSONObject
	// .parseObject(courseSyllabusData);
	// try {
	//
	// com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
	// .parseArray(jsonObject1
	// .getString("d"));
	//
	// Map<String, List<Object>> map = analysisCourseSyllabus(jsonArray);
	//
	// List<Object> courseSyllabusJBs = map
	// .get("courseSyllabusJBs");
	// List<Object> courseJBs = map
	// .get("courseJBs");
	// List<Object> branchJBs = map
	// .get("branchJBs");
	// List<Object> usersGetFromJB = map
	// .get("usersGetFromJB");
	//
	// // compare and update table tb_user
	// List<User> usersInDataBase = userService
	// .getAllUsers();
	// if (usersInDataBase.size() != 1) {
	// List<Object> updatedUsers = new ArrayList<>();
	// List<Object> insertUsers = new ArrayList<>();
	// for (Object user : usersGetFromJB) {
	// boolean flag = false;
	// for (User user2 : usersInDataBase) {
	// if (((User) user).getUserId()
	// .equals(user2
	// .getUserId())) {
	// updatedUsers.add(user);
	// flag = true;
	// break;
	// }
	// }
	// if (!flag) {
	// insertUsers.add(user);
	// }
	// }
	//
	// boolean flag = jobsGradesCourseSyllabusService
	// .updateJobsGradesCourseSyllabus(
	// jobList, gradesJBs,
	// courseSyllabusJBs,
	// courseJBs, branchJBs,
	// updatedUsers,
	// insertUsers);
	// System.out
	// .println("update jobs grades course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// boolean flag = jobsGradesCourseSyllabusService
	// .updateJobsGradesCourseSyllabus(
	// jobList, gradesJBs,
	// courseSyllabusJBs,
	// courseJBs, branchJBs,
	// null, usersGetFromJB);
	// System.out
	// .println("update jobs grades course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// } catch (com.alibaba.fastjson.JSONException e1) {
	// // TODO: handle exception
	// String isEmpty1 = jsonObject1
	// .getString("d");
	// if (isEmpty1.equals("\"Empty\"")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteCourseSyllabusAndUpdateJobsGrades(
	// jobList, gradesJBs);
	// System.out
	// .println("delete course syllabus and update jobs grades "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// }
	// }
	// } catch (com.alibaba.fastjson.JSONException e2) {
	// // TODO: handle exception
	// String isEmpty = jsonObject.getString("d");
	// if (isEmpty.equals("\"Empty\"")) {
	// if (courseSyllabusData == null
	// || courseSyllabusData.equals("")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateJobs(jobList);
	// System.out
	// .println("delete grades course syllabus and update jobs "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// com.alibaba.fastjson.JSONObject jsonObject1 =
	// com.alibaba.fastjson.JSONObject
	// .parseObject(courseSyllabusData);
	// try {
	//
	// com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
	// .parseArray(jsonObject1
	// .getString("d"));
	//
	// Map<String, List<Object>> map = analysisCourseSyllabus(jsonArray);
	//
	// List<Object> courseSyllabusJBs = map
	// .get("courseSyllabusJBs");
	// List<Object> courseJBs = map
	// .get("courseJBs");
	// List<Object> branchJBs = map
	// .get("branchJBs");
	// List<Object> usersGetFromJB = map
	// .get("usersGetFromJB");
	//
	// // compare and update table tb_user
	// List<User> usersInDataBase = userService
	// .getAllUsers();
	// if (usersInDataBase.size() != 1) {
	// List<Object> updatedUsers = new ArrayList<>();
	// List<Object> insertUsers = new ArrayList<>();
	// for (Object user : usersGetFromJB) {
	// boolean flag = false;
	// for (User user2 : usersInDataBase) {
	// if (((User) user)
	// .getUserId()
	// .equals(user2
	// .getUserId())) {
	// updatedUsers.add(user);
	// flag = true;
	// break;
	// }
	// }
	// if (!flag) {
	// insertUsers.add(user);
	// }
	// }
	//
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteGradesAndUpdateJobsCourseSyllabus(
	// jobList,
	// courseSyllabusJBs,
	// courseJBs,
	// branchJBs,
	// updatedUsers,
	// insertUsers);
	// System.out
	// .println("delete grades and update jobs course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteGradesAndUpdateJobsCourseSyllabus(
	// jobList,
	// courseSyllabusJBs,
	// courseJBs,
	// branchJBs, null,
	// usersGetFromJB);
	// System.out
	// .println("delete grades and update jobs course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// } catch (com.alibaba.fastjson.JSONException e1) {
	// // TODO: handle exception
	// String isEmpty1 = jsonObject
	// .getString("d");
	// if (isEmpty1.equals("\"Empty\"")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateJobs(jobList);
	// System.out
	// .println("delete grades course syllabus and update jobs "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// } catch (com.alibaba.fastjson.JSONException e) {
	// // TODO: handle exception
	// String isEmpty5 = jsonObject5.getString("d");
	// if (isEmpty5.equals("\"Empty\"")) {
	// if (gradeData == null || gradeData.equals("")) {
	// if (courseSyllabusData == null
	// || courseSyllabusData.equals("")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteJobsGradesCourseSyllabus();
	// System.out
	// .println("delete jobs grades course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// com.alibaba.fastjson.JSONObject jsonObject =
	// com.alibaba.fastjson.JSONObject
	// .parseObject(courseSyllabusData);
	// try {
	//
	// com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
	// .parseArray(jsonObject
	// .getString("d"));
	//
	// Map<String, List<Object>> map = analysisCourseSyllabus(jsonArray);
	//
	// List<Object> courseSyllabusJBs = map
	// .get("courseSyllabusJBs");
	// List<Object> courseJBs = map
	// .get("courseJBs");
	// List<Object> branchJBs = map
	// .get("branchJBs");
	// List<Object> usersGetFromJB = map
	// .get("usersGetFromJB");
	//
	// // compare and update table tb_user
	// List<User> usersInDataBase = userService
	// .getAllUsers();
	// if (usersInDataBase.size() != 1) {
	// List<Object> updatedUsers = new ArrayList<>();
	// List<Object> insertUsers = new ArrayList<>();
	// for (Object user : usersGetFromJB) {
	// boolean flag = false;
	// for (User user2 : usersInDataBase) {
	// if (((User) user).getUserId()
	// .equals(user2
	// .getUserId())) {
	// updatedUsers.add(user);
	// flag = true;
	// break;
	// }
	// }
	// if (!flag) {
	// insertUsers.add(user);
	// }
	// }
	//
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateCourseSyllabus(
	// courseSyllabusJBs,
	// courseJBs, branchJBs,
	// updatedUsers,
	// insertUsers);
	// System.out
	// .println("delete jobs grades and update course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateCourseSyllabus(
	// courseSyllabusJBs,
	// courseJBs, branchJBs,
	// null, usersGetFromJB);
	// System.out
	// .println("delete jobs grades and update course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// } catch (com.alibaba.fastjson.JSONException e1) {
	// // TODO: handle exception
	// String isEmpty = jsonObject.getString("d");
	// if (isEmpty.equals("\"Empty\"")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteJobsGradesCourseSyllabus();
	// System.out
	// .println("delete jobs grades course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// }
	// }
	// } else {
	// com.alibaba.fastjson.JSONObject jsonObject =
	// com.alibaba.fastjson.JSONObject
	// .parseObject(gradeData);
	// try {
	// com.alibaba.fastjson.JSONArray jsonArray1 =
	// com.alibaba.fastjson.JSONArray
	// .parseArray(jsonObject.getString("d"));
	// List<GradesJB> gradesJBs = analysisGrades(jsonArray1);
	//
	// if (courseSyllabusData == null
	// || courseSyllabusData.equals("")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateGrades(gradesJBs);
	// System.out
	// .println("delete jobs course syllabus and update grades "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// com.alibaba.fastjson.JSONObject jsonObject1 =
	// com.alibaba.fastjson.JSONObject
	// .parseObject(courseSyllabusData);
	// try {
	//
	// com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
	// .parseArray(jsonObject1
	// .getString("d"));
	//
	// Map<String, List<Object>> map = analysisCourseSyllabus(jsonArray);
	//
	// List<Object> courseSyllabusJBs = map
	// .get("courseSyllabusJBs");
	// List<Object> courseJBs = map
	// .get("courseJBs");
	// List<Object> branchJBs = map
	// .get("branchJBs");
	// List<Object> usersGetFromJB = map
	// .get("usersGetFromJB");
	//
	// // compare and update table tb_user
	// List<User> usersInDataBase = userService
	// .getAllUsers();
	// if (usersInDataBase.size() != 1) {
	// List<Object> updatedUsers = new ArrayList<>();
	// List<Object> insertUsers = new ArrayList<>();
	// for (Object user : usersGetFromJB) {
	// boolean flag = false;
	// for (User user2 : usersInDataBase) {
	// if (((User) user)
	// .getUserId()
	// .equals(user2
	// .getUserId())) {
	// updatedUsers.add(user);
	// flag = true;
	// break;
	// }
	// }
	// if (!flag) {
	// insertUsers.add(user);
	// }
	// }
	//
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteJobsAndUpdateGradesCourseSyllabus(
	// gradesJBs,
	// courseSyllabusJBs,
	// courseJBs,
	// branchJBs,
	// updatedUsers,
	// insertUsers);
	// System.out
	// .println("delete jobs and update grades course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteJobsAndUpdateGradesCourseSyllabus(
	// gradesJBs,
	// courseSyllabusJBs,
	// courseJBs,
	// branchJBs, null,
	// usersGetFromJB);
	// System.out
	// .println("delete jobs and update grades course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// } catch (com.alibaba.fastjson.JSONException e1) {
	// // TODO: handle exception
	// String isEmpty1 = jsonObject
	// .getString("d");
	// if (isEmpty1.equals("\"Empty\"")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateGrades(gradesJBs);
	// System.out
	// .println("delete jobs course syllabus and update grades "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// }
	// }
	// } catch (com.alibaba.fastjson.JSONException e2) {
	// // TODO: handle exception
	// String isEmpty = jsonObject.getString("d");
	// if (isEmpty.equals("\"Empty\"")) {
	// if (courseSyllabusData == null
	// || courseSyllabusData.equals("")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteJobsGradesCourseSyllabus();
	// System.out
	// .println("delete jobs grades course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// com.alibaba.fastjson.JSONObject jsonObject1 =
	// com.alibaba.fastjson.JSONObject
	// .parseObject(courseSyllabusData);
	// try {
	//
	// com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
	// .parseArray(jsonObject1
	// .getString("d"));
	//
	// Map<String, List<Object>> map = analysisCourseSyllabus(jsonArray);
	//
	// List<Object> courseSyllabusJBs = map
	// .get("courseSyllabusJBs");
	// List<Object> courseJBs = map
	// .get("courseJBs");
	// List<Object> branchJBs = map
	// .get("branchJBs");
	// List<Object> usersGetFromJB = map
	// .get("usersGetFromJB");
	//
	// // compare and update table
	// // tb_user
	// List<User> usersInDataBase = userService
	// .getAllUsers();
	// if (usersInDataBase.size() != 1) {
	// List<Object> updatedUsers = new ArrayList<>();
	// List<Object> insertUsers = new ArrayList<>();
	// for (Object user : usersGetFromJB) {
	// boolean flag = false;
	// for (User user2 : usersInDataBase) {
	// if (((User) user)
	// .getUserId()
	// .equals(user2
	// .getUserId())) {
	// updatedUsers
	// .add((User) user);
	// flag = true;
	// break;
	// }
	// }
	// if (!flag) {
	// insertUsers
	// .add((User) user);
	// }
	// }
	//
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateCourseSyllabus(
	// courseSyllabusJBs,
	// courseJBs,
	// branchJBs,
	// updatedUsers,
	// insertUsers);
	// System.out
	// .println("delete jobs grades and update course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// } else {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteOthersAndUpdateCourseSyllabus(
	// courseSyllabusJBs,
	// courseJBs,
	// branchJBs,
	// null,
	// usersGetFromJB);
	// System.out
	// .println("delete jobs grades and update course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// } catch (com.alibaba.fastjson.JSONException e1) {
	// // TODO: handle exception
	// String isEmpty1 = jsonObject
	// .getString("d");
	// if (isEmpty1.equals("\"Empty\"")) {
	// boolean flag = jobsGradesCourseSyllabusService
	// .deleteJobsGradesCourseSyllabus();
	// System.out
	// .println("delete jobs grades course syllabus "
	// + (flag == true ? "success"
	// : "fail"));
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// }
	// }
	//
	// private List<JobJB> analysisJobs(JSONArray jsonArray) {
	// List<JobJB> jobList = new ArrayList<>();
	// JobJB job = null;
	//
	// for (int i = 0; i < jsonArray.size(); i++) {
	// String jobid = jsonArray.getJSONObject(i).getString("ID");
	// String title = jsonArray.getJSONObject(i).getString("Title");
	// String company = jsonArray.getJSONObject(i).getString("Company");
	// String area = jsonArray.getJSONObject(i).getString("Area");
	// String domain = jsonArray.getJSONObject(i).getString("Domain");
	// String experience = jsonArray.getJSONObject(i).getString(
	// "Experience");
	// String text = jsonArray.getJSONObject(i).getString("Text");
	// String requirements = jsonArray.getJSONObject(i).getString(
	// "Requirements");
	// String email = jsonArray.getJSONObject(i).getString("Email");
	//
	// job = new JobJB(jobid, title, company, area, domain, experience,
	// text, requirements, email);
	//
	// jobList.add(job);
	// }
	//
	// return jobList;
	// }
	//
	// private List<GradesJB> analysisGrades(JSONArray jsonArray) {
	// List<GradesJB> gradesJBs = new ArrayList<>();
	//
	// GradesJB gradesJB = null;
	//
	// for (int i = 0; i < jsonArray.size(); i++) {
	// String id = jsonArray.getJSONObject(i).getString("ID");
	// String studentId = jsonArray.getJSONObject(i)
	// .getString("StudentID");
	// String courseNumber = jsonArray.getJSONObject(i).getString(
	// "CourseNumber");
	// String cycleNumber = jsonArray.getJSONObject(i).getString(
	// "CycleNumber");
	// String subject = jsonArray.getJSONObject(i).getString("Subject");
	// String points = jsonArray.getJSONObject(i).getString("Points");
	//
	// gradesJB = new GradesJB(id, Integer.parseInt(studentId),
	// courseNumber, cycleNumber, subject, points);
	//
	// gradesJBs.add(gradesJB);
	// }
	// return gradesJBs;
	// }
	//
	// private Map<String, List<Object>> analysisCourseSyllabus(JSONArray
	// jsonArray) {
	// Map<String, List<Object>> map = new HashMap<>();
	// List<Object> courseSyllabusJBs = new ArrayList<>();
	// List<Object> courseJBs = new ArrayList<>();
	// List<Object> branchJBs = new ArrayList<>();
	// List<Object> usersGetFromJB = new ArrayList<>();
	// CourseSyllabusJB courseSyllabusJB = null;
	// CourseJB courseJB = null;
	// BranchJB branchJB = null;
	//
	// for (int i = 0; i < jsonArray.size(); i++) {
	// // course syllabus
	// String userId = jsonArray.getJSONObject(i).getString("ID") == null ?
	// "null"
	// : jsonArray.getJSONObject(i).getString("ID");
	// String meetDate = jsonArray.getJSONObject(i).getString("MeetDate") ==
	// null ? "null"
	// : jsonArray.getJSONObject(i).getString("MeetDate");
	// String meetStart = jsonArray.getJSONObject(i)
	// .getString("MeetStart") == null ? "null" : jsonArray
	// .getJSONObject(i).getString("MeetStart");
	// String meetEnd = jsonArray.getJSONObject(i).getString("MeetEnd") == null
	// ? "null"
	// : jsonArray.getJSONObject(i).getString("MeetEnd");
	// String meetNum = jsonArray.getJSONObject(i).getString("MeetNum") == null
	// ? "null"
	// : jsonArray.getJSONObject(i).getString("MeetNum");
	// String meetBranch = jsonArray.getJSONObject(i).getString(
	// "MeetBranch") == null ? "null" : jsonArray.getJSONObject(i)
	// .getString("MeetBranch");
	// String meetBuildingNum = jsonArray.getJSONObject(i).getString(
	// "MeetBuildingNum") == null ? "null" : jsonArray
	// .getJSONObject(i).getString("MeetBuildingNum");
	// String meetBuildingName = jsonArray.getJSONObject(i).getString(
	// "MeetBuildingName") == null ? "null" : jsonArray
	// .getJSONObject(i).getString("MeetBuildingName");
	// String meetClassNum = jsonArray.getJSONObject(i).getString(
	// "MeetClassNum") == null ? "null" : jsonArray.getJSONObject(
	// i).getString("MeetClassNum");
	// String meetTeacher = jsonArray.getJSONObject(i).getString(
	// "MeetTeacher") == null ? "null" : jsonArray
	// .getJSONObject(i).getString("MeetTeacher");
	// String meetStatus = jsonArray.getJSONObject(i).getString(
	// "MeetStatus") == null ? "null" : jsonArray.getJSONObject(i)
	// .getString("MeetStatus");
	// String meetTopic = jsonArray.getJSONObject(i)
	// .getString("MeetTopic") == null ? "null" : jsonArray
	// .getJSONObject(i).getString("MeetTopic");
	// String meetClassName = jsonArray.getJSONObject(i).getString(
	// "MeetClassName") == null ? "null" : jsonArray
	// .getJSONObject(i).getString("MeetClassName");
	//
	// courseSyllabusJB = new CourseSyllabusJB();
	// courseSyllabusJB.setUserId(userId);
	// courseSyllabusJB.setMeetDate(meetDate);
	// courseSyllabusJB.setMeetStart(meetStart);
	// courseSyllabusJB.setMeetEnd(meetEnd);
	// courseSyllabusJB.setMeetNum(meetNum);
	// courseSyllabusJB.setMeetBranch(meetBranch);
	// courseSyllabusJB.setMeetBuildingNum(meetBuildingNum);
	// courseSyllabusJB.setMeetBuildingName(meetBuildingName);
	// courseSyllabusJB.setMeetClassNum(meetClassNum);
	// courseSyllabusJB.setMeetTeacher(meetTeacher);
	// courseSyllabusJB.setMeetStatus(meetStatus);
	// courseSyllabusJB.setMeetTopic(meetTopic);
	// courseSyllabusJB.setMeetClassName(meetClassName);
	//
	// // course
	// String courseName = jsonArray.getJSONObject(i).getString(
	// "CourseName") == null ? "null" : jsonArray.getJSONObject(i)
	// .getString("CourseName");
	// String courseNum = jsonArray.getJSONObject(i).getString(
	// "CourseNumber") == null ? "null" : jsonArray.getJSONObject(
	// i).getString("CourseNumber");
	// String cycleNum = jsonArray.getJSONObject(i).getString(
	// "CycleNumber") == null ? "null" : jsonArray
	// .getJSONObject(i).getString("CycleNumber");
	// String cycleOpenDate = jsonArray.getJSONObject(i).getString(
	// "CycleOpenDate") == null ? "null" : jsonArray
	// .getJSONObject(i).getString("CycleOpenDate");
	// String domainNumber = jsonArray.getJSONObject(i).getString(
	// "DomainNumber") == null ? "null" : jsonArray.getJSONObject(
	// i).getString("DomainNumber");
	// String domainName = jsonArray.getJSONObject(i).getString(
	// "DomainName") == null ? "null" : jsonArray.getJSONObject(i)
	// .getString("DomainName");
	//
	// courseJB = new CourseJB();
	// courseJB.setCourseName(courseName);
	// courseJB.setCourseNum(courseNum);
	// courseJB.setCycleNum(cycleNum);
	// courseJB.setCycleOpenDate(cycleOpenDate);
	// courseJB.setDomainNumber(domainNumber);
	// courseJB.setDomainName(domainName);
	// courseJB.setUserId(userId);
	//
	// // branch
	// String branch = jsonArray.getJSONObject(i).getString("Branch") == null ?
	// "null"
	// : jsonArray.getJSONObject(i).getString("Branch");
	//
	// branchJB = new BranchJB();
	// branchJB.setBranch(branch);
	// branchJB.setUserId(userId);
	//
	// courseSyllabusJBs.add(courseSyllabusJB);
	//
	// courseJBs.add(courseJB);
	//
	// // judge weather user's
	// // branch is
	// // existing
	// boolean isBranchExist = false;
	// if (branchJBs.size() == 0) {
	// branchJBs.add(branchJB);
	// } else {
	// for (Object branchJB2 : branchJBs) {
	// if (((BranchJB) branchJB2).getBranch().equals(
	// branchJB.getBranch())
	// && ((BranchJB) branchJB2).getUserId().equals(
	// branchJB.getUserId())) {
	// isBranchExist = true;
	// break;
	// }
	// }
	// if (!isBranchExist) {
	// branchJBs.add(branchJB);
	// }
	// }
	//
	// // user
	// String firstName = jsonArray.getJSONObject(i)
	// .getString("FirstName") == null ? "null" : jsonArray
	// .getJSONObject(i).getString("FirstName");
	// String lastName = jsonArray.getJSONObject(i).getString("LastName") ==
	// null ? "null"
	// : jsonArray.getJSONObject(i).getString("LastName");
	// String email = jsonArray.getJSONObject(i).getString("Email") == null ?
	// "null"
	// : jsonArray.getJSONObject(i).getString("Email");
	//
	// // judge weather user is existing
	// boolean isUserExist = false;
	// User user = new User(userId, firstName, lastName, "000000", email,
	// 0, 0);
	// if (usersGetFromJB.size() == 0) {
	// usersGetFromJB.add(user);
	// } else {
	// for (Object user2 : usersGetFromJB) {
	// if (user.getUserId().equals(((User) user2).getUserId())) {
	// isUserExist = true;
	// break;
	// }
	// }
	// if (!isUserExist) {
	// usersGetFromJB.add(user);
	// }
	// }
	// }
	//
	// map.put("courseSyllabusJBs", courseSyllabusJBs);
	// map.put("courseJBs", courseJBs);
	// map.put("branchJBs", branchJBs);
	// map.put("usersGetFromJB", usersGetFromJB);
	// return map;
	// }
}

class MyCallable implements Callable<String> {
	private String dataURL;

	public MyCallable(String dataURL) {
		super();
		this.dataURL = dataURL;
	}

	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		String result = GetDataFromWeb.doHttpGet(dataURL, 1);
		return result;
	}

}