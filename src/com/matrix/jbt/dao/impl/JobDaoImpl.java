package com.matrix.jbt.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.matrix.jbt.dao.JobDao;
import com.matrix.jbt.entity.JobJB;
import com.matrix.jbt.tool.C3P0DBConnectionPool;
import com.matrix.jbt.tool.ReadProperties;

public class JobDaoImpl implements JobDao {
	private QueryRunner runner = new QueryRunner();

	@Override
	public boolean updateJob(List<JobJB> jobList) {
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
			ret = runner.update(conn, ReadProperties.read("sql", "deleteJob"));
			ret2 = runner
					.update(conn, ReadProperties.read("sql", "deleteArea"));
			ret3 = runner.update(conn,
					ReadProperties.read("sql", "deleteDomain"));

			Map<String, String> mapAreas = new HashMap<>();
			Map<String, String> mapDomains = new HashMap<>();

			if (jobList.size() > 0) {
				for (JobJB job : jobList) {
					ret1 = runner.update(conn,
							ReadProperties.read("sql", "addJob"),
							job.getJobId(), job.getTitle(), job.getCompany(),
							job.getArea(), job.getDomain(),
							job.getExperience(), job.getText(),
							job.getRequirements(), job.getEmail());

					mapAreas.put(job.getArea(), job.getArea());
					mapDomains.put(job.getDomain(), job.getDomain());
				}
			} else {
				ret1 = 0;
			}

			if (mapAreas.size() > 0) {
				for (Entry<String, String> entry : mapAreas.entrySet()) {
					ret4 = runner.update(conn,
							ReadProperties.read("sql", "addArea"),
							entry.getValue());
				}
			} else {
				ret4 = 0;
			}

			if (mapDomains.size() > 0) {
				for (Entry<String, String> entry : mapDomains.entrySet()) {
					ret5 = runner.update(conn,
							ReadProperties.read("sql", "addDomain"),
							entry.getValue());
				}
			} else {
				ret5 = 0;
			}

			if (ret >= 0 && ret2 >= 0 && ret3 >= 0 && ret1 >= 0 && ret4 >= 0
					&& ret5 >= 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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

	@Override
	public List<JobJB> getJobList() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		List<JobJB> jobList = null;
		try {
			jobList = runner.query(conn,
					ReadProperties.read("sql", "getJobList"),
					new BeanListHandler<>(JobJB.class));
			if (jobList != null) {
				return jobList;
			}
		} catch (Exception e) {
			// TODO: handle exception
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

	@Override
	public JobJB getJobById(String jobId) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		JobJB job = null;
		try {
			job = runner.query(conn, ReadProperties.read("sql", "getJobById"),
					new BeanHandler<>(JobJB.class), jobId);
			if (job != null) {
				return job;
			}
		} catch (Exception e) {
			// TODO: handle exception
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

	@SuppressWarnings("deprecation")
	@Override
	public List<JobJB> getJobListWithAreaFilter(JSONArray areas) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		int areaSize = areas.size();
		List<JobJB> jobList = null;
		String[] filterValue = new String[areaSize];
		StringBuffer filterStr = new StringBuffer(" where ");
		for (int i = 0; i < areaSize; i++) {
			if (i == 0) {
				filterStr.append(" area=? ");
				filterValue[i] = areas.getJSONObject(i).getString("Title");
			} else {
				filterStr.append(" or area=? ");
				filterValue[i] = areas.getJSONObject(i).getString("Title");
			}
		}

		try {
			jobList = runner.query(
					conn,
					ReadProperties.read("sql", "getJobList")
							+ filterStr.toString(), filterValue,
					new BeanListHandler<>(JobJB.class));
			if (jobList != null) {
				return jobList;
			}
		} catch (Exception e) {
			// TODO: handle exception
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

	@SuppressWarnings("deprecation")
	@Override
	public List<JobJB> getJobListWithDomainFilter(JSONArray domains) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		int domainSize = domains.size();
		List<JobJB> jobList = null;
		String[] filterValue = new String[domainSize];
		StringBuffer filterStr = new StringBuffer(" where ");
		for (int i = 0; i < domainSize; i++) {
			if (i == 0) {
				filterStr.append(" domain=? ");
				filterValue[i] = domains.getJSONObject(i).getString("Title");
			} else {
				filterStr.append(" or domain=? ");
				filterValue[i] = domains.getJSONObject(i).getString("Title");
			}
		}

		try {
			jobList = runner.query(
					conn,
					ReadProperties.read("sql", "getJobList")
							+ filterStr.toString(), filterValue,
					new BeanListHandler<>(JobJB.class));
			if (jobList != null) {
				return jobList;
			}
		} catch (Exception e) {
			// TODO: handle exception
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

	@SuppressWarnings("deprecation")
	@Override
	public List<JobJB> getJobListWithDomainAndAreaFilter(JSONArray domains,
			JSONArray areas) {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		int areaSize = areas.size();
		int domainSize = domains.size();
		List<JobJB> jobList = null;
		String[] filterValue = new String[areaSize + domainSize];
		StringBuffer filterStr = new StringBuffer(" where ( ");
		for (int i = 0; i < domainSize; i++) {
			if (i == 0) {
				filterStr.append(" domain=? ");
				filterValue[i] = domains.getJSONObject(i).getString("Title");
			} else {
				filterStr.append(" or domain=? ");
				filterValue[i] = domains.getJSONObject(i).getString("Title");
			}
		}
		filterStr.append(" ) and (");

		for (int j = 0; j < areaSize; j++) {
			if (j == 0) {
				filterStr.append(" area=? ");
				filterValue[domainSize + j] = areas.getJSONObject(j).getString(
						"Title");
			} else {
				filterStr.append(" or area=? ");
				filterValue[domainSize + j] = areas.getJSONObject(j).getString(
						"Title");
			}
		}
		filterStr.append(" )");

		try {
			jobList = runner.query(
					conn,
					ReadProperties.read("sql", "getJobList")
							+ filterStr.toString(), filterValue,
					new BeanListHandler<>(JobJB.class));
			if (jobList != null) {
				return jobList;
			}
		} catch (Exception e) {
			// TODO: handle exception
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

	@Override
	public boolean deleteJobs() {
		// TODO Auto-generated method stub
		Connection conn = (Connection) C3P0DBConnectionPool.getConnection();
		try {
			conn.setAutoCommit(false);
			int ret1 = -1;
			int ret2 = -1;
			int ret3 = -1;
			ret1 = runner.update(conn, ReadProperties.read("sql", "deleteJob"));
			ret2 = runner
					.update(conn, ReadProperties.read("sql", "deleteArea"));
			ret3 = runner.update(conn,
					ReadProperties.read("sql", "deleteDomain"));
			if (ret1 >= 0 && ret2 >= 0 && ret3 >= 0) {
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
