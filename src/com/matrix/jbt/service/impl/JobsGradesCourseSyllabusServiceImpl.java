package com.matrix.jbt.service.impl;

import java.util.List;

import com.matrix.jbt.dao.JobsGradesCourseSyllabusDao;
import com.matrix.jbt.dao.impl.JobsGradesCourseSyllabusDaoImpl;
import com.matrix.jbt.entity.GradesJB;
import com.matrix.jbt.entity.JobJB;
import com.matrix.jbt.service.JobsGradesCourseSyllabusService;

public class JobsGradesCourseSyllabusServiceImpl implements
		JobsGradesCourseSyllabusService {
	private JobsGradesCourseSyllabusDao jobsGradesCourseSyllabusDao = new JobsGradesCourseSyllabusDaoImpl();

	@Override
	public boolean updateJobsGradesCourseSyllabus(List<JobJB> jobJBs,
			List<GradesJB> gradesJBs, List<Object> courseSyllabusJBs,
			List<Object> courseJBs, List<Object> branchJBs,
			List<Object> updatedUsers, List<Object> insertUsers) {
		// TODO Auto-generated method stub
		return jobsGradesCourseSyllabusDao.updateJobsGradesCourseSyllabus(
				jobJBs, gradesJBs, courseSyllabusJBs, courseJBs, branchJBs,
				updatedUsers, insertUsers);
	}

	@Override
	public boolean deleteJobsGradesCourseSyllabus() {
		// TODO Auto-generated method stub
		return jobsGradesCourseSyllabusDao.deleteJobsGradesCourseSyllabus();
	}

	@Override
	public boolean deleteOthersAndUpdateCourseSyllabus(
			List<Object> courseSyllabusJBs, List<Object> courseJBs,
			List<Object> branchJBs, List<Object> updatedUsers,
			List<Object> usersGetFromJB) {
		// TODO Auto-generated method stub
		return jobsGradesCourseSyllabusDao.deleteOthersAndUpdateCourseSyllabus(
				courseSyllabusJBs, courseJBs, branchJBs, updatedUsers,
				usersGetFromJB);
	}

	@Override
	public boolean deleteOthersAndUpdateGrades(List<GradesJB> gradesJBs) {
		// TODO Auto-generated method stub
		return jobsGradesCourseSyllabusDao
				.deleteOthersAndUpdateGrades(gradesJBs);
	}

	@Override
	public boolean deleteJobsAndUpdateGradesCourseSyllabus(
			List<GradesJB> gradesJBs, List<Object> courseSyllabusJBs,
			List<Object> courseJBs, List<Object> branchJBs,
			List<Object> updatedUsers, List<Object> usersGetFromJB) {
		// TODO Auto-generated method stub
		return jobsGradesCourseSyllabusDao
				.deleteJobsAndUpdateGradesCourseSyllabus(gradesJBs,
						courseSyllabusJBs, courseJBs, branchJBs, updatedUsers,
						usersGetFromJB);
	}

	@Override
	public boolean deleteOthersAndUpdateJobs(List<JobJB> jobList) {
		// TODO Auto-generated method stub
		return jobsGradesCourseSyllabusDao.deleteOthersAndUpdateJobs(jobList);
	}

	@Override
	public boolean deleteGradesAndUpdateJobsCourseSyllabus(List<JobJB> jobList,
			List<Object> courseSyllabusJBs, List<Object> courseJBs,
			List<Object> branchJBs, List<Object> updatedUsers,
			List<Object> usersGetFromJB) {
		// TODO Auto-generated method stub
		return jobsGradesCourseSyllabusDao
				.deleteGradesAndUpdateJobsCourseSyllabus(jobList,
						courseSyllabusJBs, courseJBs, branchJBs, updatedUsers,
						usersGetFromJB);
	}

	@Override
	public boolean deleteCourseSyllabusAndUpdateJobsGrades(List<JobJB> jobList,
			List<GradesJB> gradesJBs) {
		// TODO Auto-generated method stub
		return jobsGradesCourseSyllabusDao
				.deleteCourseSyllabusAndUpdateJobsGrades(jobList, gradesJBs);
	}

}
