package com.matrix.jbt.service.impl;

import java.util.List;

import net.sf.json.JSONArray;

import com.matrix.jbt.dao.JobDao;
import com.matrix.jbt.dao.impl.JobDaoImpl;
import com.matrix.jbt.entity.JobJB;
import com.matrix.jbt.service.JobService;

public class JobServiceImpl implements JobService {
	private JobDao jobDao = new JobDaoImpl();

	@Override
	public boolean updateJob(List<JobJB> jobList) {
		// TODO Auto-generated method stub
		return jobDao.updateJob(jobList);
	}

	@Override
	public List<JobJB> getJobList() {
		// TODO Auto-generated method stub
		return jobDao.getJobList();
	}

	@Override
	public JobJB getJobById(String jobId) {
		// TODO Auto-generated method stub
		return jobDao.getJobById(jobId);
	}

	@Override
	public List<JobJB> getJobListWithAreaFilter(JSONArray areas) {
		// TODO Auto-generated method stub
		return jobDao.getJobListWithAreaFilter(areas);
	}

	@Override
	public List<JobJB> getJobListWithDomainFilter(JSONArray domains) {
		// TODO Auto-generated method stub
		return jobDao.getJobListWithDomainFilter(domains);
	}

	@Override
	public List<JobJB> getJobListWithDomainAndAreaFilter(JSONArray domains,
			JSONArray areas) {
		// TODO Auto-generated method stub
		return jobDao.getJobListWithDomainAndAreaFilter(domains, areas);
	}

	@Override
	public boolean deleteJobs() {
		// TODO Auto-generated method stub
		return jobDao.deleteJobs();
	}

}
