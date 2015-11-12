package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.matrix.jbt.dao.InquiryDao;
import com.matrix.jbt.entity.Inquiry;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class InquiryDaoImpl implements InquiryDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public boolean addInruiry(Inquiry inquiry) {
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret = -1;
			ret = runner.update(conn, ReadProperties.read("sql", "addInruiry"),
					inquiry.getFullname(), inquiry.getCoursename(),
					inquiry.getCoursenum(), inquiry.getCyclenum(),
					inquiry.getBranch(), inquiry.getPhone(),
					inquiry.getEmail(), inquiry.getSubjectnum(),
					inquiry.getDetails(), inquiry.getUserid());
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
		return false;
	}
}
