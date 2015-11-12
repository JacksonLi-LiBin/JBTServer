package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.matrix.jbt.dao.InfoDao;
import com.matrix.jbt.entity.BranchJB;
import com.matrix.jbt.entity.CourseJB;
import com.matrix.jbt.entity.User;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class InfoDaoImpl implements InfoDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public String getInfos(String userId) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			User user = null;
			user = runner.query(conn,
					ReadProperties.read("sql", "getUserByUserId"),
					new BeanHandler<>(User.class), userId);

			if (user != null) {
				String str = "{\"d\":\"{\\\"ID\\\":\\\"" + userId
						+ "\\\",\\\"FirstName\\\":\\\"" + user.getFirstName()
						+ "\\\",\\\"LastName\\\":\\\"" + user.getLastName()
						+ "\\\",\\\"Email\\\":\\\"" + user.getEmail()
						+ "\\\",\\\"Course\\\":[";

				List<CourseJB> courseJBs = null;
				courseJBs = runner.query(conn,
						ReadProperties.read("sql", "getCourseByUser"),
						new BeanListHandler<>(CourseJB.class), userId);
				if (courseJBs != null && courseJBs.size() > 0) {
					for (CourseJB courseJB : courseJBs) {
						str = str + "{\\\"CourseNumber\\\":"
								+ courseJB.getCourseNum()
								+ ",\\\"CourseName\\\":\\\""
								+ courseJB.getCourseName()
								+ "\\\",\\\"CycleNumber\\\":"
								+ courseJB.getCycleNum() + "},";
					}
					str = str.subSequence(0, str.length() - 1)
							+ "],\\\"Branch\\\":[";
				} else {
					str = str + "],\\\"Branch\\\":[";
				}

				List<BranchJB> branchJBs = null;
				branchJBs = runner.query(conn,
						ReadProperties.read("sql", "getBranchByUser"),
						new BeanListHandler<>(BranchJB.class), userId);
				if (branchJBs != null && branchJBs.size() > 0) {
					for (BranchJB branchJB : branchJBs) {
						str = str + "{\\\"Branch\\\":" + branchJB.getBranch()
								+ "},";
					}
					str = str.subSequence(0, str.length() - 1) + "]";
				} else {
					str = str + "]";
				}

				str = str + "}\"}";
				return str;
			} else {
				return "{\"d\":\"\\\"Empty\\\"\"}";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
