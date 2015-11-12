package com.matrix.jbt.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.matrix.jbt.service.EmailService;
import com.matrix.jbt.service.impl.EmailServiceImpl;

public class TestUserCount {

	@Test
	public void test() {
		EmailService emailService = new EmailServiceImpl();
		Integer counts = emailService.getUserEmailCount();
		System.out.println("all counts:" + counts);

		Integer countsWithFilter = emailService
				.getUserEmailCountWithFilter("ריק");
		System.out.println("filter counts:" + countsWithFilter);

		Integer ecounts = emailService.getAllEmailCount();
		System.out.println("e counts:" + ecounts);

		Integer ecountf = emailService.getEmailCountWithFilter("l");
		System.out.println("e counts f:" + ecountf);
	}
}
