package com.matrix.jbt.dao;

import java.util.List;
import com.matrix.jbt.entity.GradesJB;
import com.matrix.jbt.entity.JobJB;

public interface JobsGradesCourseSyllabusDao {
	/**
	 * update database after getting data from JB
	 * 
	 * @param jobJBs
	 * @param gradesJBs
	 * @param courseSyllabusJBs
	 * @param courseJBs
	 * @param branchJBs
	 * @param updatedUsers
	 * @param insertUsers
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean updateJobsGradesCourseSyllabus(List<JobJB> jobJBs,
			List<GradesJB> gradesJBs, List<Object> courseSyllabusJBs,
			List<Object> courseJBs, List<Object> branchJBs,
			List<Object> updatedUsers, List<Object> insertUsers);

	/**
	 * delete Jobs and Grades and CourseSyllabus
	 * 
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteJobsGradesCourseSyllabus();

	/**
	 * delete other and update course syllabus
	 * 
	 * @param courseSyllabusJBs
	 * @param courseJBs
	 * @param branchJBs
	 * @param updatedUsers
	 * @param usersGetFromJB
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteOthersAndUpdateCourseSyllabus(
			List<Object> courseSyllabusJBs, List<Object> courseJBs,
			List<Object> branchJBs, List<Object> updatedUsers,
			List<Object> usersGetFromJB);

	/**
	 * delete other and update grades
	 * 
	 * @param gradesJBs
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteOthersAndUpdateGrades(List<GradesJB> gradesJBs);

	/**
	 * delete jobs and Update Grades CourseSyllabus
	 * 
	 * @param gradesJBs
	 * @param courseSyllabusJBs
	 * @param courseJBs
	 * @param branchJBs
	 * @param updatedUsers
	 * @param usersGetFromJB
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteJobsAndUpdateGradesCourseSyllabus(
			List<GradesJB> gradesJBs, List<Object> courseSyllabusJBs,
			List<Object> courseJBs, List<Object> branchJBs,
			List<Object> updatedUsers, List<Object> usersGetFromJB);

	/**
	 * delete others and update jobs
	 * 
	 * @param jobList
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteOthersAndUpdateJobs(List<JobJB> jobList);

	/**
	 * delete grades and update jobs course syllabus
	 * 
	 * @param jobList
	 * @param courseSyllabusJBs
	 * @param courseJBs
	 * @param branchJBs
	 * @param updatedUsers
	 * @param usersGetFromJB
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteGradesAndUpdateJobsCourseSyllabus(List<JobJB> jobList,
			List<Object> courseSyllabusJBs, List<Object> courseJBs,
			List<Object> branchJBs, List<Object> updatedUsers,
			List<Object> usersGetFromJB);

	/**
	 * delete course syllabus and update jobs grades
	 * 
	 * @param jobList
	 * @param gradesJBs
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteCourseSyllabusAndUpdateJobsGrades(List<JobJB> jobList,
			List<GradesJB> gradesJBs);
}
