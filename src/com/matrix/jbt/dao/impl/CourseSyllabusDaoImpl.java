package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.matrix.jbt.dao.CourseSyllabusDao;
import com.matrix.jbt.entity.BranchJB;
import com.matrix.jbt.entity.CourseJB;
import com.matrix.jbt.entity.CourseSyllabusJB;
import com.matrix.jbt.entity.User;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class CourseSyllabusDaoImpl implements CourseSyllabusDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public boolean updateCourseSyllabus(
			List<CourseSyllabusJB> courseSyllabusJBs, List<CourseJB> courseJBs,
			List<BranchJB> branchJBs, List<User> updatedUsers,
			List<User> usersGetFromJB) {
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

			if (courseSyllabusJBs.size() > 0) {
				for (CourseSyllabusJB courseSyllabusJB : courseSyllabusJBs) {
					ret3 = runner.update(conn,
							ReadProperties.read("sql", "addCourseSyllabus"),
							courseSyllabusJB.getUserId(),
							courseSyllabusJB.getMeetDate(),
							courseSyllabusJB.getMeetStart(),
							courseSyllabusJB.getMeetEnd(),
							courseSyllabusJB.getMeetNum(),
							courseSyllabusJB.getMeetBranch(),
							courseSyllabusJB.getMeetBuildingNum(),
							courseSyllabusJB.getMeetBuildingName(),
							courseSyllabusJB.getMeetClassNum(),
							courseSyllabusJB.getMeetTeacher(),
							courseSyllabusJB.getMeetStatus(),
							courseSyllabusJB.getMeetTopic(),
							courseSyllabusJB.getMeetClassName());
				}
			} else {
				ret3 = 0;
			}

			if (courseJBs.size() > 0) {
				for (CourseJB courseJB : courseJBs) {
					ret4 = runner.update(conn,
							ReadProperties.read("sql", "addCourse"),
							courseJB.getCourseName(), courseJB.getCourseNum(),
							courseJB.getCycleNum(),
							courseJB.getCycleOpenDate(),
							courseJB.getDomainNumber(),
							courseJB.getDomainName(), courseJB.getUserId());
				}
			} else {
				ret4 = 0;
			}

			if (branchJBs.size() > 0) {
				for (BranchJB branchJB : branchJBs) {
					ret5 = runner.update(conn,
							ReadProperties.read("sql", "addBranch"),
							branchJB.getBranch(), branchJB.getUserId());
				}
			} else {
				ret5 = 0;
			}

			if (updatedUsers == null) {
				ret6 = 0;
			} else {
				if (updatedUsers.size() > 0) {
					for (User user : updatedUsers) {
						ret6 = runner.update(conn,
								ReadProperties.read("sql", "updateUser"),
								user.getFirstName(), user.getLastName(),
								user.getEmail(), user.getUserId());
					}
				} else {
					ret6 = 0;
				}

			}

			if (usersGetFromJB.size() > 0) {
				for (User user : usersGetFromJB) {
					ret7 = runner.update(conn,
							ReadProperties.read("sql", "insertUser"),
							user.getUserId(), user.getFirstName(),
							user.getLastName(), user.getPassword(),
							user.getEmail(), user.getFlag(), user.getStatus());
				}
			} else {
				ret7 = 0;
			}

			// System.out.println("ret=" + ret + "ret1=" + ret1 + "ret2=" + ret2
			// + "ret3=" + ret3 + "ret4=" + ret4 + "ret5=" + ret5
			// + "ret6=" + ret6 + "ret7=" + ret7);

			if (ret >= 0 && ret1 >= 0 && ret2 >= 0 && ret3 >= 0 && ret4 >= 0
					&& ret5 >= 0 && ret6 >= 0 && ret7 >= 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
	public List<CourseSyllabusJB> getCourseSyllabusByUserId(String userId) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		List<CourseSyllabusJB> courseSyllabusList = null;
		try {
			courseSyllabusList = runner.query(conn,
					ReadProperties.read("sql", "getCourseSyllabusByUser"),
					new BeanListHandler<>(CourseSyllabusJB.class), userId);
			if (courseSyllabusList != null) {
				return courseSyllabusList;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean deleteCourseSyllabus() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			int ret1 = -1;
			int ret2 = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "deleteCourseSyllabus"));
			ret1 = runner.update(conn,
					ReadProperties.read("sql", "deleteCourse"));
			ret2 = runner.update(conn,
					ReadProperties.read("sql", "deleteBranch"));
			if (ret >= 0 && ret1 >= 0 && ret2 >= 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
			}
			return false;
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
