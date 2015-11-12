package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.dbutils.QueryRunner;

import com.matrix.jbt.dao.JobsGradesCourseSyllabusDao;
import com.matrix.jbt.entity.BranchJB;
import com.matrix.jbt.entity.CourseJB;
import com.matrix.jbt.entity.CourseSyllabusJB;
import com.matrix.jbt.entity.GradesJB;
import com.matrix.jbt.entity.JobJB;
import com.matrix.jbt.entity.User;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class JobsGradesCourseSyllabusDaoImpl implements
		JobsGradesCourseSyllabusDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public boolean updateJobsGradesCourseSyllabus(List<JobJB> jobList,
			List<GradesJB> gradesJBs, List<Object> courseSyllabusJBs,
			List<Object> courseJBs, List<Object> branchJBs,
			List<Object> updatedUsers, List<Object> usersGetFromJB) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			int ret1 = -1;
			int ret2 = -1;
			int ret3 = -1;
			int ret4 = -1;
			int ret5 = -1;
			int ret6 = -1;
			int ret7 = -1;
			int ret8 = -1;
			int ret9 = -1;
			int ret10 = -1;
			int ret11 = -1;
			int ret12 = -1;
			int ret13 = -1;
			int ret14 = -1;
			int ret15 = -1;

			ret = runner.update(conn, ReadProperties.read("sql", "deleteJob"));
			ret1 = runner
					.update(conn, ReadProperties.read("sql", "deleteArea"));
			ret2 = runner.update(conn,
					ReadProperties.read("sql", "deleteDomain"));

			ret3 = runner.update(conn,
					ReadProperties.read("sql", "deleteGrades"));

			ret4 = runner.update(conn,
					ReadProperties.read("sql", "deleteCourseSyllabus"));
			ret5 = runner.update(conn,
					ReadProperties.read("sql", "deleteCourse"));
			ret6 = runner.update(conn,
					ReadProperties.read("sql", "deleteBranch"));

			Map<String, String> mapAreas = new HashMap<>();
			Map<String, String> mapDomains = new HashMap<>();

			if (jobList.size() > 0) {
				for (JobJB job : jobList) {
					ret7 = runner.update(conn,
							ReadProperties.read("sql", "addJob"),
							job.getJobId(), job.getTitle(), job.getCompany(),
							job.getArea(), job.getDomain(),
							job.getExperience(), job.getText(),
							job.getRequirements(), job.getEmail());

					mapAreas.put(job.getArea(), job.getArea());
					mapDomains.put(job.getDomain(), job.getDomain());
				}
			} else {
				ret7 = 0;
			}

			if (mapAreas.size() > 0) {
				for (Entry<String, String> entry : mapAreas.entrySet()) {
					ret8 = runner.update(conn,
							ReadProperties.read("sql", "addArea"),
							entry.getValue());
				}
			} else {
				ret8 = 0;
			}

			if (mapDomains.size() > 0) {
				for (Entry<String, String> entry : mapDomains.entrySet()) {
					ret9 = runner.update(conn,
							ReadProperties.read("sql", "addDomain"),
							entry.getValue());
				}
			} else {
				ret9 = 0;
			}

			if (gradesJBs.size() > 0) {
				for (GradesJB gradesJB : gradesJBs) {
					ret10 = runner.update(conn,
							ReadProperties.read("sql", "addGrades"),
							gradesJB.getId(), gradesJB.getStudentId(),
							gradesJB.getCourseNumber(),
							gradesJB.getCycleNumber(), gradesJB.getSubject(),
							gradesJB.getPoints());
				}
			} else {
				ret10 = 0;
			}

			if (courseSyllabusJBs.size() > 0) {
				for (Object courseSyllabusJB : courseSyllabusJBs) {
					ret11 = runner
							.update(conn, ReadProperties.read("sql",
									"addCourseSyllabus"),
									((CourseSyllabusJB) courseSyllabusJB)
											.getUserId(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetDate(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetStart(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetEnd(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetNum(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetBranch(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetBuildingNum(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetBuildingName(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetClassNum(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetTeacher(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetStatus(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetTopic(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetClassName());
				}
			} else {
				ret11 = 0;
			}

			if (courseJBs.size() > 0) {
				for (Object courseJB : courseJBs) {
					ret12 = runner.update(conn,
							ReadProperties.read("sql", "addCourse"),
							((CourseJB) courseJB).getCourseName(),
							((CourseJB) courseJB).getCourseNum(),
							((CourseJB) courseJB).getCycleNum(),
							((CourseJB) courseJB).getCycleOpenDate(),
							((CourseJB) courseJB).getDomainNumber(),
							((CourseJB) courseJB).getDomainName(),
							((CourseJB) courseJB).getUserId());
				}
			} else {
				ret12 = 0;
			}

			if (branchJBs.size() > 0) {
				for (Object branchJB : branchJBs) {
					ret13 = runner.update(conn,
							ReadProperties.read("sql", "addBranch"),
							((BranchJB) branchJB).getBranch(),
							((BranchJB) branchJB).getUserId());
				}
			} else {
				ret13 = 0;
			}

			if (updatedUsers == null) {
				ret14 = 0;
			} else {
				if (updatedUsers.size() > 0) {
					for (Object user : updatedUsers) {
						ret14 = runner.update(conn,
								ReadProperties.read("sql", "updateUser"),
								((User) user).getFirstName(),
								((User) user).getLastName(),
								((User) user).getEmail(),
								((User) user).getUserId());
					}
				} else {
					ret14 = 0;
				}

			}

			if (usersGetFromJB.size() > 0) {
				for (Object user : usersGetFromJB) {
					ret15 = runner.update(conn,
							ReadProperties.read("sql", "insertUser"),
							((User) user).getUserId(),
							((User) user).getFirstName(),
							((User) user).getLastName(),
							((User) user).getPassword(),
							((User) user).getEmail(), ((User) user).getFlag(),
							((User) user).getStatus());
				}
			} else {
				ret15 = 0;
			}

			if (ret >= 0 && ret1 >= 0 && ret2 >= 0 && ret3 >= 0 && ret4 >= 0
					&& ret5 >= 0 && ret6 >= 0 && ret7 >= 0 && ret8 >= 0
					&& ret9 >= 0 && ret10 >= 0 && ret11 >= 0 && ret12 >= 0
					&& ret13 >= 0 && ret14 >= 0 && ret15 >= 0) {
				conn.commit();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
				return false;
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return false;
	}

	@Override
	public boolean deleteJobsGradesCourseSyllabus() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			int ret1 = -1;
			int ret2 = -1;
			int ret3 = -1;
			int ret4 = -1;
			int ret5 = -1;
			int ret6 = -1;
			ret = runner.update(conn, ReadProperties.read("sql", "deleteJob"));
			ret1 = runner
					.update(conn, ReadProperties.read("sql", "deleteArea"));
			ret2 = runner.update(conn,
					ReadProperties.read("sql", "deleteDomain"));

			ret3 = runner.update(conn,
					ReadProperties.read("sql", "deleteGrades"));

			ret4 = runner.update(conn,
					ReadProperties.read("sql", "deleteCourseSyllabus"));
			ret5 = runner.update(conn,
					ReadProperties.read("sql", "deleteCourse"));
			ret6 = runner.update(conn,
					ReadProperties.read("sql", "deleteBranch"));

			if (ret >= 0 && ret1 >= 0 && ret2 >= 0 && ret3 >= 0 && ret4 >= 0
					&& ret5 >= 0 && ret6 >= 0) {
				conn.commit();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return false;
	}

	@Override
	public boolean deleteOthersAndUpdateCourseSyllabus(
			List<Object> courseSyllabusJBs, List<Object> courseJBs,
			List<Object> branchJBs, List<Object> updatedUsers,
			List<Object> usersGetFromJB) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			int ret1 = -1;
			int ret2 = -1;
			int ret3 = -1;
			int ret4 = -1;
			int ret5 = -1;
			int ret6 = -1;
			int ret7 = -1;
			int ret8 = -1;
			int ret9 = -1;
			int ret10 = -1;
			int ret11 = -1;

			ret = runner.update(conn,
					ReadProperties.read("sql", "deleteCourseSyllabus"));
			ret1 = runner.update(conn,
					ReadProperties.read("sql", "deleteCourse"));
			ret2 = runner.update(conn,
					ReadProperties.read("sql", "deleteBranch"));

			ret8 = runner.update(conn, ReadProperties.read("sql", "deleteJob"));
			ret9 = runner
					.update(conn, ReadProperties.read("sql", "deleteArea"));
			ret10 = runner.update(conn,
					ReadProperties.read("sql", "deleteDomain"));

			ret11 = runner.update(conn,
					ReadProperties.read("sql", "deleteGrades"));

			if (courseSyllabusJBs.size() > 0) {
				for (Object courseSyllabusJB : courseSyllabusJBs) {
					ret3 = runner
							.update(conn, ReadProperties.read("sql",
									"addCourseSyllabus"),
									((CourseSyllabusJB) courseSyllabusJB)
											.getUserId(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetDate(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetStart(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetEnd(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetNum(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetBranch(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetBuildingNum(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetBuildingName(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetClassNum(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetTeacher(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetStatus(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetTopic(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetClassName());
				}
			} else {
				ret3 = 0;
			}

			if (courseJBs.size() > 0) {
				for (Object courseJB : courseJBs) {
					ret4 = runner.update(conn,
							ReadProperties.read("sql", "addCourse"),
							((CourseJB) courseJB).getCourseName(),
							((CourseJB) courseJB).getCourseNum(),
							((CourseJB) courseJB).getCycleNum(),
							((CourseJB) courseJB).getCycleOpenDate(),
							((CourseJB) courseJB).getDomainNumber(),
							((CourseJB) courseJB).getDomainName(),
							((CourseJB) courseJB).getUserId());
				}
			} else {
				ret4 = 0;
			}

			if (branchJBs.size() > 0) {
				for (Object branchJB : branchJBs) {
					ret5 = runner.update(conn,
							ReadProperties.read("sql", "addBranch"),
							((BranchJB) branchJB).getBranch(),
							((BranchJB) branchJB).getUserId());
				}
			} else {
				ret5 = 0;
			}

			if (updatedUsers == null) {
				ret6 = 0;
			} else {
				if (updatedUsers.size() > 0) {
					for (Object user : updatedUsers) {
						ret6 = runner.update(conn,
								ReadProperties.read("sql", "updateUser"),
								((User) user).getFirstName(),
								((User) user).getLastName(),
								((User) user).getEmail(),
								((User) user).getUserId());
					}
				} else {
					ret6 = 0;
				}

			}

			if (usersGetFromJB.size() > 0) {
				for (Object user : usersGetFromJB) {
					ret7 = runner.update(conn,
							ReadProperties.read("sql", "insertUser"),
							((User) user).getUserId(),
							((User) user).getFirstName(),
							((User) user).getLastName(),
							((User) user).getPassword(),
							((User) user).getEmail(), ((User) user).getFlag(),
							((User) user).getStatus());
				}
			} else {
				ret7 = 0;
			}

			if (ret >= 0 && ret1 >= 0 && ret2 >= 0 && ret3 >= 0 && ret4 >= 0
					&& ret5 >= 0 && ret6 >= 0 && ret7 >= 0 && ret8 >= 0
					&& ret9 >= 0 && ret10 >= 0 && ret11 >= 0) {
				conn.commit();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
				return false;
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return false;
	}

	@Override
	public boolean deleteOthersAndUpdateGrades(List<GradesJB> gradesJBs) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			int ret1 = -1;
			int ret2 = -1;
			int ret3 = -1;
			int ret4 = -1;
			int ret5 = -1;
			int ret6 = -1;
			int ret7 = -1;

			ret = runner.update(conn,
					ReadProperties.read("sql", "deleteCourseSyllabus"));
			ret1 = runner.update(conn,
					ReadProperties.read("sql", "deleteCourse"));
			ret2 = runner.update(conn,
					ReadProperties.read("sql", "deleteBranch"));

			ret3 = runner.update(conn, ReadProperties.read("sql", "deleteJob"));
			ret4 = runner
					.update(conn, ReadProperties.read("sql", "deleteArea"));
			ret5 = runner.update(conn,
					ReadProperties.read("sql", "deleteDomain"));

			ret6 = runner.update(conn,
					ReadProperties.read("sql", "deleteGrades"));

			if (gradesJBs.size() > 0) {
				for (GradesJB gradesJB : gradesJBs) {
					ret7 = runner.update(conn,
							ReadProperties.read("sql", "addGrades"),
							gradesJB.getId(), gradesJB.getStudentId(),
							gradesJB.getCourseNumber(),
							gradesJB.getCycleNumber(), gradesJB.getSubject(),
							gradesJB.getPoints());
				}
			} else {
				ret7 = 0;
			}
			if (ret >= 0 && ret1 >= 0 && ret2 >= 0 && ret3 >= 0 && ret4 >= 0
					&& ret5 >= 0 && ret6 >= 0 && ret7 >= 0) {
				conn.commit();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
				return false;
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return false;
	}

	@Override
	public boolean deleteJobsAndUpdateGradesCourseSyllabus(
			List<GradesJB> gradesJBs, List<Object> courseSyllabusJBs,
			List<Object> courseJBs, List<Object> branchJBs,
			List<Object> updatedUsers, List<Object> usersGetFromJB) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			int ret1 = -1;
			int ret2 = -1;
			int ret3 = -1;
			int ret4 = -1;
			int ret5 = -1;
			int ret6 = -1;
			int ret7 = -1;
			int ret8 = -1;
			int ret9 = -1;
			int ret10 = -1;
			int ret11 = -1;
			int ret12 = -1;

			ret = runner.update(conn,
					ReadProperties.read("sql", "deleteCourseSyllabus"));
			ret1 = runner.update(conn,
					ReadProperties.read("sql", "deleteCourse"));
			ret2 = runner.update(conn,
					ReadProperties.read("sql", "deleteBranch"));

			ret3 = runner.update(conn, ReadProperties.read("sql", "deleteJob"));
			ret4 = runner
					.update(conn, ReadProperties.read("sql", "deleteArea"));
			ret5 = runner.update(conn,
					ReadProperties.read("sql", "deleteDomain"));

			ret6 = runner.update(conn,
					ReadProperties.read("sql", "deleteGrades"));

			if (gradesJBs.size() > 0) {
				for (GradesJB gradesJB : gradesJBs) {
					ret7 = runner.update(conn,
							ReadProperties.read("sql", "addGrades"),
							gradesJB.getId(), gradesJB.getStudentId(),
							gradesJB.getCourseNumber(),
							gradesJB.getCycleNumber(), gradesJB.getSubject(),
							gradesJB.getPoints());
				}
			} else {
				ret7 = 0;
			}

			if (courseSyllabusJBs.size() > 0) {
				for (Object courseSyllabusJB : courseSyllabusJBs) {
					ret8 = runner
							.update(conn, ReadProperties.read("sql",
									"addCourseSyllabus"),
									((CourseSyllabusJB) courseSyllabusJB)
											.getUserId(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetDate(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetStart(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetEnd(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetNum(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetBranch(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetBuildingNum(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetBuildingName(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetClassNum(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetTeacher(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetStatus(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetTopic(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetClassName());
				}
			} else {
				ret8 = 0;
			}

			if (courseJBs.size() > 0) {
				for (Object courseJB : courseJBs) {
					ret9 = runner.update(conn,
							ReadProperties.read("sql", "addCourse"),
							((CourseJB) courseJB).getCourseName(),
							((CourseJB) courseJB).getCourseNum(),
							((CourseJB) courseJB).getCycleNum(),
							((CourseJB) courseJB).getCycleOpenDate(),
							((CourseJB) courseJB).getDomainNumber(),
							((CourseJB) courseJB).getDomainName(),
							((CourseJB) courseJB).getUserId());
				}
			} else {
				ret9 = 0;
			}

			if (branchJBs.size() > 0) {
				for (Object branchJB : branchJBs) {
					ret10 = runner.update(conn,
							ReadProperties.read("sql", "addBranch"),
							((BranchJB) branchJB).getBranch(),
							((BranchJB) branchJB).getUserId());
				}
			} else {
				ret10 = 0;
			}

			if (updatedUsers == null) {
				ret11 = 0;
			} else {
				if (updatedUsers.size() > 0) {
					for (Object user : updatedUsers) {
						ret11 = runner.update(conn,
								ReadProperties.read("sql", "updateUser"),
								((User) user).getFirstName(),
								((User) user).getLastName(),
								((User) user).getEmail(),
								((User) user).getUserId());
					}
				} else {
					ret11 = 0;
				}

			}

			if (usersGetFromJB.size() > 0) {
				for (Object user : usersGetFromJB) {
					ret12 = runner.update(conn,
							ReadProperties.read("sql", "insertUser"),
							((User) user).getUserId(),
							((User) user).getFirstName(),
							((User) user).getLastName(),
							((User) user).getPassword(),
							((User) user).getEmail(), ((User) user).getFlag(),
							((User) user).getStatus());
				}
			} else {
				ret12 = 0;
			}

			if (ret >= 0 && ret1 >= 0 && ret2 >= 0 && ret3 >= 0 && ret4 >= 0
					&& ret5 >= 0 && ret6 >= 0 && ret7 >= 0 && ret8 >= 0
					&& ret9 >= 0 && ret10 >= 0 && ret11 >= 0 && ret12 >= 0) {
				conn.commit();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
				return false;
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return false;
	}

	@Override
	public boolean deleteOthersAndUpdateJobs(List<JobJB> jobList) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			int ret1 = -1;
			int ret2 = -1;
			int ret3 = -1;
			int ret4 = -1;
			int ret5 = -1;
			int ret6 = -1;
			int ret7 = -1;
			int ret8 = -1;
			int ret9 = -1;

			ret = runner.update(conn,
					ReadProperties.read("sql", "deleteCourseSyllabus"));
			ret1 = runner.update(conn,
					ReadProperties.read("sql", "deleteCourse"));
			ret2 = runner.update(conn,
					ReadProperties.read("sql", "deleteBranch"));

			ret3 = runner.update(conn, ReadProperties.read("sql", "deleteJob"));
			ret4 = runner
					.update(conn, ReadProperties.read("sql", "deleteArea"));
			ret5 = runner.update(conn,
					ReadProperties.read("sql", "deleteDomain"));

			ret6 = runner.update(conn,
					ReadProperties.read("sql", "deleteGrades"));

			Map<String, String> mapAreas = new HashMap<>();
			Map<String, String> mapDomains = new HashMap<>();

			if (jobList.size() > 0) {
				for (JobJB job : jobList) {
					ret7 = runner.update(conn,
							ReadProperties.read("sql", "addJob"),
							job.getJobId(), job.getTitle(), job.getCompany(),
							job.getArea(), job.getDomain(),
							job.getExperience(), job.getText(),
							job.getRequirements(), job.getEmail());

					mapAreas.put(job.getArea(), job.getArea());
					mapDomains.put(job.getDomain(), job.getDomain());
				}
			} else {
				ret7 = 0;
			}

			if (mapAreas.size() > 0) {
				for (Entry<String, String> entry : mapAreas.entrySet()) {
					ret8 = runner.update(conn,
							ReadProperties.read("sql", "addArea"),
							entry.getValue());
				}
			} else {
				ret8 = 0;
			}

			if (mapDomains.size() > 0) {
				for (Entry<String, String> entry : mapDomains.entrySet()) {
					ret9 = runner.update(conn,
							ReadProperties.read("sql", "addDomain"),
							entry.getValue());
				}
			} else {
				ret9 = 0;
			}

			if (ret >= 0 && ret1 >= 0 && ret2 >= 0 && ret3 >= 0 && ret4 >= 0
					&& ret5 >= 0 && ret6 >= 0 && ret7 >= 0 && ret8 >= 0
					&& ret9 >= 0) {
				conn.commit();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
				return false;
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return false;
	}

	@Override
	public boolean deleteGradesAndUpdateJobsCourseSyllabus(List<JobJB> jobList,
			List<Object> courseSyllabusJBs, List<Object> courseJBs,
			List<Object> branchJBs, List<Object> updatedUsers,
			List<Object> usersGetFromJB) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			int ret1 = -1;
			int ret2 = -1;
			int ret3 = -1;
			int ret4 = -1;
			int ret5 = -1;
			int ret6 = -1;
			int ret7 = -1;
			int ret8 = -1;
			int ret9 = -1;
			int ret10 = -1;
			int ret11 = -1;
			int ret12 = -1;
			int ret13 = -1;
			int ret14 = -1;

			ret = runner.update(conn,
					ReadProperties.read("sql", "deleteCourseSyllabus"));
			ret1 = runner.update(conn,
					ReadProperties.read("sql", "deleteCourse"));
			ret2 = runner.update(conn,
					ReadProperties.read("sql", "deleteBranch"));

			ret3 = runner.update(conn, ReadProperties.read("sql", "deleteJob"));
			ret4 = runner
					.update(conn, ReadProperties.read("sql", "deleteArea"));
			ret5 = runner.update(conn,
					ReadProperties.read("sql", "deleteDomain"));

			ret6 = runner.update(conn,
					ReadProperties.read("sql", "deleteGrades"));

			Map<String, String> mapAreas = new HashMap<>();
			Map<String, String> mapDomains = new HashMap<>();

			if (jobList.size() > 0) {
				for (JobJB job : jobList) {
					ret7 = runner.update(conn,
							ReadProperties.read("sql", "addJob"),
							job.getJobId(), job.getTitle(), job.getCompany(),
							job.getArea(), job.getDomain(),
							job.getExperience(), job.getText(),
							job.getRequirements(), job.getEmail());

					mapAreas.put(job.getArea(), job.getArea());
					mapDomains.put(job.getDomain(), job.getDomain());
				}
			} else {
				ret7 = 0;
			}

			if (mapAreas.size() > 0) {
				for (Entry<String, String> entry : mapAreas.entrySet()) {
					ret8 = runner.update(conn,
							ReadProperties.read("sql", "addArea"),
							entry.getValue());
				}
			} else {
				ret8 = 0;
			}

			if (mapDomains.size() > 0) {
				for (Entry<String, String> entry : mapDomains.entrySet()) {
					ret9 = runner.update(conn,
							ReadProperties.read("sql", "addDomain"),
							entry.getValue());
				}
			} else {
				ret9 = 0;
			}

			if (courseSyllabusJBs.size() > 0) {
				for (Object courseSyllabusJB : courseSyllabusJBs) {
					ret10 = runner
							.update(conn, ReadProperties.read("sql",
									"addCourseSyllabus"),
									((CourseSyllabusJB) courseSyllabusJB)
											.getUserId(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetDate(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetStart(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetEnd(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetNum(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetBranch(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetBuildingNum(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetBuildingName(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetClassNum(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetTeacher(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetStatus(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetTopic(),
									((CourseSyllabusJB) courseSyllabusJB)
											.getMeetClassName());
				}
			} else {
				ret10 = 0;
			}

			if (courseJBs.size() > 0) {
				for (Object courseJB : courseJBs) {
					ret11 = runner.update(conn,
							ReadProperties.read("sql", "addCourse"),
							((CourseJB) courseJB).getCourseName(),
							((CourseJB) courseJB).getCourseNum(),
							((CourseJB) courseJB).getCycleNum(),
							((CourseJB) courseJB).getCycleOpenDate(),
							((CourseJB) courseJB).getDomainNumber(),
							((CourseJB) courseJB).getDomainName(),
							((CourseJB) courseJB).getUserId());
				}
			} else {
				ret11 = 0;
			}

			if (branchJBs.size() > 0) {
				for (Object branchJB : branchJBs) {
					ret12 = runner.update(conn,
							ReadProperties.read("sql", "addBranch"),
							((BranchJB) branchJB).getBranch(),
							((BranchJB) branchJB).getUserId());
				}
			} else {
				ret12 = 0;
			}

			if (updatedUsers == null) {
				ret13 = 0;
			} else {
				if (updatedUsers.size() > 0) {
					for (Object user : updatedUsers) {
						ret13 = runner.update(conn,
								ReadProperties.read("sql", "updateUser"),
								((User) user).getFirstName(),
								((User) user).getLastName(),
								((User) user).getEmail(),
								((User) user).getUserId());
					}
				} else {
					ret13 = 0;
				}

			}

			if (usersGetFromJB.size() > 0) {
				for (Object user : usersGetFromJB) {
					ret14 = runner.update(conn,
							ReadProperties.read("sql", "insertUser"),
							((User) user).getUserId(),
							((User) user).getFirstName(),
							((User) user).getLastName(),
							((User) user).getPassword(),
							((User) user).getEmail(), ((User) user).getFlag(),
							((User) user).getStatus());
				}
			} else {
				ret14 = 0;
			}

			if (ret >= 0 && ret1 >= 0 && ret2 >= 0 && ret3 >= 0 && ret4 >= 0
					&& ret5 >= 0 && ret6 >= 0 && ret7 >= 0 && ret8 >= 0
					&& ret9 >= 0 && ret10 >= 0 && ret11 >= 0 && ret12 >= 0
					&& ret13 >= 0 && ret14 >= 0) {
				conn.commit();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
				return false;
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return false;
	}

	@Override
	public boolean deleteCourseSyllabusAndUpdateJobsGrades(List<JobJB> jobList,
			List<GradesJB> gradesJBs) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			int ret1 = -1;
			int ret2 = -1;
			int ret3 = -1;
			int ret4 = -1;
			int ret5 = -1;
			int ret6 = -1;
			int ret7 = -1;
			int ret8 = -1;
			int ret9 = -1;
			int ret10 = -1;

			ret = runner.update(conn,
					ReadProperties.read("sql", "deleteCourseSyllabus"));
			ret1 = runner.update(conn,
					ReadProperties.read("sql", "deleteCourse"));
			ret2 = runner.update(conn,
					ReadProperties.read("sql", "deleteBranch"));

			ret3 = runner.update(conn, ReadProperties.read("sql", "deleteJob"));
			ret4 = runner
					.update(conn, ReadProperties.read("sql", "deleteArea"));
			ret5 = runner.update(conn,
					ReadProperties.read("sql", "deleteDomain"));

			ret6 = runner.update(conn,
					ReadProperties.read("sql", "deleteGrades"));

			Map<String, String> mapAreas = new HashMap<>();
			Map<String, String> mapDomains = new HashMap<>();

			if (jobList.size() > 0) {
				for (JobJB job : jobList) {
					ret7 = runner.update(conn,
							ReadProperties.read("sql", "addJob"),
							job.getJobId(), job.getTitle(), job.getCompany(),
							job.getArea(), job.getDomain(),
							job.getExperience(), job.getText(),
							job.getRequirements(), job.getEmail());

					mapAreas.put(job.getArea(), job.getArea());
					mapDomains.put(job.getDomain(), job.getDomain());
				}
			} else {
				ret7 = 0;
			}

			if (mapAreas.size() > 0) {
				for (Entry<String, String> entry : mapAreas.entrySet()) {
					ret8 = runner.update(conn,
							ReadProperties.read("sql", "addArea"),
							entry.getValue());
				}
			} else {
				ret8 = 0;
			}

			if (mapDomains.size() > 0) {
				for (Entry<String, String> entry : mapDomains.entrySet()) {
					ret9 = runner.update(conn,
							ReadProperties.read("sql", "addDomain"),
							entry.getValue());
				}
			} else {
				ret9 = 0;
			}

			if (gradesJBs.size() > 0) {
				for (GradesJB gradesJB : gradesJBs) {
					ret10 = runner.update(conn,
							ReadProperties.read("sql", "addGrades"),
							gradesJB.getId(), gradesJB.getStudentId(),
							gradesJB.getCourseNumber(),
							gradesJB.getCycleNumber(), gradesJB.getSubject(),
							gradesJB.getPoints());
				}
			} else {
				ret10 = 0;
			}

			if (ret >= 0 && ret1 >= 0 && ret2 >= 0 && ret3 >= 0 && ret4 >= 0
					&& ret5 >= 0 && ret6 >= 0 && ret7 >= 0 && ret8 >= 0
					&& ret9 >= 0 && ret10 >= 0) {
				conn.commit();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
				return false;
			} catch (Exception e2) {
				// TODO: handle exception
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return false;
	}

}
