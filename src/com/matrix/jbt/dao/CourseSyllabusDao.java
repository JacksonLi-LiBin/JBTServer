package com.matrix.jbt.dao;

import java.util.List;

import com.matrix.jbt.entity.BranchJB;
import com.matrix.jbt.entity.CourseJB;
import com.matrix.jbt.entity.CourseSyllabusJB;
import com.matrix.jbt.entity.User;

public interface CourseSyllabusDao {
	/**
	 * update course syllabus
	 * 
	 * @param courseSyllabusJBs
	 * @param courseJBs
	 * @param branchJBs
	 * @param updatedUsers
	 * @param usersGetFromJB
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean updateCourseSyllabus(
			List<CourseSyllabusJB> courseSyllabusJBs, List<CourseJB> courseJBs,
			List<BranchJB> branchJBs, List<User> updatedUsers,
			List<User> usersGetFromJB);

	/**
	 * get course syllabus by user id
	 * 
	 * @param userId
	 * @return
	 * @author JacksonLi List<CourseSyllabusJB>
	 */
	public List<CourseSyllabusJB> getCourseSyllabusByUserId(String userId);

	/**
	 * delete course syllabus
	 * 
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteCourseSyllabus();
}
