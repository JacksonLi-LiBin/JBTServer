package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.matrix.jbt.dao.GradesDao;
import com.matrix.jbt.entity.GradesJB;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class GradesDaoImpl implements GradesDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public boolean updateGrades(List<GradesJB> gradesJBs) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			int ret1 = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "deleteGrades"));

			if (gradesJBs.size() > 0) {
				for (GradesJB gradesJB : gradesJBs) {
					ret1 = runner.update(conn,
							ReadProperties.read("sql", "addGrades"),
							gradesJB.getId(), gradesJB.getStudentId(),
							gradesJB.getCourseNumber(),
							gradesJB.getCycleNumber(), gradesJB.getSubject(),
							gradesJB.getPoints());
				}
			} else {
				ret1 = 0;
			}

			if (ret >= 0 && ret1 >= 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			try {
				conn.rollback();
				return false;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
	public List<GradesJB> getGradesByUserId(String userId, String courseNumber,
			String cycleNumber) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		List<GradesJB> gradesJBs = null;
		try {
			gradesJBs = runner.query(conn,
					ReadProperties.read("sql", "getGradesByUserId"),
					new BeanListHandler<>(GradesJB.class), userId,
					courseNumber, cycleNumber);
			if (gradesJBs != null) {
				return gradesJBs;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return null;
	}

	@Override
	public boolean deleteGrades() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn,
					ReadProperties.read("sql", "deleteGrades"));
			if (ret >= 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
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

}
