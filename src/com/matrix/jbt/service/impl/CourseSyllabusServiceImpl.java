package com.matrix.jbt.service.impl;

import java.util.List;

import com.matrix.jbt.dao.CourseSyllabusDao;
import com.matrix.jbt.dao.impl.CourseSyllabusDaoImpl;
import com.matrix.jbt.entity.BranchJB;
import com.matrix.jbt.entity.CourseJB;
import com.matrix.jbt.entity.CourseSyllabusJB;
import com.matrix.jbt.entity.User;
import com.matrix.jbt.service.CourseSyllabusService;

public class CourseSyllabusServiceImpl implements CourseSyllabusService {
	private CourseSyllabusDao courseSyllabusDao = new CourseSyllabusDaoImpl();

	@Override
	public boolean updateCourseSyllabus(
			List<CourseSyllabusJB> courseSyllabusJBs, List<CourseJB> courseJBs,
			List<BranchJB> branchJBs, List<User> updatedUsers,
			List<User> usersGetFromJB) {
		// TODO Auto-generated method stub
		return courseSyllabusDao.updateCourseSyllabus(courseSyllabusJBs,
				courseJBs, branchJBs, updatedUsers, usersGetFromJB);
	}

	@Override
	public List<CourseSyllabusJB> getCourseSyllabusByUserId(String userId) {
		// TODO Auto-generated method stub
		return courseSyllabusDao.getCourseSyllabusByUserId(userId);
	}

	@Override
	public boolean deleteCourseSyllabus() {
		// TODO Auto-generated method stub
		return courseSyllabusDao.deleteCourseSyllabus();
	}

}
