package com.matrix.jbt.service.impl;

import java.util.List;

import com.matrix.jbt.dao.GradesDao;
import com.matrix.jbt.dao.impl.GradesDaoImpl;
import com.matrix.jbt.entity.GradesJB;
import com.matrix.jbt.service.GradesService;

public class GradesServiceImpl implements GradesService {
	private GradesDao gradesDao = new GradesDaoImpl();

	@Override
	public boolean updateGrades(List<GradesJB> gradesJBs) {
		// TODO Auto-generated method stub
		return gradesDao.updateGrades(gradesJBs);
	}

	@Override
	public List<GradesJB> getGradesByUserId(String userId, String courseNumber,
			String cycleNumber) {
		// TODO Auto-generated method stub
		return gradesDao.getGradesByUserId(userId, courseNumber, cycleNumber);
	}

	@Override
	public boolean deleteGrades() {
		// TODO Auto-generated method stub
		return gradesDao.deleteGrades();
	}

}
