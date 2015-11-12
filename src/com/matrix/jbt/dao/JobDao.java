package com.matrix.jbt.dao;

import java.util.List;

import net.sf.json.JSONArray;

import com.matrix.jbt.entity.JobJB;

/**
 * 
 * @author JacksonLi
 * @date 2014/5/22
 */
public interface JobDao {
	/**
	 * update job table
	 * 
	 * @param jobList
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean updateJob(List<JobJB> jobList);

	/**
	 * get all job from database
	 * 
	 * @return
	 * @author JacksonLi List<Job>
	 */
	public List<JobJB> getJobList();

	/**
	 * get job by job id
	 * 
	 * @param jobId
	 * @return
	 * @author JacksonLi Job
	 */
	public JobJB getJobById(String jobId);

	/**
	 * get all jobs with area filter
	 * 
	 * @param areas
	 * @return
	 * @author JacksonLi List<JobJB>
	 */
	public List<JobJB> getJobListWithAreaFilter(JSONArray areas);

	/**
	 * get all jobs with domain filter
	 * 
	 * @param domains
	 * @return
	 * @author JacksonLi List<JobJB>
	 */
	public List<JobJB> getJobListWithDomainFilter(JSONArray domains);

	/**
	 * get all jobs with domain and area filter
	 * 
	 * @param domains
	 * @param areas
	 * @return
	 * @author JacksonLi List<JobJB>
	 */
	public List<JobJB> getJobListWithDomainAndAreaFilter(JSONArray domains,
			JSONArray areas);

	/**
	 * delete all jobs
	 * 
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteJobs();
}
