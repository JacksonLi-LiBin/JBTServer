package com.matrix.jbt.service.impl;

import java.util.List;
import java.util.Map;

import com.matrix.jbt.dao.impl.EmailDaoImpl;
import com.matrix.jbt.entity.CourseJB;
import com.matrix.jbt.entity.Email;
import com.matrix.jbt.entity.User;
import com.matrix.jbt.service.EmailService;

public class EmailServiceImpl implements EmailService {
	private EmailDaoImpl emailDaoImpl = new EmailDaoImpl();

	@Override
	public boolean addEmail(Email email) {
		return emailDaoImpl.addEmail(email);
	}

	@Override
	public boolean updateEmail(Email email) {
		return false;
	}

	@Override
	public boolean deleteEmail(int emailId) {
		return false;
	}

	@Override
	public Email getEmailByEmailId(int emailId) {
		return null;
	}

	@Override
	public List<Email> getAll() {
		return emailDaoImpl.getAll();
	}

	@Override
	public boolean deleteEmail(String emailname) {
		// TODO Auto-generated method stub
		return emailDaoImpl.deleteEmail(emailname);
	}

	@Override
	public boolean updateEmail(String oldEmail, String newEmail) {
		// TODO Auto-generated method stub
		return emailDaoImpl.updateEmail(oldEmail, newEmail);
	}

	@Override
	public List<User> getAllUserEmail() {
		// TODO Auto-generated method stub
		return emailDaoImpl.getAllUserEmail();
	}

	@Override
	public boolean updateUserEmail(Integer userId, String email) {
		// TODO Auto-generated method stub
		return emailDaoImpl.updateUserEmail(userId, email);
	}

	@Override
	public List<Email> getEmailsByPageNumber(Integer beginRow,
			Integer pageCounts) {
		// TODO Auto-generated method stub
		return emailDaoImpl.getEmailsByPageNumber(beginRow, pageCounts);
	}

	@Override
	public List<Email> getEmailsByPageNumberWithFilter(Integer beginRow,
			Integer pageCounts, String filterStr) {
		// TODO Auto-generated method stub
		return emailDaoImpl.getEmailsByPageNumberWithFilter(beginRow,
				pageCounts, filterStr);
	}

	@Override
	public Map<User, List<CourseJB>> getUserEmailByPage(Integer beginRow,
			Integer pageCounts) {
		// TODO Auto-generated method stub
		return emailDaoImpl.getUserEmailByPage(beginRow, pageCounts);
	}

	@Override
	public Map<User, List<CourseJB>> getUserEmailByPageWithFilter(
			Integer beginRow, Integer pageCounts, String filter) {
		// TODO Auto-generated method stub
		return emailDaoImpl.getUserEmailByPageWithFilter(beginRow, pageCounts,
				filter);
	}

	@Override
	public Integer getUserEmailCount() {
		// TODO Auto-generated method stub
		return emailDaoImpl.getUserEmailCount();
	}

	@Override
	public Integer getUserEmailCountWithFilter(String filter) {
		// TODO Auto-generated method stub
		return emailDaoImpl.getUserEmailCountWithFilter(filter);
	}

	@Override
	public Integer getAllEmailCount() {
		// TODO Auto-generated method stub
		return emailDaoImpl.getAllEmailCount();
	}

	@Override
	public Integer getEmailCountWithFilter(String filter) {
		// TODO Auto-generated method stub
		return emailDaoImpl.getEmailCountWithFilter(filter);
	}

}
