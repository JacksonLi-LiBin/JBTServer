package com.matrix.jbt.dao;

import java.util.List;

import com.matrix.jbt.entity.GradesJB;

public interface GradesDao {
	/**
	 * update grades
	 * 
	 * @param gradesJBs
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean updateGrades(List<GradesJB> gradesJBs);

	/**
	 * get grades list by user id
	 * 
	 * @param userId
	 * @param courseNumber
	 * @param cycleNumber
	 * @return
	 * @author JacksonLi List<GradesJB>
	 */
	public List<GradesJB> getGradesByUserId(String userId, String courseNumber,
			String cycleNumber);

	/**
	 * delete all grades
	 * 
	 * @return
	 * @author JacksonLi boolean
	 */
	public boolean deleteGrades();
}
